package com.qkl.util.help.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 读取JSON文件工具类
 * <p>Description：  </p>
 * 
 * @project_Name yc_util
 * @class_Name JsonFileUtil.java
 * @author weigangpeng
 * @date 2015年4月21日
 * @version v1.0
 */
public class JsonFileUtil {

	/**
	 * 读取JSON文件
	 * <p>
	 * (读取JSON文件)
	 * </p>
	 * 
	 * @Title: readFile
	 * @param path
	 *            文件路径
	 * @return json字符串
	 * @throws
	 * @create author weigangpeng
	 * @create date 2015年4月21日
	 */
	public static String readFile(String path) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
