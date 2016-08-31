package com.qkl.util.help;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;


/**
 * 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 * <p>Description： 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装. </p>
 * @project_Name yc_util
 * @class_Name BeanUtil.java
 * @author weigangpeng
 * @date 2015年4月22日
 * @version v1.0
 */
public class BeanUtil extends org.apache.commons.beanutils.BeanUtils {

	protected static final Log logger = LogFactory.getLog(BeanUtil.class);

	private BeanUtil() {
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 *
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 *
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class<?> clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 *
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object).toString();
		} catch (Exception e) {
			logger.info("error wont' happen");
			result="";
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 *
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 *
	 * @throws NoSuchMethodException 如果没有该Method时抛出.
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object... params)
			throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class<?>[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class<?> clazz = object.getClass();
		Method method = null;
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				// 方法不在当前类定义,继续向上转型
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	public static List<Field> getFieldsByType(Object object, Class<?> type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class<?> getPropertyType(Class<?> type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 获得field的getter函数名称.
	 */
	public static String getGetterName(Class<?> type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 */
	public static Method getGetterMethod(Class<?> type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 对象深copy
	 * @param oldObj
	 * @return
	 * @author mawb
	 * @since 1.0
	 */
	public static Object deepCopy(Object oldObj) {   
	    Object obj = null;   
	    try {   
	        // Write the object out to a byte array   
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();   
	        ObjectOutputStream out = new ObjectOutputStream(bos);   
	        out.writeObject(oldObj);   
	        out.flush();   
	        out.close();   
	  
	        // Retrieve an input stream from the byte array and read   
	        // a copy of the object back in.   
	        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());    
	        ObjectInputStream in = new ObjectInputStream(bis);   
	        obj = in.readObject();   
	    } catch (IOException e) {   
	        e.printStackTrace();   
	    } catch (ClassNotFoundException cnfe) {   
	        cnfe.printStackTrace();   
	    }   
	    return obj;   
	}  
	
	/** 
	 * 获得对象字段的值
	 * <p> ()  </p>
	 * @Title: getFieldValue 
	 * @param obj 对象
	 * @param field 字段
	 * @return 字段值
	 * @throws 
	 * @create author weigangpeng
	 * @create date 2015年4月23日
	 */ 
	public static Object getFieldValue(Object obj, String field) {
		Object value = "";
		try {
			value = BeanUtil.forceGetProperty(obj, field);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return value;
	}
	
	/**
	 * java bean转map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> bean2Map(Object obj) {  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
        return map;  
    }  
  
}
