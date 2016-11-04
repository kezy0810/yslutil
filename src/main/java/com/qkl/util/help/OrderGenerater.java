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
     * @param usercode
     * @return: String
     */
    public static String generateOrderNo(String usercode){
        if("".equals(usercode)||usercode == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        sb.append(sf.format(new Date())).append(RandomStringUtils.randomNumeric(6)).append(usercode);
        return sb.toString();
    }
    /**
     * @describe:生成流水号
     * @author: zhangchunming
     * @date: 2016年11月4日下午2:12:12
     * @param usercode 邀请码 唯一
     * @param acc_no 交易类型 与表acc_def中acc_no对应
     * @return: String
     */
    public static String generateFlowNo(String usercode,String acc_no){
        if(StringUtil.isEmpty(usercode)||StringUtil.isEmpty(acc_no)){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        sb.append(sf.format(new Date())).append(RandomStringUtils.randomNumeric(6)).append(acc_no).append(usercode);
        return sb.toString();
    }
    /**
     * @describe:消费码规则：日期+邀请码（唯一）+6位随机数
     * @author: lishuo
     * @date: 2016年11月1日下午5:47:23
     * @param usercode
     * @return: String
     */
    public static String generateOrderCode(String usercode){
        if("".equals(usercode)||usercode == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        sb.append(sf.format(new Date())).append(usercode).append(RandomStringUtils.randomNumeric(6));
        return sb.toString();
    }
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
        String usercode = null;
        //sb.append(sf.format(new Date())).append(RandomStringUtils.randomNumeric(6)).append(usercode);
        sb.append(sf.format(new Date())).append(usercode).append(RandomStringUtils.randomNumeric(6));
        System.out.println(sb.toString());
    }
}
