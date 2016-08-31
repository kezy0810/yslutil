/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author 作者 wanghaolong
 *<br> @data 2015年6月16日 上午9:16:28
 *<br> @version v1.0
 */
package com.qkl.util.help.excel;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * <p>
 * Description：导入xls
 * </p>
 * 
 * @project_Name yc-uics-web
 * @class_Name Xls2ListUtil.java
 * @author wanghaolong
 * @date 2015年6月16日 上午9:16:28
 * @version v1.0
 */

public class Xls2ListUtil {

	/**
	 * 
	 * <p>
	 * (描述)
	 * </p>
	 * 
	 * @Title: xls2ListByXml
	 * @param @param path excel路径
	 * @param @param classname 类名
	 * @param @param isMap 是否map
	 * @param @return
	 * @param @throws Exception
	 * @throws
	 * @author wanghaolong
	 * @date 2015年6月16日 上午9:45:08
	 */
	public static List<?> xls2List(String path, String classname, boolean isMap)
			throws Exception {

		InputStream is = Xls2ListUtil.class.getResourceAsStream(path);
		List<?> list = EasyXls.xls2List(
				Xls2ListUtil.class.getResource(
						"/xls/" + classname
								+ (isMap == true ? "Map.xml" : ".xml"))
						.getPath(), is);
		return list;
	}

	/**
	 * 
	 * <p>
	 * (描述)
	 * </p>
	 * 
	 * @Title: xls2ListByXml
	 * @param @param request 请求
	 * @param @param classname 类名
	 * @param @param isMap 是否map
	 * @param @return
	 * @param @throws Exception
	 * @throws
	 * @author wanghaolong
	 * @date 2015年6月16日 上午9:45:08
	 */
	public static List<?> xls2List(HttpServletRequest request,
			String classname, boolean isMap) throws Exception {

		InputStream is = request.getInputStream();
		List<?> list = EasyXls.xls2List(
				Xls2ListUtil.class.getResource(
						"/xls/" + classname
								+ (isMap == true ? "Map.xml" : ".xml"))
						.getPath(), is);
		return list;
	}

	/**
	 * 
	 * <p> (描述)  </p>
	 * @Title: xlsx2List 
	 * @param @param request
	 * @param @param xmlName
	 * @param @return
	 * @param @throws Exception
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年7月7日 上午10:46:05
	 */
	public static List<?> xlsx2List(HttpServletRequest request, String xmlName) throws Exception {

		InputStream is = request.getInputStream();
		List<?> list = EasyXls.xlsx2List(is, xmlName);
		return list;
	}
	
	/**
	 * 
	 * <p> (描述)  </p>
	 * @Title: xlsx2List 
	 * @param @param inputStream
	 * @param @param xmlName
	 * @param @return
	 * @param @throws Exception
	 * @throws 
	 * @author wanghaolong
	 * @date 2015年7月8日 下午4:51:22
	 */
	public static List<?> xlsx2List(InputStream inputStream, String xmlName) throws Exception {
		
		List<?> list = EasyXls.xlsx2List(inputStream, xmlName);
		return list;
	}
	
	public static boolean checkXlsFileCols(InputStream inputStream, String xmlName) throws Exception {
		
		boolean ckRs = EasyXls.checkXlsFileCols(inputStream, xmlName);
		return ckRs;
	}
	
}
