package com.qkl.util.help;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 用户对象
 * <p>Description：用户对象  </p>
 * @project_Name yc_udrs_api
 * @class_Name UserBean.java
 * @author kezhiyi
 * @date 2015年6月12日
 * @version v1.0
 */

public class UserBean  implements Serializable{

	/**
	* 用户iD
	*/
	private long userid;
	/**
	* 用户名
	*/
	private String username;
	/**
	* 密码
	*/
	private String password;
	/**
	* 分公司编码
	*/
	private String deptcode;
	/**
	* 区域编码
	*/
	private String sectioncode;
	
	/** 状态 */
	private Integer status;

	/** 角色id */
	private Integer roleId;
	
	/** 角色名称 */
	private String roleName;
	
	
		
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserBean(){
		
	}
		
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getSectioncode() {
		return sectioncode;
	}
	public void setSectioncode(String sectioncode) {
		this.sectioncode = sectioncode;
	}
			
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("userid",getUserid())
			.append("username",getUsername())
			.append("password",getPassword())
			.append("deptcode",getDeptcode())
			.append("sectioncode",getSectioncode())
			.append("status",getStatus())
			.append("roleId",getRoleId())
			.append("roleName",getRoleName())			
			.toString();
	}
	
}
