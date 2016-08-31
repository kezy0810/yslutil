package com.qkl.util.help;


import java.util.UUID;

/**
 * 生成唯一串
 * @Description 生成唯一串
 * @project_Name yc_util
 * @class_Name UUId.java
 * @author liuyang
 * @date 2015年4月13日
 * @version v1.0
 */
public class UUId {

  private UUId(){}
  /**
   * 去掉“-”的uuid
   * @return
   */
  public static String getUUId(){
    UUID uuid = UUID.randomUUID();
    return uuid.toString().replace("-", "");
  }
  /**
   * 原始uuid
   * @return
   */
  public static String getUUID(){
		UUID uuid  =  UUID.randomUUID(); 
		return UUID.randomUUID().toString();
	}
  
  public static void main(String[] args) {
    String uuid = getUUId();
    System.out.println(uuid);
  }
}