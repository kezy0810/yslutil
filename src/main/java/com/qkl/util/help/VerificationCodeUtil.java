package com.qkl.util.help;

import java.util.Random;

public class VerificationCodeUtil {
	 public static  int randomInt(int from, int to){
		  Random r = new Random();
		  return from + r.nextInt(to - from);
	}
	
	public static  String getRandNum(int charCount) {
		String charValue = "";
	    for (int i = 0; i < charCount; i++){
		    char c = (char) (randomInt(0,10)+'0');
		    charValue += String.valueOf(c);
	    }
		return charValue;
	}
}
