/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author 作者 wanghaolong
 *<br> @data 2015年6月16日 上午9:16:49
 *<br> @version v1.0
 */
package com.qkl.util.help.excel;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * <p>
 * Description：导出xls
 * </p>
 * 
 * @project_Name yc-uics-web
 * @class_Name List2XlsUtil.java
 * @author wanghaolong
 * @date 2015年6月16日 上午9:16:49
 * @version v1.0
 */

public class List2XlsUtil {

	/**
	 * 
	 * <p>
	 * (描述)
	 * </p>
	 * 
	 * @Title: xls2List
	 * @param @param list 导出的list
	 * @param @param xmlPathxmlPath xml完整路径
	 * @param @param path 保存xls路径
	 * @param @param xlsname 保存xls文件名
	 * @param @param isMap
	 * @param @return 处理结果，true成功，false失败
	 * @param @throws Exception
	 * @throws Exception
	 * @author wanghaolong
	 * @date 2015年6月16日 上午10:13:59
	 */
	public static boolean list2Xls(List<?> list, String xmlPath, String path,
			String xlsname) throws Exception {

		return EasyXls.list2Xls(list, xmlPath, path, xlsname);
	}

	/**
	 * 导出list对象到excel2007
	 * <p> (描述)  </p>
	 * @Title: list2Xlsx 
	 * @param @param list
	 * @param @param xmlPath
	 * @param @param response
	 * @param @param filename 
	 * @param @return
	 * @param @throws Exception
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年6月27日 下午4:13:52
	 */
	public static boolean list2Xlsx(List<?> list, String xmlPath,String filename,HttpServletResponse response)
			throws Exception {
		return EasyXls.list2Xlsx(list, xmlPath,filename, response);
	}
}
