package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.service.AuthService;
import com.lkmotion.yesincar.service.TokenRedisService;
import com.lkmotion.yesincar.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * token创建
 *
 * @author LiZhaoTeng
 **/
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    /**
     * token有效期 60天
     */
    private final TokenRedisService tokenRedisService;
    private static final Integer EXP_HOURS = 24 * 60;

    @Autowired
    public AuthServiceImpl(TokenRedisService tokenRedisService) {this.tokenRedisService = tokenRedisService;}

    @Override
    public String createToken(String subject) {
        String jwtStr = JWTUtil.createJWT(subject, new Date());
        tokenRedisService.put(subject, jwtStr, EXP_HOURS);
        return jwtStr;
    }

    @Override
    public String checkToken(String token) {
        try {
            Claims claims = JWTUtil.parseJWT(token);
            if (claims != null) {
                String tokenT = tokenRedisService.get(claims.getSubject());
                log.info("缓存中的认证key:" + claims.getSubject() + ",value:" + tokenT);
                if (StringUtils.isNotEmpty(tokenT) && token.equals(tokenT)) {
                    log.info("验证Token是否失效 - Token仍有效 - 延长token有效期phone: " + claims.getSubject() + " token:" + tokenT);
                    tokenRedisService.expire(claims.getSubject(), EXP_HOURS);
                    return claims.getSubject();
                } else {
                    log.info("验证Token是否失效 - Token已失效 - token已过期 - token:" + token);
                    return "1";
                }
            } else {
                log.info("验证Token是否失效 - Token已失效 - token解析失败 - token:" + token);
                return "1";
            }
        } catch (Exception e) {
            log.error("验证Token是否失效 - 服务器异常", e);
            return "2";
        }
    }

    @Override
    public void deleteToken(String subject) {
        tokenRedisService.delete(subject);
    }

}
