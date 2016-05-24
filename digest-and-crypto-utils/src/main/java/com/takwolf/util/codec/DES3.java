/*
 * Copyright 2015-2016 TakWolf
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.takwolf.util.codec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.Base64;

public class DES3 {

    private static final String IV = "01234567";
    private static final String CHARSET = "utf-8";

    /**
     * 加密
     *
     * @param iv        加密向量
     * @param secretKey 密钥
     * @param plainText 原文
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String iv, String secretKey, String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(CHARSET));
        return Base64.getEncoder().encodeToString(encryptData);
    }

    /**
     * 加密并使用默认向量
     *
     * @param secretKey 密钥
     * @param plainText 原文
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String secretKey, String plainText) throws Exception {
        return encrypt(IV, secretKey, plainText);
    }

    /**
     * 解密
     *
     * @param iv          向量
     * @param secretKey   密钥
     * @param encryptText 密文
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String iv, String secretKey, String encryptText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));
        return new String(decryptData, CHARSET);
    }

    /**
     * 解密并使用默认向量
     *
     * @param secretKey   密钥
     * @param encryptText 密文
     * @return 原文
     * @throws Exception
     */
    public static String decrypt(String secretKey, String encryptText) throws Exception {
        return decrypt(IV, secretKey, encryptText);
    }

}
