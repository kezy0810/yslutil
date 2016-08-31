package com.qkl.util.help.solr;

/**solr中区别类型的变量
 * <p>Description：  </p>
 * @project_Name yc_util
 * @class_Name SolrUtil.java
 * @author liuyang
 * @date 2015年6月2日
 * @version v1.0
 */
public class SolrUtil {

	public final static String USER_TYPE = "user";// 理财师
	
	public final static String CUST_TYPE = "cust";// 客户
	
	public final static String CLUE_TYPE = "clue";// 线索
	
	
	public final static String ADD = "add"; //删除操作
	
	public final static String UPDATE = "update"; //更新操作
	
	public final static String DELETE = "delete" ; //删除操作
	
	public final static String DELETE_ALL = "deleteAll" ; //删除所有索引操作
	
	/**
	 * http调用solr的状态返回值 0为成功
	 */
	public final static int STATUS_OK = 0;
}
