package cn.zhanx.project05.utils;

import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtil {

    private static final String RSA_ALGORTHM = "RSA";
    /**
     * * 生成密钥对 *
     */
    public static RSAKeyPair generateKeyPair(final int keySize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORTHM);
        keyPairGen.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();

        String privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
        String publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());

        RSAKeyPair rsaKeyPair=new RSAKeyPair();
        rsaKeyPair.setPrivateKey(privateKey);
        rsaKeyPair.setPublicKey(publicKey);
        return rsaKeyPair;
    }

    @Data
    public static class RSAKeyPair{
        private String privateKey;
        private String publicKey;
    }

    /**
     * 公钥加密
     */
     public static String encryptWithBase64(String data, String publicKey) throws Exception {
         // 对公钥解密
         byte[] keyBytes = Base64.decodeBase64(publicKey);
         // 取公钥
         X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
         KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
         PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);

         // 对数据加密
         Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
         cipher.init(Cipher.ENCRYPT_MODE, key);

         return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
     }

     /**
     * 私钥解密
     */
     public static String decryptWithBase64(String data, String privateKey) throws Exception {
         Cipher cipher = Cipher.getInstance(RSA_ALGORTHM);
         byte[] keyBytes = Base64.decodeBase64(privateKey);
         PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
         KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
         PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
         cipher.init(Cipher.DECRYPT_MODE, key);
         // 对私钥解密
         return new String(cipher.doFinal(Base64.decodeBase64(data)));
     }

     /**
     * RSA 签名
     */
    public static String signBase64(String content, String privateKey, String algorithm) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
        PrivateKey key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(key);
        signature.update(content.getBytes());
        byte[] signed = signature.sign();
        return Base64.encodeBase64String(signed);
    }

    /**
     * 数字签名验证
     */
    public static boolean verifyBase64(String content, String sign, String publicKey, String algorithm)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
        PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(key);
        signature.update(content.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

}
