package com.example.autoimpactanalysis.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.autoimpactanalysis.entity.User;
import com.example.autoimpactanalysis.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: TokenUtils
 * @Author: kevin
 * @Date: 2022/6/14 09:31
 * @Description: 生成token
 * @Version: V1.0
 */
@Slf4j
@Component
public class TokenUtils {

    private static IUserService staticUserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    public static String generatorToken(String userid, String password) {
        String token = "";
        token = JWT.create().withAudience(userid) // 将user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
        return token;
    }

    /**
     * @Auther: kevin
     * @param: null
     * @Return Type:
     * @date: 2022/4/6 17:22
     * @describe:获取当前登录的用户信息
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            log.info("token信息："+token);
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
