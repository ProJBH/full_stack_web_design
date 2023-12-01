package com.bohuajia.o2o.util;

import java.security.Key;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.security.SecureRandom;

/**
 * AES symmetric encryption with PBKDF2 key generation and CBC mode
 */
public class AESUtil {

    private static final String PASSPHRASE = "JustAPassphrase";
    private static final String CHARSETNAME = "UTF-8";
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256; // Use 256-bit keys
    private static final byte[] salt = new byte[16];
    private static final Key key;

    static {
        try {
            // Secure Random for Salt Generation
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            // Key Generation using PBKDF2 with Passphrase
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(PASSPHRASE.toCharArray(), salt, 65536, KEY_SIZE);
            key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEncryptString(String str) {
        try {
            byte[] bytes = str.getBytes(CHARSETNAME);
            
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            // Generating IV
            byte[] iv = new byte[cipher.getBlockSize()];
            new SecureRandom().nextBytes(iv);
            IvParameterSpec ivParams = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
            byte[] encrypted = cipher.doFinal(bytes);

            // Combine IV and encrypted part (IV is needed for decryption)
            byte[] encryptedIVAndText = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedIVAndText, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedIVAndText, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedIVAndText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDecryptString(String str) {
        try {
            byte[] ivAndEncrypted = Base64.getDecoder().decode(str);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            // Extract IV and encrypted part
            byte[] iv = new byte[cipher.getBlockSize()];
            System.arraycopy(ivAndEncrypted, 0, iv, 0, iv.length);
            int encryptedSize = ivAndEncrypted.length - iv.length;
            byte[] encrypted = new byte[encryptedSize];
            System.arraycopy(ivAndEncrypted, iv.length, encrypted, 0, encryptedSize);

            IvParameterSpec ivParams = new IvParameterSpec(iv);

            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
            byte[] decryptedBytes = cipher.doFinal(encrypted);

            return new String(decryptedBytes, CHARSETNAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String encrypted = getEncryptString("root");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + getDecryptString(encrypted));
    }
}
