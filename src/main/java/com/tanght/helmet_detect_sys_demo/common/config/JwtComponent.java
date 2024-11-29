package com.tanght.helmet_detect_sys_demo.common.config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
/**
 * @Title:
 * @Author Tanght 363993584@qq.com
 * @Date 2024/11/29 15:59
 * @description:
 */

@Component
public class JwtComponent {

    // 用于签名的密钥
    private final String SECRET_KEY = "19954/601/618";

    /**
     * 生成 JWT
     *
     * @param username 用户名
     * @param expirationInSeconds 有效期（秒）
     * @return 生成的 JWT 字符串
     */
    public String sign(String username, int expirationInSeconds) {
        // 签名算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        // 计算过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expirationInSeconds);
        Date expirationDate = calendar.getTime();

        // 生成 Token
        return JWT.create()
                .withClaim("username", username)  // 添加用户名到 Token
                .withIssuedAt(new Date())        // 签发时间
                .withExpiresAt(expirationDate)   // 过期时间
                .sign(algorithm);                // 签名
    }

    /**
     * 验证 JWT 的有效性
     *
     * @param token JWT 字符串
     * @return 验证通过返回 true，否则抛出异常
     * @throws JWTVerificationException 如果验证失败
     */
    public boolean verify(String token) {
        try {
            // 创建验证器
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();

            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // 去掉 "Bearer " 前缀
            }
            // 验证 Token
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token验证失败", e);
        }
    }

    /**
     * 提取 JWT 中的用户名
     *
     * @param token JWT 字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("username").asString();
    }

    /**
     * 提取 JWT 的过期时间
     *
     * @param token JWT 字符串
     * @return 过期时间
     */
    public Date getExpiresAt(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt();
    }
}
