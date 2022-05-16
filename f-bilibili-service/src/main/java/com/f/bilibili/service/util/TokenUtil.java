package com.f.bilibili.service.util;

/**
 * @ClassName: TokenUtil
 * @Description: token工具类
 * @author: XU
 * @date: 2022年04月27日 14:13
 **/

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.f.bilibili.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

/**
 * <h1>token工具类</h1>
 */
public class TokenUtil {
    private static final String ISSUER = "签发者";

    /**
     * <h2>根据用户生id生成token并返回</h2>
     *
     * @param userId 用户Id
     * @return 生成的token
     */
    public String generateToken(Long userId) throws Exception {
        /**算法选择 RSA256，并传入公钥和私钥*/
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        /**根据当前时间创建过期时间*/
        Calendar calendar = Calendar.getInstance();
        /***/
        calendar.setTime(new Date());
        /**设置token失效时间为60秒*/
        calendar.add(Calendar.SECOND, 90000);
        /**返回生成的token*/
        return JWT.create()
                /**写入唯一标识id*/
                .withKeyId(String.valueOf(userId))
                /**写入签发者*/
                .withIssuer(ISSUER)
                /**写入过期时间*/
                .withExpiresAt(calendar.getTime())
                /**进行签名算法加密*/
                .sign(algorithm);
    }

    /**
     * <h2>传入加密的token解密得到用户id</h2>
     *
     * @param token 加密的token
     * @return 用户id
     */
    public static Long verifyToken(String token) {

        try {
            /**算法选择 RSA256，并传入公钥和私钥*/
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            /**传入验证解密算法**/
            JWTVerifier verifier = JWT.require(algorithm).build();
            /**解密得到加密前的字段*/
            DecodedJWT jwt = verifier.verify(token);
            /**获取加密前的用户id*/
            String userId = jwt.getKeyId();
            /**将获取到的用户id返回*/
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            /**token过期异常，返回code值和过期信息*/
            throw new ConditionException("555", "token过期");
        } catch (Exception e) {
            /**验证不通过*/
            throw new ConditionException("非法用户token!");
        }
    }
}
