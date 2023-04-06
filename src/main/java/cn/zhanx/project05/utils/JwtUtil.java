package cn.zhanx.project05.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    //有效期一个小时
    private static final Long JWT_TTL = 60*60*1000L;

    private static final String JWT_KEY = "itlils";

    public static String getUUID(){
        String token= UUID.randomUUID().toString().replace("-","");
        return token;
    }

    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }

    /**
     * 生成jwt
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String subject,Long ttlMillis){
        JwtBuilder jwtBuilder = getJwtBuilder(subject, ttlMillis, getUUID());
        return jwtBuilder.compact();

    }

    private static JwtBuilder getJwtBuilder(String subject,Long ttlMillis,String uuid){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey=generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now=new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMills=nowMillis+ttlMillis;
        Date expDate=new Date(expMills);
        return Jwts.builder()
                .setId(uuid) //设置jwt编码
                .setSubject(subject) //设置jwt主题
                .setIssuer("ydlclass")
                .setIssuedAt(now) //设置签发日期
                .setExpiration(expDate) //设置过期时间
                .signWith(SignatureAlgorithm.HS256, secretKey);
    }

    private static SecretKey generalKey() {
        byte[] decode = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKeySpec key = new SecretKeySpec(decode, 0, decode.length, "AES");
        return key;
    }

    public static void main(String[] args) throws Exception {
        String jwt = JwtUtil.createJWT("800012",null);
        System.out.println(jwt);
        Claims claims = JwtUtil.parseJWT(jwt);
        System.out.println(claims);
        System.out.println(getUUID());


    }

}