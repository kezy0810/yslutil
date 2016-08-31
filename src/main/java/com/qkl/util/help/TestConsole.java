/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author 作者 liubang
 *<br> @data 2015-6-22 下午3:16:45
 *<br> @version v1.0
 */
/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author 作者 liubang
 *<br> @data 2015-6-22 下午3:16:45
 *<br> @version v1.0
 */
package com.qkl.util.help;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * <p>Description：  </p>
 * @project_Name yc_crm_web
 * @class_Name TestConsole.java
 * @author liubang
 * @date 2015-6-22 下午3:16:45
 * @version v1.0
 */

public class TestConsole {
	public static void main(String[] args) {
		try {
			final RandomAccessFile randomFile = new RandomAccessFile("E:/home/project/logs/crm_manager_web/normal.log", "r");
			ScheduledExecutorService  exec = Executors.newScheduledThreadPool(1);
			exec.scheduleWithFixedDelay(new Runnable() {
				private long lastTimeFileSize = 0;  //上次文件大小  
				@Override
				public void run() {
					try {
						randomFile.seek(lastTimeFileSize);
						
						String tmp ="";
						while((tmp = randomFile.readLine())!=null ){
							System.out.println(new String(tmp.getBytes("ISO8859-1"),"GBK"));
						}
						lastTimeFileSize = randomFile.length();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}, 0, 1, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
