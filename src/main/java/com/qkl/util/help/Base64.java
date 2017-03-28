package com.qkl.util.help;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64 {
	// 加密  
    public static String getBase64(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  
    // 解密  
    public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    public static void main(String[] args) throws Exception     
    {     
    	/*String jm = Base64.getBase64("http://aub.iteye.com/");
    	System.out.println(jm);*/
    	String mm = Base64.getFromBase64("eyJhY2Nfbm8iOiIwNSIsImNyZWF0ZV90aW1lIjoiMjAxNy0wMy0yNCAxNzo0ODoyMiIsImZ1dHVyZV9jdXJyZW5jeSI6IjM2MC4wMCIsIm9wZXJhdG9yIjoiMTMwMDAwMDAwMDAiLCJvdGhlcl9hbW50IjoiMzYwLjAwIiwib3RoZXJfc291cmNlIjoi5ZWG5Z+O5YWR5o2iIiwib3RoZXJubyI6IjE3MDMyNDE3NDgyMDczNTkzMDAiLCJyZW1hcmsxIjoi5paw5qy+6JOd54mZ6ICz5py6IEpLUi0yMDVC5aS05oi05byP5peg57q/6JOd54mZ6ICz5py6IOaUr+aMgVRG5Y2hIiwic3RhdHVzIjoiNSIsInVzZXJfaWQiOiIyNTc3OSIsInVzZXJfdHlwZSI6IjEiLCJ3bGJpX2FtbnQiOiIzNjAuMDAifQ==");
    	System.out.println(mm);
    }  
}
