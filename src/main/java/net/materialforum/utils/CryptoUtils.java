package net.materialforum.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class CryptoUtils {
    
    public static String randomSalt() {
        byte[] saltBytes = new byte[32];
        new SecureRandom().nextBytes(saltBytes);
        return DatatypeConverter.printBase64Binary(saltBytes);
    }
    
    public static String hashPassword(String password, String salt) {
        try {
            String codeSalt = "Cwn9x0JA7X3kkQL2uLVO";
            String saltedPassword = password + salt + codeSalt;
            byte[] hash = MessageDigest.getInstance("SHA-512").digest(saltedPassword.getBytes("utf-8"));
            return DatatypeConverter.printBase64Binary(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return null;
        }
    }
    
    public static String md5(String input) {
        try {
            byte[] byteHash = MessageDigest.getInstance("MD5").digest(input.getBytes("utf-8"));
            String hash = new BigInteger(1, byteHash).toString(16);
            while(hash.length() < 32)
                hash = "0" + hash;
            return hash;
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return null;
        }
    }
    
}
