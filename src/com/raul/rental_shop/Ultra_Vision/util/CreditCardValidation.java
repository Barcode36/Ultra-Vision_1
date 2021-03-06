package com.raul.rental_shop.Ultra_Vision.util;

public class CreditCardValidation {

	public static boolean isEircode(String input) {

		Boolean isEircode = input.matches("^[A,C,D,E,F,H,K,N,P,R,T,V,W,X,Y]{1}"
				+ "[0-9]{1}[0-9,W]{1}[\\ \\-]?[0-9,A,C,D,E,F,H,K,N,P,R,T,V,W,X,"
				+ "Y]{4}$");
		
		return isEircode;
	}

	public static boolean isSizeThree(String input) {
		return input.length() <= 3;
	}

	public static boolean isNumber(String input) {
		return input.matches("[0-9]+");
	}

	public static boolean isCreditCard(String input) {
		return input.matches("(\\d{4}[-. ]?){4}|\\d{4}[-. ]?\\d{6}[-. ]?\\d{5}");
	}
}
