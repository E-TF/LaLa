package com.project.lala.common.encrytion;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class SHA512EncryptionService implements EncryptionService {
	@Override
	public String encrypt(String raw) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

			byte[] rawBytes = raw.getBytes();

			byte[] digested = messageDigest.digest(rawBytes);
			StringBuilder sb = new StringBuilder();

			for (byte b : digested) {
				sb.append(Integer.toString((b & 0xff + 0x100), 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
