package com.qkl.util.help;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;


public class SHA256Util {
    
    public static String sign(String pri,String data){
        try {
            Mac mac = Mac.getInstance("HMACSHA256");
            byte[] secrretByte = pri.getBytes("utf-8");
            byte[] dataByte = data.getBytes("utf-8");
            SecretKey secret = new SecretKeySpec(secrretByte,"SHA256");
            mac.init(secret);
            mac.update(dataByte);
            byte[] doFinal = mac.doFinal();
            byte[] hexB = new Hex().encode(doFinal);
            return new String(hexB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

