package com.example.logistics_robot_manager.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    protected static final Log log = LogFactory.getLog(RSAUtil.class);
    private static final String KEY_RSA_TYPE="RSA";
    /**
     * 密钥对长度。
     * 数值越大，能加密的内容就越长。本项目中仅使用RSA算法对最大长度为20个字符的密码字段进行加解密，因此将该值设为1024位
     * 注：JDK1.8 151版本之前的密钥对长度最长只能为1024位
     */
    private static final int KEY_SIZE = 1024;
    public static final String PUBLIC_KEY_NAME = "publicKey";
    public static final String PRIVATE_KEY_NAME = "privateKey";
    /**
     * cipher 实例化时的 加密算法/反馈模式/填充方案。ECB 表示无向量模式
     */
    private static final String Algorithm_RSA_TYPE_ALL = "RSA/ECB/PKCS1Padding";

    /**
     * 生成RSA密钥对
     * @return 返回包含了公私钥Base64编码的map集合，公钥键为"publicKey"，私钥键为"privateKey”
     */
    public static Map<String,String> generateRASKeyPair(){
        Map<String,String> keyMap=new HashMap<>(); // Map存放公私密钥的Base64位加密字符串
        try {
            // 生成密钥对
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_RSA_TYPE);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair=keyPairGenerator.generateKeyPair();
            //获取公钥秘钥
            String publicKeyValue = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKeyValue = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            keyMap.put(PUBLIC_KEY_NAME,publicKeyValue);
            keyMap.put(PRIVATE_KEY_NAME,privateKeyValue);
        } catch (NoSuchAlgorithmException e) {
            log.error("当前JDK版本未找到RSA加密算法！");
            e.printStackTrace();
        }
        return keyMap;
    }

    /**
     * 私钥解密
     * @param sourceBase64Str 目标密文
     * @param privateKeyBase64 私钥
     * @return 目标明文
     */
    public static String decrypt(String sourceBase64Str,String privateKeyBase64){
        byte[] privateKeyBytes=Base64.getDecoder().decode(privateKeyBase64); // 获取编码私钥
        byte[] sourceBytes=Base64.getDecoder().decode(sourceBase64Str); // 获取加密内容
        String resultStr=null;
        // 使用获得的编码私钥创建新的 PKCS8EncodedKeySpec（KeySpec：密钥规范，底层密钥的透明表示）
        PKCS8EncodedKeySpec encodedKeySpec=new PKCS8EncodedKeySpec(privateKeyBytes);
        try{
            // 创建 KeyFactory 对象，用于转换指定算法(RSA)的私钥
            KeyFactory keyFactory=KeyFactory.getInstance(KEY_RSA_TYPE);
            // 将提供的密钥规范转换生成私钥对象
            PrivateKey privateKey= keyFactory.generatePrivate(encodedKeySpec);
            // cipher初始化，指定加密算法为RSA，模式为解密，并传入私钥
            Cipher cipher=Cipher.getInstance(Algorithm_RSA_TYPE_ALL);
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            // 对密文进行解密，密文长度不能超过1024字节，否则会抛出IllegalBlockSizeException，需要调大 KEY_SIZE 的值
            byte[] decodeResultBytes=cipher.doFinal(sourceBytes);
            resultStr=new String(decodeResultBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }
}
