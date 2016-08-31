package com.qkl.util.help;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询条件对象
 * <p>Description：查询条件对象  </p>
 * @project_Name yc_udrs_api
 * @class_Name SearchCondition.java
 * @author weigangpeng
 * @date 2015年4月18日
 * @version v1.0
 */
public class SearchCondition implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 开始时间
	 */
	private Date beginDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 显示行数
	 */
	private int pageSize;
	
	

	private UserBean userBean;

	
	public SearchCondition(String tableName, Date startDate, Date endDate,
			int currentPage, int pageSize,UserBean userBean) {
		super();
		this.tableName = tableName;
		this.beginDate = startDate;
		this.endDate = endDate;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.userBean =userBean;
	}

	
	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	
	public String getBeginDateDayStr() {
		if(beginDate!=null){
			return DateUtil.getDateLong(beginDate);
		}
		return null;
	}

	public void setBeginDate(Date startDate) {
		this.beginDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public String getEndDateDayStr() {
		if(endDate!=null){
			return DateUtil.getDateLong(endDate);
		}
		return null;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
