/**
 * 
 * 版权声明钰诚集团北京数据中心，版权所有违者必究
 *
 *<br> Copyright：Copyright (c) 2015
 *<br> Company：钰诚集团北京数据中心
 *<br> @author yanglei
 *<br> @data 2015年7月1日 下午3:10:11
 *<br> @version v1.0
 */
package com.qkl.util.help.dozer;

import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.google.common.collect.Lists;

/**
 *
 * <p>
 * Description：
 * </p>
 * 
 * @project_Name yc_util
 * @class_Name BeanMapper.java
 * @author yanglei
 * @date 2015年7月1日 下午3:10:11
 * @version v1.0
 */

public class BeanMapper {

	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * 基于Dozer转换对象的类型.
	 */
	public static <T> T map(final Object source, final Class<T> destinationClass) {
		return dozer.map(source, destinationClass);
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 */
	public static <T> List<T> mapList(final Collection sourceList,
			final Class<T> destinationClass) {
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * 基于Dozer将对象A的值拷贝到对象B中.
	 */
	public static void copy(final Object source, final Object destinationObject) {
		dozer.map(source, destinationObject);
	}

}