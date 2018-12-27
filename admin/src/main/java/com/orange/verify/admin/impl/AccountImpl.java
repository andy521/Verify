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
import com.orange.verify.admin.transition.Transition;
import com.orange.verify.api.bean.*;
import com.orange.verify.api.model.ServiceResult;
import com.orange.verify.api.service.AccountService;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.AccountBindingCardVo;
import com.orange.verify.api.vo.open.AccountBindingCodeVo;
import com.orange.verify.api.vo.open.AccountLoginVo;
import com.orange.verify.api.vo.open.AccountRegisterVo;
import com.orange.verify.common.ip.BaiduIp;
import com.orange.verify.common.rsa.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

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
    private BaiduMapApiMapper baiduMapApiMapper;

    @Override
    public Page<AccountVo> page(AccountVo accountVo, Page page) {

        return page.setRecords(super.baseMapper.page(accountVo,page));
    }

    @Override
    public ServiceResult<String> getPublicKey() {

        ServiceResult<String> result = new ServiceResult<>();

        //rsa
        String publicKeyToBase64 = null;
        String privateKeyToBase64 = null;
        try {
            Map<String, Object> initKey = RsaUtil.initKey();
            publicKeyToBase64 = RsaUtil.getPublicKeyToBase64(initKey);
            privateKeyToBase64 = RsaUtil.getPrivateKeyToBase64(initKey);
        } catch (Exception e) {
            result.setCode(3);
            return result;
        }

        redis.save10Minutes(publicKeyToBase64,privateKeyToBase64);

        result.setCode(1);
        result.setData(publicKeyToBase64);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult<Integer> register(AccountRegisterVo accountRegisterVo) {

        ServiceResult<Integer> result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountRegisterVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(2);
            return result;
        }

        QueryWrapper<Account> username = new QueryWrapper<Account>().eq("username",
                accountRegisterVo.getUsername());
        Integer selectCount = super.baseMapper.selectCount(username);
        //用户名是否存在
        if (selectCount > 0) {
            result.setCode(6);
            return result;
        }

        Soft soft = softMapper.selectById(accountRegisterVo.getSoftId());
        if (soft == null) {
            result.setCode(3);
            return result;
        } else if (soft.getServiceStatus() == 2) {
            result.setCode(8);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        } else if (soft.getRegisterStatus() == 1) {
            result.setCode(9);
            result.setMsg(soft.getRegisteCloseMsg());
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        try {
            password = RsaUtil.decodeRsa(accountRegisterVo.getPassword(), privateKey);
        } catch (Exception e) {
            result.setCode(5);
            return result;
        }
        if (StrUtil.hasEmpty(password)) {
            result.setCode(5);
            return result;
        } else if (password.length() > 10) {
            result.setCode(7);
            return result;
        }

        //查询ip信息
        String addressByIp = "";
        try {
            addressByIp = getIpInfo(accountRegisterVo.getIp());
        } catch (Exception e) {
            result.setCode(4);
            return result;
        }

        //进行转型然后插入数据库
        accountRegisterVo.setPassword(password);

        Account account = transition.fromVo(accountRegisterVo);
        account.setCreateIpInfo(addressByIp);

        int insert = super.baseMapper.insert(account);

        AccountRegisterLog accountRegisterLog = new AccountRegisterLog();
        accountRegisterLog.setAccountId(account.getId());
        accountRegisterLog.setIp(account.getCreateIp());
        accountRegisterLog.setIpInfo(addressByIp);
        accountRegisterLogMapper.insert(accountRegisterLog);

        result.setCode(1);
        result.setData(insert);

        return result;
    }

    @Override
    public ServiceResult<Long> login(AccountLoginVo accountLoginVo) {

        ServiceResult<Long> result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountLoginVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(3);
            return result;
        }

        Soft soft = softMapper.selectById(accountLoginVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(4);
            return result;
        } else if (soft.getServiceStatus() == 2) {
            result.setCode(8);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountLoginVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountLoginVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(5);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(5);
            return result;
        } else if (password.length() > 10) {
            result.setCode(6);
            return result;
        }

        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>().eq("username",
                accountLoginVo.getUsername()).eq("password",password).eq("soft_id",accountLoginVo.getSoftId());

        //只支持单机 或者 是收费 进行机器码控制打开软件
        if (soft.getDosingStrategy() == 0 || soft.getServiceStatus() == 0) {
            queryWrapper = queryWrapper.eq("code",code);
        }
        Account account = super.baseMapper.selectOne(queryWrapper);
        if (account != null) {

            if (account.getBlacklist() == 1) {
                result.setCode(12);
                return result;
            }

            Card card = null;
            if (soft.getServiceStatus() == 0) {
                String cardId = account.getCardId();
                if (StrUtil.hasEmpty(cardId)) {
                    result.setCode(9);
                    return result;
                }
                card = cardMapper.selectById(cardId);
                if (card == null) {
                    result.setCode(9);
                    return result;
                } else if (card.getClosure() == 1) {
                    result.setCode(10);
                    return result;
                }
                long totalTime = card.getEndDate() - System.currentTimeMillis();
                if (totalTime < 1) {
                    result.setCode(11);
                    return result;
                }
            }

            //查询ip信息
            String addressByIp = "";
            try {
                addressByIp = getIpInfo(accountLoginVo.getIp());
            } catch (Exception e) {
                result.setCode(4);
                return result;
            }

            AccountLoginLog accountLoginLog = new AccountLoginLog();
            accountLoginLog.setAccountId(account.getId());
            accountLoginLog.setIp(accountLoginVo.getIp());
            accountLoginLog.setIpInfo(addressByIp);
            accountLoginLogMapper.insert(accountLoginLog);

            result.setCode(1);
            return result;
        }
        result.setCode(2);
        return result;
    }

    private String getIpInfo(String ip) throws Exception {

        BaiduMapApi single = baiduMapApiMapper.getSingle();
        if (single == null) {
            throw new Exception();
        }

        String ipInfo = "";
        if (!"127.0.0.1".equals(ip)) {
            try {
                ipInfo = BaiduIp.start(single.getAppkey())
                        .getAddressByIp(ip);
            } catch (Exception e) {
                throw new Exception();
            }
        }
        return ipInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult<Integer> bindingCard(AccountBindingCardVo accountBindingCardVo) {

        ServiceResult<Integer> result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountBindingCardVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(3);
            return result;
        }

        Soft soft = softMapper.selectById(accountBindingCardVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(4);
            return result;
        } else if (soft.getServiceStatus() == 2) {
            result.setCode(8);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountBindingCardVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountBindingCardVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(5);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(5);
            return result;
        } else if (password.length() > 10) {
            result.setCode(6);
            return result;
        }

        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>().eq("username",
                accountBindingCardVo.getUsername()).eq("password",password).eq("soft_id",accountBindingCardVo.getSoftId());
        Account account = super.baseMapper.selectOne(queryWrapper);
        if (account == null) {
            result.setCode(9);
            return result;
        } else if (account.getBlacklist() == 1) {
            result.setCode(12);
            return result;
        }
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_number",
                accountBindingCardVo.getCardNumber()));
        if (card == null) {
            result.setCode(10);
            return result;
        } else if (card.getUseStatus() == 1) {
            result.setCode(13);
            return result;
        } else if (card.getClosure() == 1) {
            result.setCode(14);
            return result;
        }
        CardType cardType = cardTypeMapper.selectById(card.getCardTypeId());
        if (cardType == null) {
            result.setCode(10);
            return result;
        } else if (!accountBindingCardVo.getSoftId().equals(cardType.getSoftId())) {
            result.setCode(4);
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
        cardMapper.updateById(cardUpdate);

        Account accountUpdate = new Account();
        accountUpdate.setId(account.getId());
        accountUpdate.setCardId(card.getId());
        accountUpdate.setCode(code);
        super.baseMapper.updateById(accountUpdate);

        result.setCode(1);
        return result;
    }

    @Override
    public ServiceResult<Integer> bindingCode(AccountBindingCodeVo accountBindingCodeVo) {

        ServiceResult<Integer> result = new ServiceResult<>();

        String privateKey = (String)redis.getByKey(accountBindingCodeVo.getPublicKey());
        //钥匙不存在直接返回
        if (StrUtil.hasEmpty(privateKey)) {
            result.setCode(3);
            return result;
        }

        Soft soft = softMapper.selectById(accountBindingCodeVo.getSoftId());
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(4);
            return result;
        } else if (soft.getServiceStatus() == 2) {
            result.setCode(8);
            result.setMsg(soft.getServiceCloseMsg());
            return result;
        } else if (soft.getChangeStrategy() == 1) {
            result.setCode(10);
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountBindingCodeVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountBindingCodeVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(5);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(5);
            return result;
        } else if (password.length() > 10) {
            result.setCode(6);
            return result;
        }

        QueryWrapper<Account> queryWrapper = new QueryWrapper<Account>().eq("username",
                accountBindingCodeVo.getUsername()).eq("password",password).eq("soft_id",accountBindingCodeVo.getSoftId());
        Account account = super.baseMapper.selectOne(queryWrapper);
        if (account == null) {
            result.setCode(9);
            return result;
        } else if (account.getBlacklist() == 1) {
            result.setCode(12);
            return result;
        }

        Account accountUpdate = new Account();
        accountUpdate.setId(account.getId());
        accountUpdate.setCode(code);
        super.baseMapper.updateById(accountUpdate);

        result.setCode(1);
        return result;
    }

}
