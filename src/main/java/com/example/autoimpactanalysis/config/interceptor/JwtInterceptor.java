package com.example.autoimpactanalysis.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.autoimpactanalysis.common.Constants;
import com.example.autoimpactanalysis.entity.User;
import com.example.autoimpactanalysis.exception.ServiceException;
import com.example.autoimpactanalysis.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: JwtInterceptor
 * @Author: kevin
 * @Date: 2022/6/14 09:48
 * @Description: 拦截器
 * @Version: V1.0
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request:"+request.getMethod());
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0); //TokenUtils中的载荷
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }
        //根据token中的userid查询数据库
        User user = userService.getById(userId);

        //不从数据库验证用户
        /*User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setNickname("管理员");*/

        if (user == null) {
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        // OPTIONS请求类型直接返回不处理
        if ("OPTIONS".equals(request.getMethod())){
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
