package com.qkl.util.help;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel导出工具类
 * <p>Description： Excel导出工具类 </p>
 * @project_Name yc_util
 * @class_Name ExcelUtil.java
 * @author kezhiyi,weigangpeng
 * @date 2015年7月21日
 * @version v1.0
 */
public class ExcelUtil {

	/**
	 * 一个sheet中最多行数
	 */
	private final static int MAX_ROW_COUNT_IN_ONE_SHEET = 65536;
	/**
	 * 分多个excel文件导出，每个导出excel文件的记录数
	 */
	public final static int ROW_COUNT_IN_ONE_EXCEL=10000;
	
	/** 
	 * 导出Excel
	 * <p> (导出Excel)  </p>
	 * @Title: exportExcel 
	 * @param out 输出流
	 * @param title 标题
	 * @param list 数据集合
	 * @param fields 字段数组
	 * @param fieldNames 字段名称数据
	 * @return 
	 * @throws 
	 * @create author weigangpeng
	 * @create date 2015年4月23日
	 */ 
	public static HSSFWorkbook exportExcel(OutputStream out,String title, List list,
			String[] fields, String[] fieldNames) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = initCellStyle(workbook); 
		String sheetName = "Sheet1";
		if(list.size()<=MAX_ROW_COUNT_IN_ONE_SHEET){
			writeSheet(title, list, fields, fieldNames, workbook, style, sheetName);
		}else{
			int pageSize = MAX_ROW_COUNT_IN_ONE_SHEET - 2;
			int count = list.size();
			int sheetCount = count / pageSize + (count % pageSize != 0 ? 1 : 0);
			for (int i = 0; i < sheetCount; i++) {
				int fromIndex = i * pageSize ;
				int toIndex = (i+1) * pageSize ;
				if(toIndex > count ){
					toIndex = count ;
				}
				List newList = list.subList(fromIndex, toIndex);
				sheetName = "第"+(i+1)+"部分";
				writeSheet(title, newList, fields, fieldNames, workbook, style, sheetName);
			}
		}
		try {
			workbook.write(out);
//			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private static void writeSheet(String title, List list, String[] fields,
			String[] fieldNames, HSSFWorkbook workbook, HSSFCellStyle style,
			String sheetName) {
		HSSFSheet sheet = workbook.createSheet(sheetName);
		int rowIndex = 0, cellIndex = 0;
		
		ExcelUtil.insertMainTitle(rowIndex, cellIndex, fields.length - 1, title,
				sheet, workbook);

		rowIndex++;

		for (int i = 0; i < fieldNames.length; i++) {
			insertTitle(rowIndex, rowIndex, cellIndex, cellIndex,
					fieldNames[i], 12, sheet, workbook);
			cellIndex++;
		}

		rowIndex++;
		cellIndex = 0;
		for (Object obj : list) {
			for (int i = 0, len = fields.length; i < len; i++) {
				String value = "";
				if(obj instanceof Map){
					Object objValue = ((HashMap)obj).get(fields[i]);
					if(objValue != null){
						value = objValue.toString();
					}
				}else{
					if ("index".equals(fields[i])) {
						value =String.valueOf(rowIndex-1);
					}else{
						value = getFieldValue(obj, fields[i]);
						if(StringUtils.isEmpty(value)){
							value = "";
						}
					}
				}
				ExcelUtil.insertValue(rowIndex, cellIndex, value, sheet,
						workbook, style);
				cellIndex++;
			}
			rowIndex++;
			cellIndex = 0;
		}
	}

	/** 
	 * 导出Excel，数据源为list，子项为map对象
	 * <p> (导出Excel)  </p>
	 * @Title: exportExcel 
	 * @param out 输出流
	 * @param title 标题
	 * @param list 数据集合(子项为map对象)
	 * @param fields 字段数组
	 * @param fieldNames 字段名称数据
	 * @return 
	 * @throws 
	 * @create author weigangpeng
	 * @create date 2015年4月23日
	 */ 
	public static HSSFWorkbook exportExcelByMapData(OutputStream out,String title, List<HashMap<String, Object>> list,
			String[] fields, String[] fieldNames) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle style = initCellStyle(workbook); 
		String sheetName = "Sheet1";
		if(list.size()<=MAX_ROW_COUNT_IN_ONE_SHEET){
			writeSheet(title, list, fields, fieldNames, workbook, style, sheetName);
		}else{
			int pageSize = MAX_ROW_COUNT_IN_ONE_SHEET - 2;
			int count = list.size();
			int sheetCount = count / pageSize + (count % pageSize != 0 ? 1 : 0);
			for (int i = 0; i < sheetCount; i++) {
				int fromIndex = i * pageSize ;
				int toIndex = (i+1) * pageSize ;
				if(toIndex > count ){
					toIndex = count ;
				}
				List newList = list.subList(fromIndex, toIndex);
				sheetName = "第"+(i+1)+"部分";
				writeSheet(title, newList, fields, fieldNames, workbook, style, sheetName);
			}
		}
		try {
			workbook.write(out);
//			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	
	/**
	 * 在指定的行号、列号插入数据
	 */
	public static void insertValue(int rowIndex, int cellIndex, String value,
			HSSFSheet sheet, HSSFWorkbook workbook, HSSFCellStyle style ) {
		if (sheet == null)
			return;
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(cellIndex);
		if (cell == null) {
			cell = row.createCell(cellIndex);
		}

		 row.setRowStyle(style);
		 row.setHeightInPoints(20);
		 cell.setCellStyle(style);
		cell.setCellValue(value);
//		 sheet.autoSizeColumn(( short ) cellIndex ); // 调整列宽度
		row = null;
		cell = null;

	}

	private static HSSFCellStyle initCellStyle(HSSFWorkbook workbook) {
		HSSFFont f = workbook.createFont();
		f.setFontHeightInPoints((short) 12); //字体大小
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		return style;
	}

	/**
	 * 在指定的行号、列号插入数据
	 */
	public static void insertTitle(int rowIndex, int rowto, int cellIndex,
			int colto, String value, int fontsize, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		if (sheet == null)
			return;
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(cellIndex);
		if (cell == null) {
			cell = row.createCell(cellIndex);
		}

		HSSFFont f = workbook.createFont();

		f.setFontName("黑体");
		f.setFontHeightInPoints((short) fontsize); // 字体大小
//		f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
		// 定义表头单元格格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(f); // 单元格字体
		style.setBorderBottom(style.BORDER_THIN); // 单元格边框
		style.setBorderTop(style.BORDER_THIN);
		style.setBorderRight(style.BORDER_THIN);
		style.setBorderLeft(style.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平对齐方式
		cell.setCellStyle(style);

		// 3是列号，4是列宽值
		sheet.setColumnWidth(cellIndex, 5000);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowto,cellIndex,colto));
//		 sheet.addMergedRegion(new Region(rowIndex, (short) cellIndex,rowto,
//		 (short) colto));

		cell.setCellValue(value);
	}

	/**
	 * 插入主标题
	 */
	public static void insertMainTitle(int rowIndex, int cellIndex, int colto,
			String value, HSSFSheet sheet, HSSFWorkbook workbook) {
		insertTitle(rowIndex, rowIndex, cellIndex, colto, value, 16, sheet,
				workbook);
	}

	/**
	 * 插入小标题
	 */
	public static void insertSmallTitle(int rowIndex, int cellIndex,
			String value, HSSFSheet sheet, HSSFWorkbook workbook) {
		String[] values = value.split(";");
		for (int i = 0; i < values.length; i++) {
			insertTitle(rowIndex, rowIndex, cellIndex, cellIndex, values[i],
					12, sheet, workbook);
			cellIndex++;
		}
	}

	public static String getFieldValue(Object obj, String field) {
		String value = "";
		try {
			Object result = BeanUtil.forceGetProperty(obj, field);
			if(result instanceof Date){
				return DateUtil.getDateLong((Date)result);
			}
			value = BeanUtil.forceGetProperty(obj, field).toString();
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return value;
	}
	
	
	 /**
		 * 将数据导出到指定路径下
		 * <p>
		 * (导出数据)
		 * </p>
		 * @create author kezhiyi
		 * @param title 导出文件的表头
		 * @param List  导出数据
		 * @param fileds 字段
		 * @param fieldNames 显示字段列表
		 * @Title: list2Xlsfile
		 * @throws
		 * @create date 2015年7月17日
		 */
		public static boolean list2Xlsfile(String title, List list, String[] fields, String[] fieldNames,String filePath,String fileName,int tRownCnt)
				throws Exception{		
			File file = new File(filePath);
			if ((!file.exists()) && 
			(!file.mkdirs())) {
				throw new RuntimeException("创建导出目录失败!");
			}
			
			if (!fileName.toLowerCase().endsWith(".xls")) {
				fileName = fileName + ".xls";
		      }			
			OutputStream out = new FileOutputStream(filePath + "/" + fileName);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle style = initCellStyle(workbook);
			String sheetName = "Sheet1";
			int excelRowCnt = tRownCnt<1?ROW_COUNT_IN_ONE_EXCEL:(tRownCnt>60000?ROW_COUNT_IN_ONE_EXCEL:tRownCnt);
			
			if(list!=null&&list.size()>excelRowCnt){		
				out.close();
				return false;
			}		
			 writeSheet(title, list, fields, fieldNames,workbook,style,sheetName);			
			try{
				 workbook.write(out);			
				}catch(Exception e){
				 out.close();
				 e.printStackTrace();
				 return false;
				}
			 out.close();
			 return true;	
		}
	
		
		
		
	

	public static void main(String[] args) {

		try {
			
			List<CustomerInfoBean> list = new ArrayList<CustomerInfoBean>();
			for (int i = 0; i < 75500; i++) {
				CustomerInfoBean bean = new CustomerInfoBean();
				bean.setCall_phone("1870111111"+i);
				bean.setChannel_realname("理财师"+i);
				bean.setChannel_username("lcs"+i);
				bean.setCity("城市"+i);
				bean.setCust_id(Long.parseLong(i+""));
				bean.setCust_name("客户"+(i+1));
				bean.setCustomer_flrm("分公司"+i);
				bean.setCustomer_section("区域管理部"+i);
				bean.setE_username("城市"+i);
				bean.setReg_time(new Date());
				list.add(bean);
			}
			
			String[] fields = {"cust_id","cust_name","city","call_phone","e_username","channel_username","channel_realname","customer_flrm","customer_section","reg_time"};
			String[] fieldNames = {"客户id","客户名称","所在城市","手机号码","e租宝账号","理财师e租宝账号","理财师姓名","所属分公司","所属区域管理部","注册时间"};
			OutputStream out = new FileOutputStream("e://1.xls");
			ExcelUtil.exportExcel(out, "e租宝客户明细数据",list, fields, fieldNames);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
