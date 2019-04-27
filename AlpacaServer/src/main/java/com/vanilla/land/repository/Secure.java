package com.vanilla.land.repository;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class Secure {

    private final static byte[] salt = "5#aY{E7kK#MaoR8-5&w&|]TeHKpGc#".getBytes();
    private final static char[] password = "8IubjOAnaU".toCharArray();

    public static String getKey(int size, String field) {

        String key = null;

        try{

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);

            byte[] encryptedData = cipher.doFinal(field.getBytes(StandardCharsets.UTF_8));
            key = new String(Base64.encodeBase64(encryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;

    }

}
