package com.qkl.util.help;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
/**
 * @author zhangchunming
 * @describe 订单生成器
 */
public class OrderGenerater {
  
    public static String generateOrderNo(){
    	StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        sb.append(sf.format(new Date())).append(RandomStringUtils.randomNumeric(6));
        return sb.toString();
    }
    /**
     * @describe:订单号规则：日期+6位随机数+邀请码（唯一）
     * @author: zhangchunming
     * @date: 2016年11月1日下午5:47:23
     * @param user_code
     * @return: String
     */
    public static String generateOrderNo(String user_code){
        if("".equals(user_code)||user_code == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        sb.append(sf.format(new Date())).append(RandomStringUtils.randomNumeric(6)).append(user_code);
        return sb.toString();
    }
}
