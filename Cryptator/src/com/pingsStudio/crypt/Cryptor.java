package com.pingsStudio.crypt;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Cryptor {

	public static void main(String[] args) {
		String rawPass="Password123";
		
		String salt="saltyKing88";
		System.out.println("SHA1 Password ["+encodeToSHA(rawPass,salt)+"]");

		System.out.println("BCrypt Password ["+encodeToBCrypt(rawPass)+"]");
		
		
		//=-=-=-=-=-=-=-=-=--=
		String checkRawPass="Password123";
		
		String checkEncSHA="572e9a903cdc6b05db4b7beda6a912c8fbad5493";
		String checkSHASalt="saltyKing88";
		System.out.println("SHA1 Password Valid ["+checkSHAPass(checkRawPass,checkEncSHA,checkSHASalt)+"]");
		
		String checkEncBCRYPT="$2a$10$B6aHm.24pcJW90Y6jrZj..l4GWfOznKC/GzVp4ZlIHMyc6QVg/9jC";
		System.out.println("BCrypt Password Valid ["+checkBCryptPass(checkRawPass,checkEncBCRYPT)+"]");
		
	}
	
	
	public static String encodeToSHA(String rawPass,String salt) {
		if(rawPass==null) {
			System.out.println("Dude, you must give the rawPassword to be encode!!!");
			return null;
		}
		if(salt==null || salt.trim().length()<1) {
			salt= "{}";
		}
		ShaPasswordEncoder s = new ShaPasswordEncoder();
		String encPass=s.encodePassword(rawPass, salt);
		return encPass;
	}
	public static boolean checkSHAPass(String rawPass,String encPass,String salt) {
		if(rawPass==null || encPass==null) {
			System.out.println("Dude, you must give the raw and enc Password to be verify!!!");
			return false;
		}
		if(salt==null || salt.trim().length()<1) {
			salt= "";
		}
		ShaPasswordEncoder s = new ShaPasswordEncoder();
		return s.isPasswordValid(encPass, rawPass, salt);
	}
	
	public static String encodeToBCrypt(String rawPass) {
		if(rawPass==null) {
			System.out.println("Dude, you must give the rawPassword to be encode!!!");
			return null;
		}
		
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		String encPass=b.encode(rawPass);
		return encPass;
	}
	
	public static boolean checkBCryptPass(String rawPass,String encPass) {
		if(rawPass==null || encPass==null) {
			System.out.println("Dude, you must give the raw and enc Password to be verify!!!");
			return false;
		}
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		return b.matches(rawPass,encPass);
	}

}
