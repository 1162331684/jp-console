package com.jeeplus.modules.app.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;


/**
 * 密码工具类
 * 
 * @author 永旺
 *
 */
public abstract class Encryption {
	private static final String SHA256 = "SHA-256";

	private static byte[] digest(byte[] input, String algorithm, byte[] salt,
			int iterations) throws NoSuchAlgorithmException {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
	}


	public static String sha256Hex(String source) throws NoSuchAlgorithmException {
		return Hex.encodeHexString(digest(
				source.getBytes(StandardCharsets.UTF_8), SHA256, null, 1));
	}


}
