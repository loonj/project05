package cn.zhanx.project05;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.zhanx.project05.domain.Banner;
import cn.zhanx.project05.service.BannerService;
import cn.zhanx.project05.utils.DesUtil;
import cn.zhanx.project05.utils.RSAUtil;
import cn.zhanx.project05.utils.SendMessage;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;

@SpringBootTest
class project05ApplicationTests {

    @Test
    void contextLoads() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis+10000);

        //生成jwt令牌
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666") //设置jwt编码
                .setSubject("hello world") //设置jwt主题
                .setIssuedAt(new Date()) //设置签发日期
                //.setExpiration(date) //设置过期时间
                .claim("userId","jinl")
                .claim("address","湖南津市")
                .signWith(SignatureAlgorithm.HS256, "ydlershe");

        //生成jwt
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);

        Claims ydlershe = Jwts.parser().setSigningKey("ydlershe").parseClaimsJws(jwtToken).getBody();
        System.out.println(ydlershe);

    }

    @Autowired
    private SendMessage sendMessge;

    @Test
    public void sendTest() throws InterruptedException {
        for (int i = 0; i < 5; i++){
            sendMessge.send(i);
            System.out.println("send message: " +i);
        }
        Thread.sleep(1000);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testBcript(){
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        System.out.println(encode1);
        System.out.println(encode2);

        boolean flag1 = passwordEncoder.matches("123456",encode1);
        boolean flag2 = passwordEncoder.matches("123456",encode2);
        System.out.println(flag1);
        System.out.println(flag2);

    }

    @Autowired
    private BannerService bannerService;

    private static final String DES_KEY="3CX3RUkLerk=";
    @Test
    void testDes() throws Exception {
        Banner banner = bannerService.getById(1);
        //加密
        String encrypt = DesUtil.encryptBase64(JSON.toJSONString(banner), DES_KEY);
        System.out.println(encrypt);
        //解密
        String decrypt = DesUtil.decryptBase64(encrypt, DES_KEY);
        System.out.println(decrypt);

    }

    @Test
    void testRsa() throws Exception{
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.generateKeyPair(1024);
        String privateKey = rsaKeyPair.getPrivateKey();
        String publicKey = rsaKeyPair.getPublicKey();

        System.out.println(privateKey);
        System.out.println(publicKey);
        //RSA对DES的秘钥进行加密
        String desKeyWithRsa = RSAUtil.encryptWithBase64(DES_KEY, publicKey);
        //用私钥进行DES的秘钥解密
        String desKey = RSAUtil.decryptWithBase64(desKeyWithRsa, privateKey);


        //用DES加密后的数据
        String dataWithEncrypt = "Oa9kScHPOpaelRq7atM7cyohYuGwjUCFIiQ79LK0rbvUsISCkaRgG5LL20dFu5Cm4JXhvT7Et8zXmAzqurz8W5/m8gMmeE7s6Ebft6NOlDZ524Y2ThVCdh4AmN0jWwAbDTSJFmWrynKIAHjI5b6gGgXXUWNzOfVYDb5IDCld/VB4+2rQUz8j9YaL+8dlSSktvwtXBoYMFAPAZkt8ZVTtfk+Q2LXHyfPdqGaX5YZrtYg=";

        //DES对报文进行解密
        String data = DesUtil.decryptBase64(dataWithEncrypt, desKey);

        Banner banner = JSON.parseObject(data, Banner.class);
        System.out.println(banner);

    }

    @Data
    private static class StandardBindCardReq implements Serializable {
        private static final long serialVersionUID = 1L;

        private String idNo;
        private String name;
        private String mobile;
        private String cardNo;
    }

    /**
     * 公共请求参数
     * 序号	参数名	中文名称	数据类型	是否必填	备注
     * 1	orgCode	合作机构编号	A64	M	合作方提供
     * 2	transDate	请求发送时间		M	yyyy-MM-dd HH:mm:ss
     * 3	transSeq	请求流水号		M	唯一请求流水
     * 4	charset	字符集		M	UTF-8
     * 5	version	接口版本号		M	当前版本固定值“1.0.0”
     * 6	data	请求参数		M	加密
     * 7	signature	签名		M	数据的签名
     * 8	desKey	Des秘钥		M	Rsa加密后的Des秘钥
     */
    @Data
    private static class StandardRequest implements Serializable {
        private static final long serialVersionUID = 1L;

        private Charset charset;
        private String data;
        private String desKey;
        private String signature;
        private String orgCode;
        private String transDate;
        private String transSeq;
        private String version;

    }

    //公钥加密、私钥解密、私钥签名、公钥验签
    //这是我们自己的
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1QtK9w1ye9rsGSz3bHI6gc3fqa3R1aso74maMZlN5Fqwf8GkNg3rTM8IQ0lw2EXW+hFBOE08/0DuZiyt4luD7wP15Lo3wRS+Dg0A0+vVjo1oN7WGCCrzU94kvVyj58PIYENWtn2iMz5DrY8usCud30UaSR+UM7TyISI4FRB50wIDAQAB";
    private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALPVC0r3DXJ72uwZLPdscjqBzd+prdHVqyjviZoxmU3kWrB/waQ2DetMzwhDSXDYRdb6EUE4TTz/QO5mLK3iW4PvA/XkujfBFL4ODQDT69WOjWg3tYYIKvNT3iS9XKPnw8hgQ1a2faIzPkOtjy6wK53fRRpJH5QztPIhIjgVEHnTAgMBAAECgYBfSC4nlibF1eH03pH4trj/Tu9p5ll2qKN7mQ9rZ4Q2xRtYERjkjM9rMNMxKZWr+KtocC7cQ5yJCSW6KuDZcOnTZhSVqewHlUwbsFKTsf8WL3OpfJ33TLv584neXHEVyU+5PCD4eLPA9Th35uo/AGCSaiWtdgV+aPgtGLkwDIm1gQJBAOGdshETxaj9nDGuZSSxDisjlH5TIhAGN/ijT5ZwNe430cj4XYuxEk99YttgqnkzodtNhiN6I1+GeeAL22aU5kMCQQDMDOm/K/5Oy8diHjtlfCBtsRE1RkmV6y18K87xMoomNmUXBqQqYnIoKLQAUTYx9WXzsChVwGut8Z4GHuD9NQ0xAkAgVuxZSk5A5OlorajN05tGT7lPSjdNuLZ5K1+THs58MQAfIIq4JU+MmVp8O6Tz7pS6zKf3meBMJwh9NvXiBGHlAkANMfCTqjahxc+46peQsnwuW7rvJQJxLWodk3+oXUpNukrDOGy5SPB05FzvgFDu3h2fqo6RLQZJk0xSvQBzn1ChAkAu/DOiduOl0/M6jBxLoHuY1CDBCTLlL+Rh1mHp531/g26codrfJSrHCEuKrqVFth+dewrIX+u5kAKn/uhXT6KM";

    private static final String CHANNEL_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkNQfjAxvoJ9alKEhs1pICRL53Xx+5ZC3ut/VJBlUruxJLUryAE60TKmsGdLvim/VkHlePH2p2xPlv3grodJ8sCZx+NJK9iOUwWD8tu/UXb+xLWBgZqrqZcoDkHpDK3BWIvcIgRSIixulI9q1487LulJ3ZPc17U01o55goLlFf2wIDAQAB";
    private static final String CHANNEL_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKQ1B+MDG+gn1qUoSGzWkgJEvndfH7lkLe639UkGVSu7EktSvIATrRMqawZ0u+Kb9WQeV48fanbE+W/eCuh0nywJnH40kr2I5TBYPy279Rdv7EtYGBmquplygOQekMrcFYi9wiBFIiLG6Uj2rXjzsu6Undk9zXtTTWjnmCguUV/bAgMBAAECgYB6Y6hYNIYFWEQs30R0Es3K8RABix3rIVb2+ZfR8O1kZc9x4t3qnD/A7DnPGwy7D0EseU0LuP+yPIgqMBZzQhEsIO2WQUVhyC8uVU8UfCZQb5NWg2QH6GhakP6kcQYNAact4yEKl0tX+0pRv34MBFSpNaQbnDVKjbCGu3kiCOB6WQJBAM5LoMyneBjKNJI8TEyjUfZSc5A/2LnD5su6kDV8l4ZbJOis/kAfr6HZQ2VaSwZ+362usCcET3LS6vdWmwtEiTcCQQDLxWJB0I7uxD1JbcIn/hEPJmA8+ZgsSJTC0/JLywW2/KRbK5CxtN+LFFhid688yDw74FaKkTn9l0x4Vz2UqKB9AkEArc0Nph4ZklkZPXLLnW2jafFBJ3CC+M7RLSb6w/U5+5niWJ0Wf5OydpsCqusNdmWBsWQRSG3bOZP1HXi7mxyctQJBAJ6QAK6AEMkhMSOTYhLms/WbAiZQfqZu8DtO2+s4l0DWBTrggp6VxaGe+kvdICJnGd/p6M24xmxbsGiwUj38na0CQALe2/0uIYrDxcjd7xlKOdK4qVJtldtJvv/Ux0TZGvwtSG2dIetSaJmJONfnciiZcmCBN3EDJmijOV2+lLmixG4=";

    @Test
    void test2() throws Exception{
        String desKey = DesUtil.generateKeyBase64();
        System.out.println("desKey--->" + desKey);
        StandardBindCardReq req = new StandardBindCardReq();
        req.setIdNo("13062319999999");
        req.setName("李潇");
        req.setMobile("17702738888");
        req.setCardNo("62270024701999999");

        String data= JSON.toJSONString(req);
        StandardRequest request = new StandardRequest();
        request.setCharset(Charsets.UTF_8);

        // 把请求数据用desKey进行加密
        request.setData(DesUtil.encryptBase64(data, desKey));
        //对desKey 用对方的公钥对desKey加密
        request.setDesKey(RSAUtil.encryptWithBase64(desKey, CHANNEL_PUBLIC_KEY));
        //对请求数据用我们的私钥进行签名
        request.setSignature(RSAUtil.signBase64(data, PRIVATE_KEY, "SHA1WithRSA"));
        request.setOrgCode("HF");
        request.setTransDate(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

        request.setTransSeq(UUID.randomUUID().toString());
        request.setVersion("1.0.0");

        System.out.println("请求:" + JSON.toJSONString(request));

        // 渠道方用他们自己的私钥对request.getDesKey()对解密得到 desKey
        String desKey2 = RSAUtil.decryptWithBase64(request.getDesKey(), CHANNEL_PRIVATE_KEY);
        // 再用desKey 解密request.getData()
        String data2 = DesUtil.decryptBase64(request.getData(), desKey2);
        System.out.println("解密后的内容：" + data2);
        // 签名验证数据是否一致性
        String sign = request.getSignature();
        System.out.println("验签结果--->" + RSAUtil.verifyBase64(data, sign, PUBLIC_KEY, "SHA1WithRSA"));

    }



}
