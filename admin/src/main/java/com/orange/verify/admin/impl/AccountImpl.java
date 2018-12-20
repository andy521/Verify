package com.orange.verify.admin.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.dubbo.config.annotation.Service;
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
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private RedisImpl redis;

    @Autowired
    private SoftMapper softMapper;

    @Autowired
    private Transition transition;

    @Override
    public Page<AccountVo> page(AccountVo accountVo, Page page) {

        return page.setRecords(super.baseMapper.page(accountVo,page));
    }

    @Override
    public ServiceResult<String> getPublicKey(String ip) {

        ServiceResult<String> result = new ServiceResult<>();

        ip = ip.replaceAll(":",".");

        //验证短时间内不能再次发送
        Map<String,Object> v = (HashMap<String, Object>) redis.getByKey(ip);
        if (v != null) {
            Long createDate = (Long) v.get("createDate");
            long totalTime = (System.currentTimeMillis() - createDate);
            //小于10000毫秒秒，不批发key出去
            if (totalTime < 10000) {
                result.setCode(2);
                return result;
            }
        }

        //rsa
        String publicKeyToBase64 = null;
        String privateKeyToBase64 = null;
        try {
            Map<String, Object> initKey = RsaUtil.initKey();
            publicKeyToBase64 = RsaUtil.getPublicKeyToBase64(initKey);
            privateKeyToBase64 = RsaUtil.getPrivateKeyToBase64(initKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redis.save10Minutes(publicKeyToBase64,privateKeyToBase64);

        HashMap<String, Object> map = new HashMap<>();
        map.put("ip",ip);
        map.put("createDate",System.currentTimeMillis());

        redis.save10Minutes(ip, map);

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
            e.printStackTrace();
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
