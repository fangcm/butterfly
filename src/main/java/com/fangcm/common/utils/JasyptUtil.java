package com.fangcm.common.utils;

import com.fangcm.common.constant.SecurityConstant;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Created by FangCM on 2018/5/23.
 */
public class JasyptUtil {
    /**
     * Jasypt生成加密结果
     *
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待加密值
     * @return
     */
    public static String encryptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.encrypt(value);
        return result;
    }

    /**
     * Jasypt生成加密结果
     *
     * @param value 待加密值
     * @return
     */
    public static String encryptPwd(String value) {
        return encryptPwd(SecurityConstant.JWT_SIGN_KEY, value);
    }

    /**
     * 解密
     *
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待解密密文
     * @return
     */
    public static String decryptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        String result = encryptor.decrypt(value);
        return result;
    }

    /**
     * 解密
     *
     * @param value 待解密密文
     * @return
     */
    public static String decryptPwd(String value) {
        return decryptPwd(SecurityConstant.JWT_SIGN_KEY, value);
    }

    private static SimpleStringPBEConfig cryptor(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    public static void main(String[] args) {

        //加密
        System.out.println(encryptPwd("123456.xhy"));
        //解密
        System.out.println(decryptPwd("F4B0s6u9xcDw3V+P0qC4CA=="));
    }
}
