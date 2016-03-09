package com.paanxis.mojoy.app.util;

import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.util.*;

/**
 * 加密.
 * Created by zhengxiaoyao0716 on 2016/1/25.
 */
public enum Encryption {
    INSTANCE("This is a secret key for Mojoy app.");

    private final String key;
    Encryption(String key) { this.key = key; }
    /**
     * 验证签名.
     * <p>{@code return verify(sign, content, null);}</p>
     * @param sign 待校验的签名
     * @param content 要加密的Json
     * @return 校验结果
     */
    public boolean verify(String sign, JSONObject content) { return verify(sign, content, null); }
    /**
     * 验证签名.
     * @param sign 待校验的签名
     * @param content 要加密的Json
     * @param ruleOut 要排除的键
     * @return 校验结果
     */
    public boolean verify(String sign, JSONObject content, List<String> ruleOut) { return sign.equals(sign(content, ruleOut)); }

    /**
     * 生成签名.
     * <p>{@code return sign(content, null);}</p>
     * @param content 要加密的Json
     * @return 生成的密文
     */
    public String sign(JSONObject content) { return sign(content, null); }
    /**
     * 生成签名.
     * @param content 要加密的Json
     * @param ruleOut 要排除的键
     * @return 生成的密文
     */
    public String sign(JSONObject content, List<String> ruleOut)
    {
        //取出所有的键
        List<String> keyList = new ArrayList<String>();
        Iterator iterator = content.keys();
        while (iterator.hasNext())
            keyList.add(iterator.next().toString());

        //排除指定的键
        if (ruleOut != null) for (String  ruleOutKey : ruleOut)
            keyList.remove(ruleOutKey);

        //排序
        Collections.sort(keyList, new Comparator<String>() {
            final Collator collator = Collator.getInstance(Locale.CHINA);
            @Override
            public int compare(String s, String t1) {
                return collator.compare(s, t1);
            }
        });
        String sortedValues = "";
        for (String key : keyList)
            sortedValues  += content.optString(key);
        //加密
        return hmacSha256Hex(sortedValues);
    }

    /**
     * HMAC-SHA256算法加密再转化小写形式的十六进制字符串.
     * @param message 需要加密的信息
     * @return 加密后的密文
     */
    public String hmacSha256Hex(String message) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (mac == null) return null;
        try {
            mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        char[] hexChars = encodeHex(mac.doFinal(message.getBytes()));
        return new String(hexChars);
    }

    /**
     * 编码成小写形式十六进制字符串.
     * @param data 要编码的数据
     * @return 编码后的结果
     */
    public char[] encodeHex(final byte[] data) {
        char[] toDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
}