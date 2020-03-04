package com.goochou.p2b.admin.realm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import com.goochou.p2b.model.Resources;
import com.goochou.p2b.model.UserAdmin;
import com.goochou.p2b.service.UserAdminService;

/**
 * 自定义指定的shiro验证用户登陆的类
 *
 * @author Administrator
 */
@Component
public class MyRealm extends AuthorizingRealm {
	
	private static final Logger logger = Logger.getLogger(MyRealm.class);

    @Resource
    private UserAdminService adminService;

    /**
     * 拦截器，查询是否有权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Cache<Object, AuthorizationInfo> c = getAuthorizationCache();
        String currentUsername = (String) super.getAvailablePrincipal(principals);
        logger.info("[当前登录的用户名为：" + currentUsername + "]");
        if (c != null) {
            Object o = c.get(currentUsername);
            if (o != null) {
            	logger.info("===============当前用户权限已缓存，无需再次查询==================");
                AuthorizationInfo a = (AuthorizationInfo) o;
                // Collection<String> cols = a.getStringPermissions();
                // LogUtil.infoLogs("=================当前登录的用户的权限为=======================");
                // for (String s : cols) {
                // LogUtil.infoLogs(s);
                // }
                return a;
            }
        }

        List<String> permissionList = new ArrayList<String>();
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        try {
            UserAdmin userAdmin = adminService.getByUsername(currentUsername);
            List<Resources> resources = adminService.selectResourcesByAdminId(userAdmin.getId(), null, null, false);
            if (null != resources && !resources.isEmpty()) {
                for (Resources r : resources) {
                    permissionList.add(r.getPermission());
                }
            }
            // 获取当前用户的permission
            simpleAuthorInfo.addStringPermissions(permissionList);
            // LogUtil.infoLogs("[当前登录的用户的权限为：");
            // for (String str : permissionList) {
            // LogUtil.infoLogs(str);
            // }

            setCachingEnabled(true);
            setAuthorizationCachingEnabled(true);
            Map<Object, AuthorizationInfo> p = new HashMap<Object, AuthorizationInfo>();
            p.put(currentUsername, simpleAuthorInfo);
            Cache<Object, AuthorizationInfo> map = new MapCache<Object, AuthorizationInfo>(currentUsername, p);
            setAuthorizationCache(map);
        } catch (Exception e) {
        	logger.error(e);
        }
        return simpleAuthorInfo;
    }

    public void modifyCache() throws Exception {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        String currentUsername = (String) super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

        UserAdmin userAdmin = adminService.getByUsername(currentUsername);
        List<Resources> resources = adminService.selectResourcesByAdminId(userAdmin.getId(), null, null, false);
        List<String> permissionList = new ArrayList<String>();
        if (null != resources && !resources.isEmpty()) {
            for (Resources r : resources) {
                permissionList.add(r.getPermission());
            }
        }
        // 获取当前用户的permission
        simpleAuthorInfo.addStringPermissions(permissionList);
        // LogUtil.infoLogs("[当前登录的用户的权限为：");
        // for (String str : permissionList) {
        // LogUtil.infoLogs(str);
        // }
        Map<Object, AuthorizationInfo> p = new HashMap<Object, AuthorizationInfo>();
        p.put(currentUsername, simpleAuthorInfo);
        Cache<Object, AuthorizationInfo> map = new MapCache<Object, AuthorizationInfo>(currentUsername, p);
        setAuthorizationCache(map);
    }

    /**
     * 获取身份验证相关信息,登录调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        // 获取基于用户名和密码的令牌
        // 实际上这个authcToken是从UserAdminController里面currentUser.login(token)传过来的
        // 两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        logger.info("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        try {
            UserAdmin admin = new UserAdmin();
            admin.setUsername(token.getUsername());
            admin.setPassword(String.valueOf(token.getPassword()));
            admin.setLastLoginTime(new Date());
            admin.setLastLoginIp(token.getHost());
            Map<String, Object> map = adminService.login(admin);
            if ((Boolean) map.get("status")) {
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(admin.getUsername(), admin.getPassword(), admin.getUsername());
                this.setSession("user", map.get("msg"));
                return authcInfo;
            }
        } catch (Exception e) {
        	logger.error(e);
        }
        return null;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            logger.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
