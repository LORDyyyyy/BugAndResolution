package com.BugAndResolution.BugAndResolution.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
    private static final String ALGORITHM = "AES";
    private static final String KEY = "12345678901234567890123456789012";

    public static String encrypt(String value)
    {
        try {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e)
        {
            throw new RuntimeException("Error while encrypting value", e);
        }
    }
    public static String decrypt(String encryptedValue)
    {
        try
        {
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decoded = Base64.getDecoder().decode(encryptedValue);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while decrypting value", e);
        }
    }
}