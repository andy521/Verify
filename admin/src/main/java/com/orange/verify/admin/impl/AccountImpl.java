package com.orange.verify.admin.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.*;
import com.orange.verify.admin.service.BaiduMapApiServiceL;
import com.orange.verify.admin.transition.Transition;
import com.orange.verify.api.bean.*;
import com.orange.verify.api.sc.*;
import com.orange.verify.api.sr.ServiceResult;
import com.orange.verify.api.service.AccountService;
import com.orange.verify.api.sr.*;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.*;
import com.orange.verify.common.rsa.RsaUtil;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AccountImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private RedisImpl redis;

    @Autowired
    private SoftMapper softMapper;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private Transition transition;

    @Autowired
    private CardTypeMapper cardTypeMapper;

    @Autowired
    private AccountRegisterLogMapper accountRegisterLogMapper;

    @Autowired
    private AccountLoginLogMapper accountLoginLogMapper;

    @Autowired
    private BaiduMapApiServiceL baiduMapApiServiceL;

    @Autowired
    private InterProcessMutex lock;

    @Override
    public Page<AccountVo> page(AccountVo accountVo, Page page) {

        return page.setRecords(super.baseMapper.page(accountVo,page));
    }

    @Override
    public ServiceResult<String> getPublicKey() {

        ServiceResult<String> result = new ServiceResult<>();

        try {

            Map<String, Object> initKey = RsaUtil.initKey();
            String publicKeyToBase64 = RsaUtil.getPublicKeyToBase64(initKey);
            String privateKeyToBase64 = RsaUtil.getPrivateKeyToBase64(initKey);

            redis.save10Minutes(publicKeyToBase64,privateKeyToBase64);

            result.setCode(AccountImplGetPublicKeyEnum.SUCCESS);
            result.setData(publicKeyToBase64);

            return result;

        } catch (Exception e) {
            result.setCode(AccountImplGetPublicKeyEnum.KEY_ERROR);
            return result;
        }

    }

    @Override
    public void saveVerificationCode(AccountVerificationCodeVo accountVerificationCodeVo) {

        String privateKey = (String) redis.getByKey(accountVerificationCodeVo.getPublicKey());
        if (!StrUtil.hasEmpty(privateKey)) {
            redis.save10Minutes("vc=" + accountVerificationCodeVo.getPublicKey(),
                    accountVerificationCodeVo.getCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult register(AccountRegisterVo accountRegisterVo) {

        ServiceResult result = new ServiceResult<>();

        String privateKey = (String) redis.getByKey(accountRegisterVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(AccountImplRegisterEnum.KEY_EMPTY);
            return result;
        }

        //验证码不匹配直接返回
        String vc = (String) redis.getByKey("vc=" + accountRegisterVo.getPublicKey());
        if (StrUtil.hasEmpty(vc)) {
            result.setCode(AccountImplRegisterEnum.VC_EMPTY);
            return result;
        } else if (!accountRegisterVo.getVc().equals(vc)) {
            result.setCode(AccountImplRegisterEnum.VC_MISMATCHES);
            return result;
        }

        //采用分布式锁进行用户名的唯一控制
        try {
            boolean acquire = lock.acquire(5, TimeUnit.SECONDS);
            if (acquire == true) {
                QueryWrapper<Account> username = new QueryWrapper<Account>().eq("username",
                        accountRegisterVo.getUsername());
                Integer selectCount = super.baseMapper.selectCount(username);
                //用户名是否存在
                if (selectCount > 0) {
                    result.setCode(AccountImplRegisterEnum.ACCOUNT_ALREADY_EXIST);
                    return result;
                }
            } else {
                result.setCode(AccountImplRegisterEnum.NIMIETY);
                return result;
            }
        } catch (Exception e) {
            result.setCode(AccountImplRegisterEnum.SERVER_ERROR);
            return result;
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                result.setCode(AccountImplRegisterEnum.SERVER_ERROR);
                return result;
            }
        }

        Soft soft = softMapper.selectById(accountRegisterVo.getSoftId());
        if (soft == null) {
            result.setCode(AccountImplRegisterEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(AccountImplRegisterEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        } else if (soft.getRegisterStatus() == SoftRegisterStatus.CLOSE.getStatusCode()) {
            result.setCode(AccountImplRegisterEnum.REGISTER_CLOSE);
            result.setMsg(soft.getRegisteCloseMsg());
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountRegisterVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountRegisterVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(AccountImplRegisterEnum.KEY_ERROR);
            return result;
        }
        if (StrUtil.hasEmpty(password)) {
            result.setCode(AccountImplRegisterEnum.KEY_ERROR);
            return result;
        } else if (password.length() > 10 || password.length() < 5) {
            result.setCode(AccountImplRegisterEnum.PASSWORD_LENGTH_ERROR);
            return result;
        }

        //查询ip信息
        String addressByIp = "";
        try {
            addressByIp = baiduMapApiServiceL.getIpInfo(accountRegisterVo.getIp());
        } catch (Exception e) {
            result.setCode(AccountImplRegisterEnum.BAIDU_API_ERROR);
            return result;
        }

        //进行转型然后插入数据库
        accountRegisterVo.setPassword(password);
        accountRegisterVo.setCode(code);

        Account account = transition.fromVo(accountRegisterVo);
        account.setCreateIpInfo(addressByIp);

        int insert = super.baseMapper.insert(account);
        if (insert > 0) {
            AccountRegisterLog accountRegisterLog = new AccountRegisterLog();
            accountRegisterLog.setAccountId(account.getId());
            accountRegisterLog.setIp(account.getCreateIp());
            accountRegisterLog.setIpInfo(addressByIp);
            accountRegisterLog.setSoftId(account.getSoftId());
            accountRegisterLogMapper.insert(accountRegisterLog);

            result.setCode(AccountImplRegisterEnum.REGISTER_SUCCESS);
            return result;
        }

        result.setCode(AccountImplRegisterEnum.REGISTER_ERROR);
        return result;
    }

    @Override
    public ServiceResult login(AccountLoginVo accountLoginVo) {

        ServiceResult result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountLoginVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(AccountImplLoginEnum.KEY_EMPTY);
            return result;
        }

        Soft soft = softMapper.selectById(accountLoginVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(AccountImplLoginEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(AccountImplLoginEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        }

        //判断用户不存在直接返回
        Integer selectCount = super.baseMapper.selectCount(new QueryWrapper<Account>().eq("username",
                accountLoginVo.getUsername()).eq("soft_id",accountLoginVo.getSoftId()));
        if (selectCount < 1) {
            result.setCode(AccountImplLoginEnum.ACCOUNT_EMPTY);
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountLoginVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountLoginVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(AccountImplLoginEnum.KEY_ERROR);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(AccountImplLoginEnum.KEY_ERROR);
            return result;
        } else if (password.length() > 10 || password.length() < 5) {
            result.setCode(AccountImplLoginEnum.PASSWORD_LENGTH_ERROR);
            return result;
        }

        Account account = super.baseMapper.selectOne(new QueryWrapper<Account>().eq("username",
                accountLoginVo.getUsername()).eq("password",password).eq("soft_id",accountLoginVo.getSoftId()));
        if (account == null) {
            result.setCode(AccountImplLoginEnum.PASSWORD_ERROR);
            return result;
        }

        //只支持单机进行机器码控制打开软件
        if (soft.getDosingStrategy() == SoftDosingStrategy.SINGLE.getStatusCode()) {
            QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>().eq("username",
                    accountLoginVo.getUsername()).eq("password",password).eq("soft_id",accountLoginVo.getSoftId());
            queryWrapper = queryWrapper.eq("code",code);
            Account accountQ = super.baseMapper.selectOne(queryWrapper);
            if (accountQ == null) {
                result.setCode(AccountImplLoginEnum.LOGIN_ERROR);
                return result;
            }
        }

        if (account.getBlacklist() == AccountBlackList.YES.getStatusCode()) {
            result.setCode(AccountImplLoginEnum.ACCOUNT_BLACKLIST);
            return result;
        }

        if (soft.getServiceStatus() == SoftServiceStatus.CHARGE.getStatusCode()) {
            String cardId = account.getCardId();
            if (StrUtil.hasEmpty(cardId)) {
                result.setCode(AccountImplLoginEnum.ACCOUNT_NOT_BOUND_CARD);
                return result;
            }
            Card card = cardMapper.selectById(cardId);
            if (card == null) {
                result.setCode(AccountImplLoginEnum.ACCOUNT_NOT_BOUND_CARD);
                return result;
            } else if (card.getClosure() == CardClosure.YES.getStatusCode()) {
                result.setCode(AccountImplLoginEnum.CARD_CLOSURE);
                return result;
            }
            long totalTime = card.getEndDate() - System.currentTimeMillis();
            if (totalTime < 1) {
                result.setCode(AccountImplLoginEnum.CARD_PAST_DUE);
                return result;
            }
        }

        //查询ip信息
        String addressByIp = "";
        try {
            addressByIp = baiduMapApiServiceL.getIpInfo(accountLoginVo.getIp());
        } catch (Exception e) {
            result.setCode(AccountImplLoginEnum.BAIDU_API_ERROR);
            return result;
        }

        AccountLoginLog accountLoginLog = new AccountLoginLog();
        accountLoginLog.setAccountId(account.getId());
        accountLoginLog.setIp(accountLoginVo.getIp());
        accountLoginLog.setIpInfo(addressByIp);
        accountLoginLog.setSoftId(accountLoginVo.getSoftId());
        accountLoginLogMapper.insert(accountLoginLog);

        result.setCode(AccountImplLoginEnum.LOGIN_SUCCESS);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult bindingCard(AccountBindingCardVo accountBindingCardVo) {

        ServiceResult result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountBindingCardVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(AccountImplBindingCardEnum.KEY_EMPTY);
            return result;
        }

        Soft soft = softMapper.selectById(accountBindingCardVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(AccountImplBindingCardEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(AccountImplBindingCardEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.FREE.getStatusCode()) {
            result.setCode(AccountImplBindingCardEnum.SOFT_FREE);
            return result;
        }

        //判断用户不存在直接返回
        Integer selectCount = super.baseMapper.selectCount(new QueryWrapper<Account>().eq("username",
                accountBindingCardVo.getUsername()).eq("soft_id",accountBindingCardVo.getSoftId()));
        if (selectCount < 1) {
            result.setCode(AccountImplBindingCardEnum.ACCOUNT_EMPTY);
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountBindingCardVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountBindingCardVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(AccountImplBindingCardEnum.KEY_ERROR);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(AccountImplBindingCardEnum.KEY_ERROR);
            return result;
        } else if (password.length() > 10 || password.length() < 5) {
            result.setCode(AccountImplBindingCardEnum.PASSWORD_LENGTH_ERROR);
            return result;
        }

        Account account = super.baseMapper.selectOne(new QueryWrapper<Account>().eq("username",
                accountBindingCardVo.getUsername()).eq("password",password).eq("soft_id",accountBindingCardVo.getSoftId()));
        if (account == null) {
            result.setCode(AccountImplBindingCardEnum.PASSWORD_ERROR);
            return result;
        } else if (account.getBlacklist() == AccountBlackList.YES.getStatusCode()) {
            result.setCode(AccountImplBindingCardEnum.ACCOUNT_BLACKLIST);
            return result;
        }

        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_number",
                accountBindingCardVo.getCardNumber()));
        if (card == null) {
            result.setCode(AccountImplBindingCardEnum.CARD_EMPTY);
            return result;
        } else if (card.getUseStatus() == CardUseStatus.YES.getStatusCode()) {
            result.setCode(AccountImplBindingCardEnum.CARD_USE);
            return result;
        } else if (card.getClosure() == CardClosure.YES.getStatusCode()) {
            result.setCode(AccountImplBindingCardEnum.CARD_CLOSURE);
            return result;
        }

        CardType cardType = cardTypeMapper.selectById(card.getCardTypeId());
        if (cardType == null) {
            result.setCode(AccountImplBindingCardEnum.CARD_EMPTY);
            return result;
        } else if (!accountBindingCardVo.getSoftId().equals(cardType.getSoftId())) {
            result.setCode(AccountImplBindingCardEnum.SOFT_INCONSISTENCY);
            return result;
        }

        //开始使用时间和结束使用期间
        String now = DateUtil.now();
        Date date = DateUtil.parse(now);
        long startTime = date.getTime();
        long endTime = 0;
        DateTime dateTime = null;

        switch (cardType.getUnit()) {
            case 0:
                dateTime = DateUtil.offsetMinute(date, cardType.getValue());
                break;
            case 1:
                dateTime = DateUtil.offsetHour(date, cardType.getValue());
                break;
            case 2:
                dateTime = DateUtil.offsetDay(date, cardType.getValue());
                break;
            case 3:
                dateTime = DateUtil.offsetWeek(date, cardType.getValue());
                break;
            case 4:
                dateTime = DateUtil.offsetMonth(date, cardType.getValue());
                break;
            case 5:
                dateTime = DateUtil.offset(date, DateField.YEAR, cardType.getValue());
                break;
        }
        endTime = dateTime.getTime();

        Card cardUpdate = new Card();
        cardUpdate.setId(card.getId());
        cardUpdate.setStartDate(startTime);
        cardUpdate.setEndDate(endTime);
        cardUpdate.setUseStatus(1);
        cardUpdate.setSellStatus(1);
        cardUpdate.setAccountId(account.getId());
        int cardUpdateResult = cardMapper.updateById(cardUpdate);

        Account accountUpdate = new Account();
        accountUpdate.setId(account.getId());
        accountUpdate.setCardId(card.getId());
        accountUpdate.setCode(code);
        int accountUpdateResult = super.baseMapper.updateById(accountUpdate);

        if (cardUpdateResult > 0 && accountUpdateResult > 0) {
            result.setCode(AccountImplBindingCardEnum.BINDING_CARD_SUCCESS);
            return result;
        }

        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        result.setCode(AccountImplBindingCardEnum.BINDING_CARD_ERROR);
        return result;
    }

    @Override
    public ServiceResult bindingCode(AccountBindingCodeVo accountBindingCodeVo) {

        ServiceResult result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountBindingCodeVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(AccountImplBindingCodeEnum.KEY_EMPTY);
            return result;
        }

        Soft soft = softMapper.selectById(accountBindingCodeVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(AccountImplBindingCodeEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(AccountImplBindingCodeEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        } else if (soft.getChangeStrategy() == SoftChangeStrategy.NO.getStatusCode()) {
            result.setCode(AccountImplBindingCodeEnum.SOFT_NO_CHANGE);
            return result;
        }

        //判断用户不存在直接返回
        Integer selectCount = super.baseMapper.selectCount(new QueryWrapper<Account>().eq("username",
                accountBindingCodeVo.getUsername()).eq("soft_id",accountBindingCodeVo.getSoftId()));
        if (selectCount < 1) {
            result.setCode(AccountImplBindingCardEnum.ACCOUNT_EMPTY);
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountBindingCodeVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountBindingCodeVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(AccountImplBindingCodeEnum.KEY_ERROR);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(AccountImplBindingCodeEnum.KEY_ERROR);
            return result;
        } else if (password.length() > 10 || password.length() < 5) {
            result.setCode(AccountImplBindingCodeEnum.PASSWORD_LENGTH_ERROR);
            return result;
        }

        Account account = super.baseMapper.selectOne(new QueryWrapper<Account>().eq("username",
                accountBindingCodeVo.getUsername()).eq("password",password).eq("soft_id",accountBindingCodeVo.getSoftId()));
        if (account == null) {
            result.setCode(AccountImplBindingCodeEnum.PASSWORD_ERROR);
            return result;
        } else if (account.getBlacklist() == AccountBlackList.YES.getStatusCode()) {
            result.setCode(AccountImplBindingCodeEnum.ACCOUNT_BLACKLIST);
            return result;
        }

        Account accountUpdate = new Account();
        accountUpdate.setId(account.getId());
        accountUpdate.setCode(code);
        int updateById = super.baseMapper.updateById(accountUpdate);
        if (updateById > 0) {
            result.setCode(AccountImplBindingCodeEnum.BINDING_CODE_SUCCESS);
            return result;
        }

        result.setCode(AccountImplBindingCodeEnum.BINDING_CODE_ERROR);
        return result;
    }

    @Override
    public ServiceResult updatePassword(AccountUpdatePasswordVo accountUpdatePasswordVo) {

        ServiceResult result = new ServiceResult<>();

        Soft soft = softMapper.selectById(accountUpdatePasswordVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(AccountImplUpdatePasswordEnum.SOFT_EMPTY);
            return result;
        } else if (soft.getServiceStatus() == SoftServiceStatus.CLOSE.getStatusCode()) {
            result.setCode(AccountImplUpdatePasswordEnum.SOFT_CLOSE);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        }

        Account accountF = super.baseMapper.selectOne(new QueryWrapper<Account>().eq("username",
                accountUpdatePasswordVo.getUsername()).eq("soft_id",accountUpdatePasswordVo.getSoftId()));
        if (accountF == null) {
            result.setCode(AccountImplUpdatePasswordEnum.ACCOUNT_EMPTY);
            return result;
        }

        Account account = super.baseMapper.selectOne(new QueryWrapper<Account>().eq("username",
                accountUpdatePasswordVo.getUsername()).eq("security_code",accountUpdatePasswordVo.getSecurityCode()));
        if (account == null) {
            result.setCode(AccountImplUpdatePasswordEnum.SECURITY_CODE_ERROR);
            return result;
        } else if (account.getBlacklist() == AccountBlackList.YES.getStatusCode()) {
            result.setCode(AccountImplUpdatePasswordEnum.ACCOUNT_BLACKLIST);
            return result;
        }

        int updatePassword = super.baseMapper.updatePassword(accountUpdatePasswordVo);
        if (updatePassword > 0) {
            result.setCode(AccountImplUpdatePasswordEnum.UPDATE_PASSWORD_SUCCESS);
            return result;
        }

        result.setCode(AccountImplUpdatePasswordEnum.UPDATE_PASSWORD_ERROR);
        return result;
    }

}
