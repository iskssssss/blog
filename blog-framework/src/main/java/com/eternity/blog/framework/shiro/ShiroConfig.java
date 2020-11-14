package com.eternity.blog.framework.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.eternity.blog.common.constants.ShiroConstants;
import com.eternity.blog.common.utils.SpringUtils;
import com.eternity.blog.common.utils.StringUtils;
import com.eternity.blog.framework.shiro.session.OnlineSessionFactory;
import com.eternity.blog.framework.shiro.web.filter.LogoutFilter;
import com.eternity.blog.framework.shiro.web.filter.SyncOnlineSessionFilter;
import com.eternity.blog.framework.shiro.web.session.OnlineWebSessionManager;
import com.eternity.blog.framework.shiro.session.OnlineSessionDAO;
import com.eternity.blog.framework.shiro.web.filter.OnlineAccessControlFilter;
import com.eternity.blog.framework.shiro.web.session.ESessionValidationScheduler;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.PropertySource;

import javax.naming.ConfigurationException;
import javax.servlet.Filter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/10 19:10
 */
@Configuration
public class ShiroConfig {
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    @Value("${shiro.user.logoutUrl}")
    private String logoutUrl;

    @Value("${shiro.cookie.domain}")
    private String domain;

    @Value("${shiro.cookie.path}")
    private String path;

    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    @Value("${shiro.session.expireTime}")
    private int expireTime;

    public EhCacheManager getEhCacheManager() {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("blog");
        EhCacheManager ehCacheManager = new EhCacheManager();
        if (StringUtils.isNull(cacheManager)){
            ehCacheManager.setCacheManager(new net.sf.ehcache.CacheManager());
            return ehCacheManager;
        }
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    protected InputStream getCacheManagerConfigFile() throws ConfigurationException {
        String file = "classpath:ehcache/ehcache-shiro.xml";
        InputStream inputStream = null;
        try{
            inputStream = ResourceUtils.getInputStreamForPath(file);
            byte[] b = IOUtils.toByteArray(inputStream);
            return new ByteArrayInputStream(b);
        } catch (IOException e) {
            throw new ConfigurationException("");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * Shiro 过滤器配置
     *
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //认证失败 则跳转指定页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 认证失败 则跳转指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/login");

        //设置静态资源匿名访问
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/medias/**", "anon");
        filterChainDefinitionMap.put("/mod/**", "anon");

        filterChainDefinitionMap.put("/main", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
//        filterChainDefinitionMap.put("/register", "authc");

        filterChainDefinitionMap.put("/system/**", "user");
        filterChainDefinitionMap.put("/logout", "logout");

        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("accessControlFilter", accessControlFilter());
        filters.put("syncOnlineSessionFilter", syncOnlineSessionFilter());
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);
        filterChainDefinitionMap.put("/**", "accessControlFilter,syncOnlineSessionFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 自定义用户退出过滤器
     *
     * @return 退出过滤器
     */
    private LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLogoutUrl(logoutUrl);
        return logoutFilter;
    }

    /**
     * 自定义访问过滤器
     *
     * @return 访问过滤器
     */
    @Bean
    public OnlineAccessControlFilter accessControlFilter() {
        return new OnlineAccessControlFilter();
    }

    /**
     * 自定义同步过滤器
     *
     * @return 同步过滤器
     */
    @Bean
    public SyncOnlineSessionFilter syncOnlineSessionFilter() {
        return new SyncOnlineSessionFilter();
    }

    /**
     * 安全管理器
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义 Realm
     *
     * @return UserRealm
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 自定义会话管理器
     *
     * @return OnlineSessionManager
     */
    @Bean(name = "sessionManager")
    public OnlineWebSessionManager sessionManager() {
        OnlineWebSessionManager sessionManager = new OnlineWebSessionManager();
        // 删除过期 Session
        sessionManager.setDeleteInvalidSessions(true);
        // 删除 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 設置Session过期时间
        sessionManager.setGlobalSessionTimeout(expireTime * ShiroConstants.MILLIS_PER_MINUTE);
        // 定时检查 Session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 自定义 Session 会话
        sessionManager.setSessionFactory(sessionFactory());
        // 自定义 SessionDAO
        sessionManager.setSessionDAO(sessionDAO());
        // 自定义无效session定时调度器
        sessionManager.setSessionValidationScheduler(SpringUtils.getBean(ESessionValidationScheduler.class));
        return sessionManager;
    }

    /**
     * 自定义sessionDAO会话
     *
     * @return OnlineSessionDAO
     */
    @Bean
    public OnlineSessionDAO sessionDAO() {
        return new OnlineSessionDAO();
    }

    /**
     * 自定义 Session 会话
     *
     * @return OnlineSessionFactory
     */
    @Bean
    public OnlineSessionFactory sessionFactory() {
        return new OnlineSessionFactory();
    }

    /**
     * 记住我
     *
     * @return CookieRememberMeManager
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager meManager = new CookieRememberMeManager();
        meManager.setCookie(simpleCookie());
        meManager.setCipherKey(Base64.decode(ShiroConstants.CIPHER_KEY));
        return meManager;
    }

    /**
     * Cookie 设置
     *
     * @return SimpleCookie
     */
    public SimpleCookie simpleCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * ShiroConstants.SECOND_PER_DAY);
        return cookie;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
//    @Bean
//    public ShiroDialect shiroDialect() {
//        return new ShiroDialect();
//    }
}
