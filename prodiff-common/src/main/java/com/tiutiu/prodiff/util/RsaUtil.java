package com.tiutiu.prodiff.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

@Slf4j
public class RsaUtil {
    //签名算法名称
    private static final String RSA_KEY_ALGORITHM = "RSA";

    //标准签名算法名称
    private static final String RSA_SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String RSA2_SIGNATURE_ALGORITHM = "SHA256withRSA";
    //RSA密钥长度
    private static final int KEY_SIZE = 2048;
    public static String buildCacheKey(Long userId) {
        return "rsaKeyPair:" + userId.toString();
    }
    public static List<String> buildRsaKeyPair() {
        KeyPairGenerator keyGen;
        try{
            keyGen = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
        }catch (NoSuchAlgorithmException e){
            log.error("RSA密钥生成器初始化失败", e);
            return null;
        }
        // 初始化随机数生成器
        SecureRandom random = new SecureRandom();
        random.setSeed(System.currentTimeMillis());
        // 初始化密钥生成器
        keyGen.initialize(KEY_SIZE, random);
        KeyPair keyPair = keyGen.genKeyPair();
        // 公钥
        byte[] _publicKey = keyPair.getPublic().getEncoded();
        String publicKey = Base64.encodeBase64String(_publicKey);
        // 私钥
        byte[] _privateKey = keyPair.getPrivate().getEncoded();
        String privateKey = Base64.encodeBase64String(_privateKey);
        return List.of(privateKey, publicKey);
    }
    public static String encryptByPublicKey(String data, String publicKeyStr) throws Exception {
        var pubKey = Base64.decodeBase64(publicKeyStr);
        //创建X509编码密钥规范
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
        //返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        //根据X509编码密钥规范产生公钥对象
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        //根据转换的名称获取密码对象Cipher（转换的名称：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        //用公钥初始化此Cipher对象（加密模式）
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //对数据加密
        byte[] encrypt = cipher.doFinal(data.getBytes());
        //返回base64编码后的字符串
        return Base64.encodeBase64String(encrypt);
    }
    public static String decryptByPrivateKey(String data, String privateKeyStr) throws Exception{
        //Java原生base64解码
        byte[] priKey = Base64.decodeBase64(privateKeyStr);
        //创建PKCS8编码密钥规范
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        //返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        //根据PKCS8编码密钥规范产生私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //根据转换的名称获取密码对象Cipher（转换的名称：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        //用私钥初始化此Cipher对象（解密模式）
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //对数据解密
        byte[] decrypt = cipher.doFinal(Base64.decodeBase64(data));
        //返回字符串
        return new String(decrypt);
    }
}
