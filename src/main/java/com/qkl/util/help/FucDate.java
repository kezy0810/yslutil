package com.qkl.util.help;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.DateFunc;

public class FucDate {
	public static String timeFuc(int time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = new Date();
        // 创建基于当前时间的日历对象
		
        Calendar cl = Calendar.getInstance();
        cl.setTime(endDate);
        String start = "";
        Date startDate = null;
        try {
            switch (time) {
            	case 0:
                    // 当天时间
                    cl.add(Calendar.DATE, -0);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 1:
                    // 当天时间
                    cl.add(Calendar.DATE, -1);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 2:
                    // 近2天
                    cl.add(Calendar.DATE, -2);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 3:
                    // 近3天
                    cl.add(Calendar.DATE, -3);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 4:
                    // 近一周
                    cl.add(Calendar.DATE, -6);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 5:
                    // 近半月
                    cl.add(Calendar.DATE, -14);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 6:
                    // 近一月
                    cl.add(Calendar.MONTH, -1);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 7:
                    // 近三个月
                    cl.add(Calendar.MONTH, -3);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 8:
                	format = new SimpleDateFormat("yyyy");
                	start = format.format(endDate);
                	start += "-01-01";
                    break;
                case 41:
                    // 近一周
                    cl.add(Calendar.DATE, -7);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 51:
                    // 近半月
                    cl.add(Calendar.DATE, -15);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 61:
                    // 近一月
                    cl.add(Calendar.MONTH, -1);
                    cl.add(Calendar.DATE, -1);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 71:
                    // 近三个月
                    cl.add(Calendar.MONTH, -3);
                    cl.add(Calendar.DATE, -1);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 81:
                	format = new SimpleDateFormat("yyyy");
                	start = format.format(endDate);
                	start += "-01-01";
                	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                	Date date = format1.parse(start);
                	Calendar c2 = Calendar.getInstance();
	                c2.setTime(date);
	                c2.add(Calendar.DATE, -1);
	                startDate = c2.getTime();
	                // 格式化开始日期和结束日期
	                start = format1.format(startDate);
                    break;    
                case 9:
                    // 近七天
                    cl.add(Calendar.DATE, -6);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 10:
                    // 本周周一
                	while (cl.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                        cl.add(Calendar.DATE, -1);
                    }
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 11:
                    // 近30天
                    cl.add(Calendar.DATE, -29);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 12:
                    // 本月初一
                	cl.set(Calendar.DAY_OF_MONTH, cl.getActualMinimum(Calendar.DAY_OF_MONTH)); 
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 14:
                    // 本周周日
                	while (cl.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        cl.add(Calendar.DATE, 1);
                    }
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 15:
                    // 本月月末
                	cl.set(Calendar.DAY_OF_MONTH, cl.getActualMaximum(Calendar.DAY_OF_MONTH));  
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start += format.format(startDate);
                    break;
                case 16:
                	 // 近5天
                    cl.add(Calendar.DATE, -5);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                case 17:
                	 //计算据今天3天后的时间（用于提醒投资到期）
                    cl.add(Calendar.DATE, 3);
                    startDate = cl.getTime();
                    // 格式化开始日期和结束日期
                    start = format.format(startDate);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return start;
	}
	/**
	 * 
	 * <p> (根据时间获取类型)  </p>
	 * @Title: getTimeType 
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 1、 7d、week 300d、month
	 * @throws 
	 * @create author liubang
	 * @create date 2015-4-27
	 */
	public static String getTimeType(int timeType){
		String timeString="1";
		 switch (timeType) {
		 	case 2:
		 		timeString="1";
		 		break;
		 	case 9:
		 		timeString="7d";
		 		break;
		 	case 10:
		 		timeString="week";
		 		break;
		 	case 11:
		 		timeString="30d";
		 		break;
		 	case 12:
		 		timeString="month";
		 		break;
		 	default:
		 		break;
		 }
		return timeString;
	}
	public static List<String> getTimeDate(int timeType){
		List<String> list = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = new Date();
		if(timeType == 2){
			for(int i = 0;i<24;i++){
				list.add(getHour(""+i));
			}
		}else if(timeType == 9){
			Calendar cl = Calendar.getInstance();
	        try {
				cl.setTime(format.parse(timeFuc(9)));
				for(int i = 0;i<7;i++){
					list.add(format.format( cl.getTime()));
					cl.add(Calendar.DATE, 1);
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(timeType == 10){
			Calendar cl = Calendar.getInstance();
	        try {
				cl.setTime(format.parse(timeFuc(10)));
				for(int i = 0;i<7;i++){
					list.add(format.format( cl.getTime()));
					cl.add(Calendar.DATE, 1);
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(timeType == 11){
			Calendar cl = Calendar.getInstance();
	        try {
				cl.setTime(format.parse(timeFuc(11)));
				for(int i = 0;i<30;i++){
					list.add(format.format( cl.getTime()));
					cl.add(Calendar.DATE, 1);
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(timeType == 12){
			Calendar cl = Calendar.getInstance();
	        try {
				cl.setTime(format.parse(timeFuc(12)));
				while(true){
					list.add(format.format( cl.getTime()));
					cl.add(Calendar.DATE, 1);
					if(format.format( cl.getTime()).equals(timeFuc(15))){
						list.add(format.format( cl.getTime()));
						break;
					}
					
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(timeType == 8){
			String time=timeFuc(8).substring(0,4);
			for(int i=1; i<=12; i++){
				if(i<10){
				list.add(time+"-"+"0"+i);
			}else{
				list.add(time+"-"+i);
				}
			}
			
		}
		return list;
	}
	private static String getHour(String hour) {
		if (StringUtils.length(hour) == 1) {
			return "0" + hour + ":00";
		} else {
			return hour + ":00";
		}
	}
	public static void main(String[] args){
		System.out.println("今天："+timeFuc(1));
		System.out.println("近两天："+timeFuc(2));
		System.out.println("近3天："+timeFuc(3));
		System.out.println("近一周："+timeFuc(4));
		System.out.println("近半个月："+timeFuc(5));
		System.out.println("近一个月："+timeFuc(6));
		System.out.println("近3个月："+timeFuc(7));
		System.out.println("近一年："+timeFuc(8));
		
		System.out.println("近七天："+timeFuc(9));
		System.out.println("本周内："+timeFuc(10));
		System.out.println("近30天："+timeFuc(11));
		System.out.println("本月内："+timeFuc(12));
		System.out.println("本周周末："+timeFuc(14));
		System.out.println("本月月末："+timeFuc(15));
		System.out.println("3天后时间："+timeFuc(17));
		System.out.println("getTimeDate2:"+getTimeDate(2));
		System.out.println("getTimeDate9:"+getTimeDate(9));
		System.out.println("getTimeDate10:"+getTimeDate(10));
		System.out.println("getTimeDate11:"+getTimeDate(11));
		System.out.println("getTimeDate12:"+getTimeDate(12));
		System.out.println("getTimeDate8:"+getTimeDate(8));
		
	}
}
