package com.orange.verify.adminweb.realm;

import com.alibaba.dubbo.config.annotation.Reference;
import com.orange.verify.api.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {

    @Reference
    private UserService userService;

    /**
     * 权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        set.add("role");
        info.addRoles(set);
        return info;
    }

    /**
     * 登陆
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        int verifyUser = userService.verifyUser(username, password);
        if (verifyUser > 0) {
            return new SimpleAuthenticationInfo(username,password,getName());
        }

        throw new AuthenticationException();
    }
    
}
