package kr.co.momdeal.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class SHAE {
	private static final String ENCODE_TYPE = "SHA-256";
	private static final String CHAR_SET = "UTF-8";
	private static final String SALT = "ggi600!@";
	private static byte[] INPUT;
	private static MessageDigest DIGEST;
	static {
		try {
			INPUT = SALT.getBytes(CHAR_SET);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			DIGEST = MessageDigest.getInstance(ENCODE_TYPE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String encodeSHA(String str){
		byte[] source = str.getBytes();
		byte[] bytes = new byte[source.length + INPUT.length];
		System.arraycopy(source, 0, bytes, 0, source.length);
		System.arraycopy(INPUT, 0, bytes, source.length, INPUT.length);
		byte[] byteData = DIGEST.digest(bytes);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
		}
		return sb.toString();
	}

	public static boolean match(String orgMsg, String encodeMsg) {
		return encodeMsg.equals(encodeSHA(orgMsg));
	}

	public static boolean match(Map<String,Object> orgCui, Map<String,Object> targetCui) {
		return match(orgCui.get("CUI_PWD").toString(), targetCui.get("CUI_PWD").toString());
	}
	
	public static void main (String a[]) {
		System.out.println(SHAE.encodeSHA("tivld82"));
	}
}
