package cn.zhanx.project05.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DesUtil {
    /**
     * 生成秘钥
     *
     * @return
     * @throws Exception
     */
    public static String generateKey() throws Exception {
        // 以DES的方式初始化Key生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);// 设置密钥的长度为56位
        // 生成一个Key
        SecretKey generateKey = keyGenerator.generateKey();
        // 转变为字节数组
        byte[] encoded = generateKey.getEncoded();
        // 生成密钥字符串
        return Hex.encodeHexString(encoded);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        // 再把我们的字符串转变为字节数组，可以用于另一方使用，验证
        byte[] decodeHex = Hex.decodeHex(key.toCharArray());
        // 生成密钥对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodeHex, "DES");

        // 获取加解密实例
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 初始化加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // 加密
        byte[] doFinal = cipher.doFinal(data.getBytes());
        return Hex.encodeHexString(doFinal);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        byte[] decodeHex = Hex.decodeHex(key.toCharArray());
        // 生成密钥对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodeHex, "DES");

        // 获取加解密实例
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // 初始化解密模式
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        // 解密
        byte[] doFinal2 = cipher.doFinal(Hex.decodeHex(data.toCharArray()));

        return new String(doFinal2);
    }

    /**
     * 生成秘钥
     *
     * @return
     * @throws Exception
     */
    public static String generateKeyBase64() throws Exception {
        // 以DES的方式初始化Key生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);// 设置密钥的长度为56位
        // 生成一个Key
        SecretKey generateKey = keyGenerator.generateKey();
        // 转变为字节数组
        byte[] encoded = generateKey.getEncoded();
        // 生成密钥字符串
        return Base64.encodeBase64String(encoded);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String data, String key) throws Exception {
        // 再把我们的字符串转变为字节数组，可以用于另一方使用，验证
        byte[] decodeHex = Base64.decodeBase64(key);
        // 生成密钥对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodeHex, "DES");

        // 获取加解密实例
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 初始化加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // 加密
        byte[] doFinal = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64String(doFinal);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBase64(String data, String key) throws Exception {
        byte[] decodeHex = Base64.decodeBase64(key);
        // 生成密钥对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodeHex, "DES");

        // 获取加解密实例
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // 初始化解密模式
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        // 解密
        byte[] doFinal2 = cipher.doFinal(Base64.decodeBase64(data));

        return new String(doFinal2);
    }


}
