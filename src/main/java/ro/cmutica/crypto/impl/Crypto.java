package ro.cmutica.crypto.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author cmutica
 */
public class Crypto {
    
    private static final String ENVAR_NAME = "APPS_KEY";
    private static SecretKeySpec key;
    
    static {
        try {
            var _envVar = System.getenv(ENVAR_NAME).getBytes(StandardCharsets.UTF_8);
            var _hash = Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(_envVar), 16);
            key = new SecretKeySpec(_hash, "AES");
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
    public static String encrypt(String sInput) throws Exception{
        var _cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        _cipher.init(Cipher.ENCRYPT_MODE, key);
        
        var _bEncrypt = _cipher.doFinal(sInput.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(_bEncrypt);
    }
    
    public static String decrypt(String sInput) throws Exception{
        var _cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        _cipher.init(Cipher.DECRYPT_MODE, key);
        
        var _bDecoded = Base64.getDecoder().decode(sInput);
        var _bDecrypt = _cipher.doFinal(_bDecoded);
        return new String(_bDecrypt, StandardCharsets.UTF_8);
    }
}
