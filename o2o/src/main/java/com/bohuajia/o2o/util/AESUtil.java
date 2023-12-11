package com.bohuajia.o2o.util;

import java.security.Key;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

/**
 * AES symmetric encryption with PBKDF2 key generation and CBC mode
 */
public class AESUtil {

	private static final String PASSPHRASE = "JustAPassphrase";
	private static final String CHARSETNAME = "UTF-8";
	private static final String ALGORITHM = "AES";
	private static final String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final int KEY_SIZE = 256; // Use 256-bit keys
	private static final byte[] salt = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
	private static final byte[] iv = new byte[16]; // Fixed IV (16 bytes for AES)
	private static final Key key;

	static {
		try {
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
			IvParameterSpec ivParams = new IvParameterSpec(iv);

			cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
			byte[] encrypted = cipher.doFinal(bytes);

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			// debug
			System.out.println("Exception arised on getEncryptString");
			throw new RuntimeException(e);
		}
	}

	public static String getDecryptString(String str) {
		try {
			byte[] encrypted = Base64.getDecoder().decode(str);

			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
			IvParameterSpec ivParams = new IvParameterSpec(iv);

			cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
			byte[] decryptedBytes = cipher.doFinal(encrypted);

			return new String(decryptedBytes, CHARSETNAME);
		} catch (Exception e) {
			// debug
			System.out.println("Exception arised on getDecryptString");
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String root = getEncryptString("root");
		System.out.println("Encrypted: " + root);
		System.out.println("Decrypted: " + getDecryptString(root));
		String encrypted = getEncryptString("jbh920207");
		System.out.println("Encrypted: " + encrypted);
		System.out.println("Decrypted: " + getDecryptString(encrypted));
	}
}
