package com.lkmotion.yesincar.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

/**
 * jwt
 * @author LiZhaoTeng
 **/
public class JWTUtil {


    private static String secret = "ko346134h_we]rg3in_yip1!";

    public static String createJWT(String subject, Date issueDate) {
        String compactJws = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
        return compactJws;
    }

    /**
     * 解密 jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        return claims;
    }


}
