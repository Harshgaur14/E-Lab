package com.cybergyan.cybergyanelab.dto;

public class MyUser{
	private static String username;
	private static String myPassword;

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		MyUser.username = username;
	}

	public static String getMyPassword() {
		return myPassword;
	}

	public static void setMyPassword(String myPassword) {
		MyUser.myPassword = myPassword;
	}
}
