package com.lkmotion.yesincar.service;

/**
 * @author LiZhaoTeng
 **/
public interface AuthService {
    /**
     * 生成验证码
     *
     */
    String createToken(String phoneNum);
    /**
     * 检查验证码
     */
    String checkToken(String token) throws Exception;

    public void deleteToken(String subject);
}
