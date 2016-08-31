package com.qkl.util.help;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * E租宝客户明细数据对象
 * <p>
 * Description：E租宝客户明细数据对象
 * </p>
 * 
 * @project_Name yc_udrs_provider
 * @class_Name CustomerInfoBean.java
 * @author weigangpeng
 * @date 2015年4月20日
 * @version v1.0
 */
public class CustomerInfoBean implements java.io.Serializable {

	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * 客户id
	 */
	private java.lang.Long cust_id;
	/**
	 * 客户名称
	 */
	private java.lang.String cust_name;
	/**
	 * 所在城市
	 */
	private java.lang.String city;
	/**
	 * 手机号码
	 */
	private java.lang.String call_phone;
	/**
	 * e租宝账号
	 */
	private java.lang.String e_username;
	/**
	 * 理财师e租宝账号
	 */
	private java.lang.String channel_username;
	/**
	 * 理财师姓名
	 */
	private java.lang.String channel_realname;
	/**
	 * 所属分公司
	 */
	private java.lang.String customer_flrm;
	/**
	 * 所属区域管理部
	 */
	private java.lang.String customer_section;
	/**
	 * 注册时间
	 */
	private java.util.Date reg_time;

	public CustomerInfoBean() {
	}

	/**
	 * 获取 id
	 * 
	 * @return java.lang.Integer id
	 */
	public java.lang.Integer getId() {
		return this.id;
	}

	/**
	 * 设置 id
	 * 
	 * @param java
	 *            .lang.Integer id
	 */
	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	/**
	 * 获取 客户id
	 * 
	 * @return java.lang.Long 客户id
	 */
	public java.lang.Long getCust_id() {
		return this.cust_id;
	}

	/**
	 * 设置 客户id
	 * 
	 * @param java
	 *            .lang.Long 客户id
	 */
	public void setCust_id(java.lang.Long value) {
		this.cust_id = value;
	}

	/**
	 * 获取 客户名称
	 * 
	 * @return java.lang.String 客户名称
	 */
	public java.lang.String getCust_name() {
		return this.cust_name;
	}

	/**
	 * 设置 客户名称
	 * 
	 * @param java
	 *            .lang.String 客户名称
	 */
	public void setCust_name(java.lang.String value) {
		this.cust_name = value;
	}

	/**
	 * 获取 所在城市
	 * 
	 * @return java.lang.String 所在城市
	 */
	public java.lang.String getCity() {
		return this.city;
	}

	/**
	 * 设置 所在城市
	 * 
	 * @param java
	 *            .lang.String 所在城市
	 */
	public void setCity(java.lang.String value) {
		this.city = value;
	}

	/**
	 * 获取 手机号码
	 * 
	 * @return java.lang.String 手机号码
	 */
	public java.lang.String getCall_phone() {
		return this.call_phone;
	}

	/**
	 * 设置 手机号码
	 * 
	 * @param java
	 *            .lang.String 手机号码
	 */
	public void setCall_phone(java.lang.String value) {
		this.call_phone = value;
	}

	/**
	 * 获取 e租宝账号
	 * 
	 * @return java.lang.String e租宝账号
	 */
	public java.lang.String getE_username() {
		return this.e_username;
	}

	/**
	 * 设置 e租宝账号
	 * 
	 * @param java
	 *            .lang.String e租宝账号
	 */
	public void setE_username(java.lang.String value) {
		this.e_username = value;
	}

	/**
	 * 获取 理财师e租宝账号
	 * 
	 * @return java.lang.String 理财师e租宝账号
	 */
	public java.lang.String getChannel_username() {
		return this.channel_username;
	}

	/**
	 * 设置 理财师e租宝账号
	 * 
	 * @param java
	 *            .lang.String 理财师e租宝账号
	 */
	public void setChannel_username(java.lang.String value) {
		this.channel_username = value;
	}

	/**
	 * 获取 理财师姓名
	 * 
	 * @return java.lang.String 理财师姓名
	 */
	public java.lang.String getChannel_realname() {
		return this.channel_realname;
	}

	/**
	 * 设置 理财师姓名
	 * 
	 * @param java
	 *            .lang.String 理财师姓名
	 */
	public void setChannel_realname(java.lang.String value) {
		this.channel_realname = value;
	}

	/**
	 * 获取 所属分公司
	 * 
	 * @return java.lang.String 所属分公司
	 */
	public java.lang.String getCustomer_flrm() {
		return this.customer_flrm;
	}

	/**
	 * 设置 所属分公司
	 * 
	 * @param java
	 *            .lang.String 所属分公司
	 */
	public void setCustomer_flrm(java.lang.String value) {
		this.customer_flrm = value;
	}

	/**
	 * 获取 所属区域管理部
	 * 
	 * @return java.lang.String 所属区域管理部
	 */
	public java.lang.String getCustomer_section() {
		return this.customer_section;
	}

	/**
	 * 设置 所属区域管理部
	 * 
	 * @param java
	 *            .lang.String 所属区域管理部
	 */
	public void setCustomer_section(java.lang.String value) {
		this.customer_section = value;
	}

	/**
	 * 获取 注册时间
	 * 
	 * @return java.util.Date 注册时间
	 */
	public java.util.Date getReg_time() {
		return this.reg_time;
	}

	/**
	 * 设置 注册时间
	 * 
	 * @param java
	 *            .util.Date 注册时间
	 */
	public void setReg_time(java.util.Date value) {
		this.reg_time = value;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId()).append("cust_id", getCust_id())
				.append("cust_name", getCust_name()).append("city", getCity())
				.append("call_phone", getCall_phone())
				.append("e_username", getE_username())
				.append("channel_username", getChannel_username())
				.append("channel_realname", getChannel_realname())
				.append("customer_flrm", getCustomer_flrm())
				.append("customer_section", getCustomer_section())
				.append("reg_time", getReg_time()).toString();
	}

}
