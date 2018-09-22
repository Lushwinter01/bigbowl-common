package com.bigbowl.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Title: SignUtil
 * @Description: 签名与验签Util
 * @return String
 * @author Leon
 * @date 2018年9月12日上午10:51:02
 */
public class SignUtils {
    private static final String ALGORITHM_HMACMD5 = "HmacMD5";
    private static final String ALGORITHM_HMACSHA1 = "HmacSHA1";
    private static final String ALGORITHM_HMACSHA256 = "HmacSHA256";
    private static final String ALGORITHM_HMACSHA512 = "HmacSHA512";

    /**
     * @Title: getAppKey
     * @Description:生成一个AppKey
     * @return String
     * @date 2018年9月12日上午10:51:46
     */
    public static String getAppKey() {
        String appKey = "";
        try {
            return byteArrayToHexString(getSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appKey;
    }

    /**
     * @Title: getAppSecret
     * @Description: 生成一个密钥
     * @return String
     * @date 2018年9月12日上午11:00:03
     */
    public static String getAppSecret() {
        String appSecret = "";
        try {
            return byteArrayToHexString(getSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appSecret;
    }

    /**
     * @Title: getSecretKey
     * @Description: 生成一个SecretKey
     * @return
     * @throws Exception byte[]
     * @date 2018年9月12日上午11:34:43
     */
    public static byte[] getSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_HMACSHA256); // 可填入 HmacMD5 HmacSHA1，HmacSHA256 等
        SecretKey key = keyGenerator.generateKey();
        byte[] keyBytes = key.getEncoded();
        return keyBytes;
    }

    /**
     * @Title: byteArrayToHexString
     * @Description:将加密后的字节数组转换成字符串
     * @param b
     * @return String
     * @date 2018年9月12日上午11:40:32
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * @Title: sign
     * @Description: 签名
     * @return String
     * @author Leon
     * @date 2018年9月12日上午11:00:56
     */
    public static String sign(String message, String secret) {
        return encript_HMAC(message, secret, ALGORITHM_HMACSHA256);
    }

    // 签名和验证签名的逻辑是相同的，只是多两个比较
    public static boolean verifySign() {
        return false;

    }

    /**
     * HMAC 签名
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密签名后字符串
     */
    private static String encript_HMAC(String message, String secret, String encriptAlgorithm) {
        String hash = "";
        try {
            Mac encript_HMAC = Mac.getInstance(encriptAlgorithm);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), encriptAlgorithm);
            encript_HMAC.init(secret_key);
            byte[] bytes = encript_HMAC.doFinal(message.getBytes());
            // String hash1 =
            // Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
            // System.out.println(hash1);
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println(encriptAlgorithm + " Error :" + e.getMessage());
        }
        return hash;
    }

    /**
     * @Title: getDictSortString
     *
     * @Description: 获取字典排序后的字符串
     * @param map
     * @return String
     * @date 2018年9月12日下午10:33:11
     */
    public static String getDictSortString(Map<String, Object> map) {
        if (map == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        Collection<String> keyset = map.keySet();

        List<String> list = new ArrayList<String>(keyset);

        Collections.sort(list); // 默认是升序

        // 拼接字符串
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            result.append("=");
            result.append(String.valueOf(map.get(list.get(i))));
            result.append("&");
        }
        // 移除最后一个 凭借服

        return result.substring(0, result.length() - 1);
    }

    public static void main(String[] args) throws Exception {
        // 生成key:
        String appKey = SignUtils.getAppKey();
        System.out.println("AppKey:" + appKey);
        // 生成密钥
        String appSecret = SignUtils.getAppSecret();
        System.out.println("AppSecret:" + appSecret);
        // 签名
        String message = "api_key=d554d071885feeaa05f4087e93c9409d&api_time=1535598970&appkey="+appKey;
        System.out.println(message);
        String signature = SignUtils.sign(message, appSecret);
        System.out.println("签名结果：" + signature);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appKey", "13283f3405c9cf7212e8396a3a8f36391d3e1a7f17d5bcc5f5");
        map.put("channel", "aliq");
        map.put("timestamp", 1511946622616L);
        map.put("traceId", "123456654321");
        map.put("version", "1.0");
        map.put("brandCode", "01");
        String sortedString = SignUtils.getDictSortString(map);
        System.out.println(sortedString);

        String signature1 = SignUtils.sign(sortedString, "d1402da39de131ce30eb7ccdd98ddc86ec00cfa25b6e0b48fe");
        System.out.println(signature1);



    }

}
