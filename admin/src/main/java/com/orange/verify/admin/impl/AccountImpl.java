package com.orange.verify.admin.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.orange.verify.admin.mapper.AccountMapper;
import com.orange.verify.admin.mapper.SoftMapper;
import com.orange.verify.admin.transition.Transition;
import com.orange.verify.api.bean.Account;
import com.orange.verify.api.bean.Soft;
import com.orange.verify.api.model.ServiceResult;
import com.orange.verify.api.service.AccountService;
import com.orange.verify.api.vo.AccountVo;
import com.orange.verify.api.vo.open.AccountRegisterVo;
import com.orange.verify.common.ip.BaiduIp;
import com.orange.verify.common.rsa.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service
public class AccountImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private RedisImpl redis;

    @Autowired
    private SoftMapper softMapper;

    @Autowired
    private Transition transition;

    private final String PREFIX_GETPUBLICKEY_IP = "impl.AccountImpl.getPublicKey.ip.";
    private final String PREFIX_REGISTER_IP = "impl.AccountImpl.register.ip.";

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
        //软件不存在直接返回
        if (soft == null) {
            result.setCode(3);
            return result;
        }

        //进行解密 >>> password 和 code >>> 解密成真实文本
        String password = null;
        String code = null;
        try {
            password = RsaUtil.decodeRsa(accountRegisterVo.getPassword(), privateKey);
            code = RsaUtil.decodeRsa(accountRegisterVo.getCode(), privateKey);
        } catch (Exception e) {
            result.setCode(5);
            return result;
        }
        if (StrUtil.hasEmpty(password,code)) {
            result.setCode(5);
            return result;
        } else if (password.length() > 10) {
            result.setCode(7);
            return result;
        }

        //查询ip信息
        String addressByIp = "";
        if (!"127.0.0.1".equals(accountRegisterVo.getIp())) {

            try {
                addressByIp = BaiduIp.start("m1ykK4CPuUVgZW3KDZO3lrvGzW2ZzYn6")
                        .getAddressByIp(accountRegisterVo.getIp());
            } catch (Exception e) {
                result.setCode(4);
                return result;
            }
        }

        //进行转型然后插入数据库
        accountRegisterVo.setPassword(password);
        accountRegisterVo.setCode(code);

        Account account = transition.fromVo(accountRegisterVo);
        account.setCreateIpInfo(addressByIp);

        int insert = super.baseMapper.insert(account);

        result.setCode(1);
        result.setData(insert);

        return result;
    }

}
