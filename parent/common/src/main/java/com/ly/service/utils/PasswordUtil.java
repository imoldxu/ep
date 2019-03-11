package com.ly.service.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PasswordUtil {

	public static String generatePwd(String oldPwd,String nonce){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String newPwdStr = oldPwd+nonce;
		byte[] newPwdArray = md5.digest(newPwdStr.getBytes());
		String newPwd = bytesToHex(newPwdArray);
		return newPwd;
	}
	
	public static String generateClientPwd(String oldPwd){
		String nonce = "hk";
		return generatePwd(oldPwd, nonce);
	}
	
	public static boolean isEqual(String serverPwd, String clientPwd, String nonce){
		String newPwd = generatePwd(clientPwd, nonce);
		if(newPwd.equals(serverPwd)){
			return true;
		}else{
			return false;
		}
	}
	
	public static String generateNonce(){
		return UUID.randomUUID().toString();
	}
	

	private static String bytesToHex(byte[] bytes) {  
	    StringBuffer sb = new StringBuffer();  
	    for(int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(bytes[i] & 0xFF);  
	        if(hex.length() < 2){  
	            sb.append(0);  
	        }  
	        sb.append(hex);  
	    }  
	    return sb.toString();  
	}
}
