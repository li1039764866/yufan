
package org.yufan.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.yufan.bean.User;
import org.yufan.common.JSONUtils;
import org.yufan.service.JedisService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈访问需要登录的网站被拦截，没有登录则转到登录页面〉
 * @author 龙
 * @create 2018/8/23 22:01
 * @since 1.0.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisService jedisService;
    //与SSO中字符串对应，不要轻易更改
    private static final String USER_LOGIN="gfsdgvafvqweac:";
    private Logger LOGGER=LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                 HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取用户登陆后的令牌
        Cookie[] cookies=httpServletRequest.getCookies();
        String token=null;
        for (Cookie cookie:cookies) {//解析cookie
            if("token".equals(cookie.getName()))  token=cookie.getValue();
        }
        if(StringUtils.isEmpty(token)){//查找令牌，没有令牌则返回登录页面
            LOGGER.info("没有查找到令牌，返回登录页面");
            httpServletResponse.sendRedirect("/user/login.html");
            return false;
        }


        User user=JSONUtils.jsonToPojo(jedisService.get(USER_LOGIN+token),User.class);
        if(user==null)  {
            LOGGER.info("令牌无效或过期，返回登录页面");
            httpServletResponse.sendRedirect("/user/login.html");
            return false;
        }
        //用户登录状态有效，验证通过，转到欲访问的页面
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
               HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}