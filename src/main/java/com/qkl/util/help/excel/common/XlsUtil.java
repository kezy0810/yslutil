package com.qkl.util.help.excel.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.DateCell;
import jxl.ErrorCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qkl.util.help.excel.bean.ExcelConfig;
import com.qkl.util.help.excel.bean.Field;
import com.qkl.util.help.excel.generater.GenXml;

/**
 * excel，对导入导出进行封装
 *
 * @author liuzh
 */
public class XlsUtil {

    public static final String EXCEL = ".xls";
    private static Map<String, ExcelConfig> cache = new HashMap<String, ExcelConfig>();

    /**
     * 获取xml配置对象
     *
     * @param xmlPath xml完整路径
     * @return xml配置对象
     */
    private static ExcelConfig getEasyExcel(String xmlPath) {
        ExcelConfig easyExcel = cache.get(xmlPath);
        if (easyExcel == null) {
            easyExcel = XmlConfig.getXmlConfig(xmlPath);
        }
        if (easyExcel == null) {
            throw new RuntimeException("无法获取xml配置文件!");
        }
        if (easyExcel.getCache() == null || easyExcel.getCache()) {
            cache.put(xmlPath, easyExcel);
        }
        return easyExcel;
    }

    /**
     * 打开代码生成器，请在项目中执行，只有这样才能加载相应的类
     */
    public static void openGenerater() {
        GenXml.run();
    }

    /**
     * 导入xml到List
     *
     * @param xmlPath xml完整路径
     * @param xlsFile xls文件路径
     * @return List对象
     * @throws Exception
     */
    public static List<?> xls2List(String xmlPath, File xlsFile) throws Exception {
        Workbook wb = null;
        List<?> list = null;
        try {
            wb = Workbook.getWorkbook(xlsFile);
            list = workbook2List(xmlPath, wb);
        } catch (Exception e) {
            throw new Exception("转换xls出错:" + e.getMessage());
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
        return list;
    }

    /**
     * 导入xml到List
     *
     * @param config  配置
     * @param xlsFile xls文件路径
     * @return List对象
     * @throws Exception
     */
    public static List<?> xls2List(ExcelConfig config, File xlsFile) throws Exception {
        Workbook wb = null;
        List<?> list = null;
        try {
            wb = Workbook.getWorkbook(xlsFile);
            list = workbook2List(config, wb);
        } catch (Exception e) {
            throw new Exception("转换xls出错:" + e.getMessage());
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
        return list;
    }

    /**
     * 导入xml到List
     *
     * @param xmlPath     xml完整路径
     * @param inputStream xls文件流
     * @return List对象
     * @throws Exception
     */
    public static List<?> xls2List(String xmlPath, InputStream inputStream) throws Exception {
        Workbook wb = null;
        List<?> list = null;
        try {
            wb = Workbook.getWorkbook(inputStream);
            list = workbook2List(xmlPath, wb);
        } catch (Exception e) {
            throw new Exception("转换xls出错:" + e.getMessage());
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
        return list;
    }

    /**
     * 导入xml到List
     *
     * @param config      配置
     * @param inputStream xls文件流
     * @return List对象
     * @throws Exception
     */
    public static List<?> xls2List(ExcelConfig config, InputStream inputStream) throws Exception {
        Workbook wb = null;
        List<?> list = null;
        try {
            wb = Workbook.getWorkbook(inputStream);
            list = workbook2List(config, wb);
        } catch (Exception e) {
            throw new Exception("转换xls出错:" + e.getMessage());
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
        return list;
    }

    /**
     * workbook转换为list
     *
     * @param xmlPath
     * @param wb
     * @return
     * @throws Exception
     */
    public static List<?> workbook2List(String xmlPath, Workbook wb) throws Exception {
        //获取配置文件
        ExcelConfig config = getEasyExcel(xmlPath);
        return workbook2List(config, wb);
    }

    /**
     * workbook转换为list
     *
     * @param config 配置
     * @param wb     excel
     * @return
     * @throws Exception
     */
    public static List<?> workbook2List(ExcelConfig config, Workbook wb) throws Exception {
        String[] names = config.getNames();
        String[] types = config.getTypes();
        Field key = config.getKey();

        List<Object> list = new ArrayList<Object>();
        Sheet sheet = wb.getSheet(config.getSheetNum());
        int length = sheet.getColumns() < names.length ? sheet.getColumns() : names.length;
        //计算行数
        int rowLength = sheet.getRows() < config.getMaxRow() ?
                sheet.getRows() : (config.getMaxRow() > 0 ? (config.getMaxRow() + config.getStartRow()) : sheet.getRows());

        for (int i = config.getStartRow(); i < rowLength; i++) {
            //Map类型要特殊处理
            Class clazz = Class.forName(config.getClazz());
            Object obj = null;
            if (Map.class.isAssignableFrom(clazz)) {
                obj = new HashMap();
            } else {
                obj = clazz.newInstance();
            }
            for (int j = 0; j < length; j++) {
                setValue(obj, names[j], types[j], sheet.getCell(j, i));
            }
            //checkKey
            if (key != null) {
                //当主键为空时，不在继续读取excel
                if (key.get(obj) == null || "".equals(String.valueOf(key.get(obj)))) {
                    break;
                }
            }
            list.add(obj);
        }
        return list;
    }
    
    

    /**
     * 获取单元格的数据
     *
     * @param cell
     * @return
     * @throws Exception
     */
    private static Object getCellValue(Cell cell) throws Exception {
        Object value = null;
        if (cell instanceof ErrorCell) {
            value = null;
        } else if (cell instanceof LabelCell) {
            value = ((LabelCell) cell).getString();
        } else if (cell instanceof NumberCell) {
            value = ((NumberCell) cell).getValue();
        } else if (cell instanceof DateCell) {
            value = ((DateCell) cell).getDate();
        } else if (cell instanceof BooleanCell) {
            value = ((BooleanCell) cell).getValue();
        } /*else if (cell instanceof FormulaCell) {
            value = ((FormulaCell) cell).getFormula();
        }*/ else {
            value = cell.getContents();
        }
        return value;
    }

    /**
     * 跟对象obj的某个field赋值value
     *
     * @param obj       属性对象
     * @param fieldName 字段名
     * @param cell      单元格
     * @throws Exception
     */
    private static void setValue(Object obj, String fieldName, String type, Cell cell) throws Exception {
        Object val = null;
        Object v = getCellValue(cell);
      
        if (v == null) {
            //不处理
        } else if (Object.class.getCanonicalName().equals(type)) {
            //类型一致的直接使用
            val = v;
        } else if (v.getClass().getName().equals(type)) {
            //类型一致的直接使用
            val = v;
        } else {
            //类型不一致进行转换
            String value = v.toString();
            if (value != null && !value.trim().equals("")) {
                value = value.trim();
                /**
                 * 对类型进行转换，支持int,long,float,double,boolean,Integer,Long,Double,Float,Date,String
                 */
                if (type.equals("int")) {
                    val = new BigDecimal(value).intValue();
                } else if (type.equals("long")) {
                    val = new BigDecimal(value).longValue();
                } else if (type.equals("float")) {
                    val = new BigDecimal(value).floatValue();
                } else if (type.equals("double")) {
                    val = new BigDecimal(value).doubleValue();
                } else if (type.equals("boolean")) {
                    val = Boolean.parseBoolean(value);
                } else {
                    Class clazz = Class.forName(type);
                    if (!clazz.equals(String.class)) {
                        if (clazz.equals(Date.class)) {
                            val = DateUtil.smartFormat(value);
                        } else if (clazz.equals(Integer.class)) {
                            val = new BigDecimal(value).intValue();
                        } else if (clazz.equals(Long.class)) {
                            val = new BigDecimal(value).longValue();
                        } else if (clazz.equals(Float.class)) {
                            val = new BigDecimal(value).floatValue();
                        } else if (clazz.equals(Double.class)) {
                            val = new BigDecimal(value).doubleValue();
                        } else if (clazz.equals(Boolean.class)) {
                            val = Boolean.parseBoolean(value);
                        } else if (clazz.equals(BigDecimal.class)) {
                            val = new BigDecimal(value);
                        }
                    } else {
                        val = value;
                    }
                }
            }
        }
        Field field = FieldUtil.getField(obj, fieldName);
        if (field != null) {
            field.set(obj, val);
        }
    }


    /**
     * 导出list对象到excel
     *
     * @param list     导出的list
     * @param xmlPath  xml完整路径
     * @param filePath 保存xls路径
     * @param fileName 保存xls文件名
     * @return 处理结果，true成功，false失败
     * @throws Exception
     */
    public static boolean list2Xls(List<?> list, String xmlPath, String filePath, String fileName) throws Exception {
        //创建目录
        File file = new File(filePath);
        if (!(file.exists())) {
            if (!file.mkdirs()) {
                throw new RuntimeException("创建导出目录失败!");
            }
        }
        try {
            ExcelConfig config = getEasyExcel(xmlPath);
            list2Xls(config, list, filePath, fileName);
        } catch (Exception e1) {
            return false;
        }
        return true;
    }

    /**
     * 导出list对象到excel
     *
     * @param config   配置
     * @param list     导出的list
     * @param filePath 保存xls路径
     * @param fileName 保存xls文件名
     * @return 处理结果，true成功，false失败
     * @throws Exception
     */
    public static boolean list2Xls(ExcelConfig config, List<?> list, String filePath, String fileName) throws Exception {
        //创建目录
        File file = new File(filePath);
        if (!(file.exists())) {
            if (!file.mkdirs()) {
                throw new RuntimeException("创建导出目录失败!");
            }
        }
        try {
            String[] header = config.getHeaders();
            String[] names = config.getNames();
            String[] values;

            if (!fileName.toLowerCase().endsWith(EXCEL)) {
                fileName += EXCEL;
            }
            File excelFile = new File(filePath + "/" + fileName);
            WritableWorkbook wb = Workbook.createWorkbook(excelFile);
            String sheetName = (config.getSheet() != null && !config.getSheet().equals("")) ? config.getSheet() : ("sheet" + config.getSheetNum());
            WritableSheet sheet = wb.createSheet(sheetName, 0);

            int row = 0;
            int column = 0;
            int rowadd = 0;
            //写入标题
            if (config.getHeader()) {
                for (column = 0; column < header.length; column++) {
                    sheet.addCell(new Label(column, row + rowadd, header[column]));
                    if (config.getColumn(column).getWidth() != null) {
                        sheet.setColumnView(column, config.getColumn(column).getWidth() / 7);
                    }
                }
                rowadd++;
            }
            //写入内容//行
            for (row = 0; row < list.size(); row++) {
                Object rowData = list.get(row);
                values = getObjValues(rowData, names);
                //列
                for (column = 0; column < values.length; column++) {
                    sheet.addCell(new Label(column, row + rowadd, values[column]));
                }
            }
            wb.write();
            wb.close();
        } catch (Exception e1) {
            return false;
        }
        return true;
    }

    /**
     * 获取对象指定字段值
     *
     * @param source     对象
     * @param fieldnames 属性名数组
     * @return 对象的字符串数组，和属性名对应
     * @throws Exception
     */
    private static String[] getObjValues(Object source, String... fieldnames) throws Exception {
        String[] results = new String[fieldnames.length];
        Field field;
        String value;
        Object obj;
        for (int i = 0; i < fieldnames.length; i++) {
            field = FieldUtil.getField(source, fieldnames[i]);
            if (field == null) {
                continue;
            }
            obj = field.get(source);
            if (obj == null) {
                value = "";
            } else if (obj instanceof Date) {
                value = DateUtil.smartFormat((Date) obj);
            } else {
                value = String.valueOf(obj);
            }
            results[i] = value;
        }
        return results;
    }
    
    /**
     * 导出list对象到excel
     *
     * @param list     导出的list
     * @param xmlPath  xml完整路径
     * @param outputStream 输出流
     * @return 处理结果，true成功，false失败
     * @throws Exception
     */
    public static boolean list2Xls(List<?> list, String xmlPath, OutputStream outputStream) throws Exception {
        try {
            ExcelConfig config = getEasyExcel(xmlPath);
            list2Xls(config, list, outputStream);
        } catch (Exception e1) {
            return false;
        }
        return true;
    }
    
    /**
     * 导出list对象到excel
     *
     * @param config   配置
     * @param list     导出的list
     * @param outputStream 输出流
     * @return 处理结果，true成功，false失败
     * @throws Exception
     */
    public static boolean list2Xls(ExcelConfig config, List<?> list, OutputStream outputStream) throws Exception {
        try {
            String[] header = config.getHeaders();
            String[] names = config.getNames();
            String[] values;

            WritableWorkbook wb = Workbook.createWorkbook(outputStream);
            String sheetName = (config.getSheet() != null && !config.getSheet().equals("")) ? config.getSheet() : ("sheet" + config.getSheetNum());
            WritableSheet sheet = wb.createSheet(sheetName, 0);

            int row = 0;
            int column = 0;
            int rowadd = 0;
            //写入标题
            if (config.getHeader()) {
                for (column = 0; column < header.length; column++) {
                    sheet.addCell(new Label(column, row + rowadd, header[column]));
                    if (config.getColumn(column).getWidth() != null) {
                        sheet.setColumnView(column, config.getColumn(column).getWidth() / 7);
                    }
                }
                rowadd++;
            }
            //写入内容//行
            for (row = 0; row < list.size(); row++) {
                Object rowData = list.get(row);
                values = getObjValues(rowData, names);
                //列
                for (column = 0; column < values.length; column++) {
                    sheet.addCell(new Label(column, row + rowadd, values[column]));
                }
            }
            
            wb.write();
            wb.close();
            outputStream.close();
        } catch (Exception e1) {
            return false;
        }
        return true;
    }

	public static boolean list2Xlsx(List<?> list, String xmlPath,String filename,
 HttpServletResponse response) throws Exception {
		try {
			String path = XlsUtil.class.getResource("/xls/" + xmlPath)
					.getPath();
			ExcelConfig config = getEasyExcel(path);
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
			// response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			// response.setContentType("application/vnd.ms-excel;charset=utf-8");//2003
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			String[] names = config.getNames();
			String[] header = config.getHeaders();
			String[] values;
			int column = 0;
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(config.getSheet());
			XSSFRow row = sheet.createRow(0);

			// 写入标题
			if (config.getHeader()) {
				for (column = 0; column < header.length; column++) {
					row.createCell(column).setCellValue(header[column]);
					sheet.setColumnWidth(column, 13 * 256);
				}
			}
			if (list != null) {
				for (int i = 1; i <= list.size(); i++) {
					// 创建第一个sheet
					// 生成第一行
					row = sheet.createRow(i);
					Object rowData = list.get(i - 1);
					values = getObjValues(rowData, names);

					for (column = 0; column < values.length; column++) {
						if ("index".equals(names[column])) {
							row.createCell(0).setCellValue(i);
						} else {
							row.createCell(column).setCellValue(values[column]);
						}
						sheet.setColumnWidth(column, 13 * 256);
					}

				}
			}
			// 写文件
			wb.write(toClient);
			toClient.flush();
			toClient.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public static List<?> xlsx2List(InputStream input,String xmlName) throws Exception {
		String path=XlsUtil.class.getResource("/xls/"+xmlName).getPath();
		ExcelConfig config = getEasyExcel(path);
		return xlsx2List(input, config);
	}
	
	
	public static boolean checkFileCols(InputStream input,String xmlName) throws Exception{
		String path=XlsUtil.class.getResource("/xls/"+xmlName).getPath();
		ExcelConfig config = getEasyExcel(path);
		String[] names = config.getNames();
		int fileCols = sheetColsCnt(input);
		if(fileCols==0||names.length==0){
			return false;
		}
		if(fileCols==names.length){
			return true;
		}else{
			return false;
		}	
	}
	
	
	
	public static List<?> xlsx2List(InputStream input,ExcelConfig config) throws Exception {
		String[] names = config.getNames();
        String[] types = config.getTypes();
        Field key = config.getKey();
        List<Object> list = new ArrayList<Object>();
		XSSFWorkbook xwb = new XSSFWorkbook(new BufferedInputStream(input));
		int fileCols = sheetColsCntByBook(xwb);//以sheet0的列数为标准
		
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
			XSSFSheet xSheet = xwb.getSheetAt(numSheet);
			if (xSheet == null) {
				continue;
			}

			
			//计算实际行数，不包含空行	
			int i=0;
		    for (i = xSheet.getFirstRowNum(); i <= xSheet.getPhysicalNumberOfRows(); i++) {  
		        if (null == xSheet.getRow(i)) {  
		            break;  
		        }else{
		        	if(rowCellIsnull(xSheet,i,fileCols)){
		        	break;
		        	}
		        }  
		    } 
		 // 循环行Row
//			for (int rowNum = 1; rowNum <= xSheet.getLastRowNum(); rowNum++) { //空行不读取，注释此行			    
				for (int rowNum = 1; rowNum <i ; rowNum++) { 
				XSSFRow xRow = xSheet.getRow(rowNum);
				if (xRow == null) {
					continue;
				}
				//Map类型要特殊处理
	            Class clazz = Class.forName(config.getClazz());
	            Object obj = null;
	            if (Map.class.isAssignableFrom(clazz)) {
	                obj = new HashMap();
	            } else {
	                obj = clazz.newInstance();
	            }
				// 循环列Cell
				for (int cellNum = 0; cellNum <= fileCols-1; cellNum++) {
					XSSFCell xCell = xRow.getCell(cellNum);
					if (xCell == null) {
						continue;
					}
					try{
	                setValue(obj, names[cellNum], types[cellNum], xCell);
					}catch(Exception e){
						e.printStackTrace();	
						 System.out.println("****cellNum "+cellNum+" ,setValue fail,"+e);	
					}
				}
				//checkKey
	            if (key != null) {
	                //当主键为空时，不在继续读取excel
	                if (key.get(obj) == null || "".equals(String.valueOf(key.get(obj)))) {
	                    break;
	                }
	            }
				list.add(obj);
			}
		}
		return list;
	}
	
	
	
	
	
	/**
     * 跟对象obj的某个field赋值value
     *
     * @param obj       属性对象
     * @param fieldName 字段名
     * @param cell      单元格
     * @throws Exception
     */
    private static void setValue(Object obj, String fieldName, String type, XSSFCell cell) throws Exception {
        Object val = null;
    //    Object v = getCellValue(cell); getCellValue处理不够全面，改用getCellValueEx方法
        Object v =getCellValueEx(cell);
        
        if (v == null) {
            //不处理
        } else if (Object.class.getCanonicalName().equals(type)) {
            //类型一致的直接使用
            val = v;
        } else if (v.getClass().getName().equals(type)) {
            //类型一致的直接使用
            val = v;
        } else {
            //类型不一致进行转换
            String value = v.toString();
            if (value != null && !value.trim().equals("")) {
                value = value.trim();
                /**
                 * 对类型进行转换，支持int,long,float,double,boolean,Integer,Long,Double,Float,Date,String
                 */
                if (type.equals("int")) {
                    val = new BigDecimal(value).intValue();
                } else if (type.equals("long")) {
                    val = new BigDecimal(value).longValue();
                } else if (type.equals("float")) {
                    val = new BigDecimal(value).floatValue();
                } else if (type.equals("double")) {
                    val = new BigDecimal(value).doubleValue();
                } else if (type.equals("boolean")) {
                    val = Boolean.parseBoolean(value);
                } else {
                    Class clazz = Class.forName(type);
                    if (!clazz.equals(String.class)) {
                        if (clazz.equals(Date.class)) {
                            val = DateUtil.smartFormat(value);
                        } else if (clazz.equals(Integer.class)) {
                            val = new BigDecimal(value).intValue();
                        } else if (clazz.equals(Long.class)) {
                            val = new BigDecimal(value).longValue();
                        } else if (clazz.equals(Float.class)) {
                            val = new BigDecimal(value).floatValue();
                        } else if (clazz.equals(Double.class)) {
                            val = new BigDecimal(value).doubleValue();
                        } else if (clazz.equals(Boolean.class)) {
                            val = Boolean.parseBoolean(value);
                        } else if (clazz.equals(BigDecimal.class)) {
                            val = new BigDecimal(value);
                        }
                    } else {
                        val = value;
                    }
                }
            }
        }
        Field field = FieldUtil.getField(obj, fieldName);
        if (field != null) {
            field.set(obj, val);
        }
    }
    
    /**
     * 获取首行列数
     *
     * @param cell
     * @return
     * @throws Exception
     */
    private static boolean rowCellIsnull(XSSFSheet xSheet,int row,int cols){  	
    	boolean ckRs =true;
    	for(int j=0;j<cols;j++){
//    		System.out.println("xSheet.getRow("+row+").getCell("+j+") value is "+xSheet.getRow(row).getCell(j).toString());
    		if(xSheet.getRow(row).getCell(j)!=null&&!xSheet.getRow(row).getCell(j).toString().trim().equals("")){
    			return false;
    		}
    	}	
    	return ckRs;
    }
    
    /**
     * 获取首行列数
     *
     * @param InputStream
     * @return
     * @throws Exception
     */
    private static int sheetColsCnt(InputStream input) throws Exception {  	
    	int j=0;
    	try{
    	XSSFWorkbook xwb = new XSSFWorkbook(new BufferedInputStream(input));
    	XSSFSheet xSheet = xwb.getSheetAt(0);
    	for( j = xSheet.getFirstRowNum(); j <xSheet.getRow(0)
				.getPhysicalNumberOfCells(); j++){
					if(xSheet.getRow(0).getCell(j)==null){
						break;
					}
				}
    	}catch(Exception e){
    		throw new Exception("sheetColsCnt 计算xls列数失败！");
    	}    	
    	return j;
    }
    
    
    
    /**
     * 获取首行列数
     *
     * @param InputStream
     * @return
     * @throws Exception
     */
    private static int sheetColsCntByBook(XSSFWorkbook xwb) throws Exception {  	
    	int j=0;
    	try{   	
    	XSSFSheet xSheet = xwb.getSheetAt(0);
    	for( j = xSheet.getFirstRowNum(); j <xSheet.getRow(0)
				.getPhysicalNumberOfCells(); j++){
					if(xSheet.getRow(0).getCell(j)==null){
						break;
					}
				}
    	}catch(Exception e){
    		throw new Exception("计算xls列数失败！");
    	}    	
    	return j;
    }
    
    
    
    /**
     * 获取单元格的数据
     *
     * @param cell
     * @return
     * @throws Exception
     */
    private static Object getCellValue(XSSFCell xCell) throws Exception {
        Object value = null;
        if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
        	value=xCell.getBooleanCellValue();
		} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			value=xCell.getNumericCellValue();
		} else {
			value=xCell.getStringCellValue();
		}
        return value;
    }
    
    
    /**
     * 获取单元格的数据
     *
     * @param cell
     * @return
     * @author kezhiyi
     * @date 20150725
     * @throws Exception
     */
	private static Object getCellValueEx(XSSFCell xCell) throws Exception {
		Object value = null;

		switch (xCell.getCellType()) {
		case XSSFCell.CELL_TYPE_BLANK:
			return "";
		case XSSFCell.CELL_TYPE_BOOLEAN:
			return xCell.getBooleanCellValue() ? "TRUE" : "FALSE";
		case XSSFCell.CELL_TYPE_ERROR:
			return ErrorEval.getText(xCell.getErrorCellValue());
		case XSSFCell.CELL_TYPE_FORMULA:
			return xCell.getCellFormula();
		case XSSFCell.CELL_TYPE_NUMERIC:
			if (org.apache.poi.hssf.usermodel.HSSFDateUtil.isCellDateFormatted(xCell)) {
				try {
					Double d = xCell.getNumericCellValue();
					java.util.Date date = HSSFDateUtil.getJavaDate(d);
					SimpleDateFormat sdf = null;
					if (d.toString().matches("^-?[0-9]*(\\.0+)?$")) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					} else if (d.toString().matches("^-?[0-9]*\\.[1-9]\\d*$")) {
						sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					} else if (d.toString().matches("^-?0\\.[1-9]\\d*$")) {
						sdf = new SimpleDateFormat("HH:mm:ss");
					}
					value = sdf.format(date);
					return sdf.format(xCell.getDateCellValue());
				} catch (Exception e) {
					value = xCell.toString();
					return value;
				}
			}
			
//			Double d = new Double(xCell.toString());
//			if (d.toString().matches("^-?[0-9]*\\.\\d*[1-9]\\d*$")) {
//				value = xCell.toString();
//			} else {
//				DecimalFormat df = new DecimalFormat("0");
//				value = df.format(d);
//			}
			String str=null;  
            double doubleValue = xCell.getNumericCellValue();  
              
                // 是否为数值型  
                double d = xCell.getNumericCellValue(); 
                
             if(Double.toString(d).matches("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$")){
            	 BigDecimal db = new BigDecimal(Double.toString(d));
            	 String ii = db.toPlainString(); 
            	 if(ii.matches("^-?[0-9]*\\.\\d*[1-9]\\d*$")){
            		 value = ii; 
            	 }else{
     				DecimalFormat df1 = new DecimalFormat("0");
     				value = df1.format(db);
            	 }               	 
             }   
             else if(Double.toString(d).matches("^-?[0-9]*\\.\\d*[1-9]\\d*$")){
            	 value = xCell.toString();
             }else {
 				DecimalFormat df = new DecimalFormat("0");
 				value = df.format(d);
             }
             
             
			return value;
		case XSSFCell.CELL_TYPE_STRING:
			return xCell.getRichStringCellValue().toString();
		default:
			return "Unknown Cell Type: " + xCell.getCellType();

		}

	}
    
    
    
}
