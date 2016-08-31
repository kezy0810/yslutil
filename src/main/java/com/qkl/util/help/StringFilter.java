package com.qkl.util.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**特殊符号过滤
 * @Description 特殊符号过滤
 * @project_Name yc_util
 * @class_Name StringFilter.java
 * @author liuyang
 * @date 2015年4月13日
 * @version v1.0
 */
public class StringFilter {

	// 过滤特殊字符
	public static String stringFilter(String str){
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s*|\t|\r|\n]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	//是否有特殊符号
	public static boolean hasSpecialSymbol(String str){
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~#$%^&*|''\\/~#￥……&||*——|【】‘”“’\\s*|\t|\r|\n]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	public static void main(String[] args) {
//		String str = "*\t\r\nadCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		String str = "亿翁%";
		System.out.println(str);
		System.out.println("***********华丽的分割线***********");
		System.out.println(hasSpecialSymbol(str));
	}
	
	/**
	 * 过滤需要带特殊符号的一些参数
	 * @Description: 过滤需要带特殊符号的一些参数
	 * @Title: someProperties 
	 * @param key  Map中的key值
	 * @param value Map中的value值
	 * @return boolean (true=含有特殊符合,不通过 or false=可以通过)
	 * @create author lian
	 * @create date 2015年4月14日
	 */
	public static boolean someProperties(String key ,String value){
		if(value.replace("|","").length()!=value.length()){
			return true;
		}	
		
		return false;
	}
}
