package com.qkl.util.help;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qkl.util.help.test.Student;

public class ListUtil {

	/**
	 * list转字符串
	 * <p>
	 * (描述)
	 * </p>
	 * 
	 * @Title: list2String
	 * @param @param list
	 * @param @param field
	 * @param @return
	 * @throws
	 * @author wanghaolong
	 * @date 2015年11月13日 下午2:32:45
	 */
	public static <E> String list2String(List<E> list, String field) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				E temp = list.get(i);
				Object value = getValue(temp, field);
				if (i < list.size() - 1) {
					sb.append(value + ",");
				} else {
					sb.append(value);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * list转map
	 * <p>
	 * (描述)
	 * </p>
	 * 
	 * @Title: toMap
	 * @param @param list
	 * @param @param field
	 * @param @return
	 * @throws
	 * @author wanghaolong
	 * @date 2015年11月13日 下午2:28:39
	 */
	public static <E> Map<String, E> toMap(List<E> list, String field) {
		Map<String, E> map = new HashMap<String, E>();
		for (E e : list) {
			Object value = getValue(e, field);
			map.put(String.valueOf(value), e);
		}
		return map;
	}

	/**
	 * 集合去重，field是要排重的字段
	 * <p>
	 * (描述)
	 * </p>
	 * 
	 * @Title: removeDuplicate
	 * @param @param list
	 * @param @param field
	 * @param @return
	 * @throws
	 * @author wanghaolong
	 * @date 2015年10月14日 下午3:56:35
	 */
	public static <E> List<E> removeDuplicate(List<E> list, String field) {
		int len = list.size();
		for (int i = 0; i < len - 1; i++) {
			E temp = list.get(i);
			Object value = getValue(temp, field);
			for (int j = i + 1; j < len; j++) {

				if (value.equals(getValue(list.get(j), field))) {
					list.remove(j);
					j--;
					len--;
				}
			}
		}
		return list;
	}

	/**
	 * 集合去重，args是要排重的不固定字段,duplist排重的集合，list不重复的集合
	 * @param list
	 * @param args
	 * @return
	 */
	public static <E> Map<String, List<E>> removeDuplicate(List<E> list,
			String... args) {
		Map<String, List<E>> ret = new HashMap<String, List<E>>();
		List<E> duplist = new ArrayList<E>();
		int len = list.size();
		Map map = new HashMap();
		for (int i = 0; i < len - 1; i++) {
			boolean yflag = false;
			E temp = list.get(i);
			for (String field : args) {
				map.put(field, getValue(temp, field));
			}
			boolean flag = false;
			for (int j = i + 1; j < len; j++) {
				for (int j2 = 0; j2 < args.length; j2++) {
					String field = args[j2];
					if (j2 == 0) {
						if (map.get(field).equals(getValue(list.get(j), field))) {
							flag = true;
							continue;
						} else {
							flag = false;
							continue;
						}
					} else if (j2 > 0 && flag) {
						if (map.get(field).equals(getValue(list.get(j), field))) {
							flag = true;
							continue;
						} else {
							flag = false;
							continue;
						}
					} else if (j2 > 0 && !flag) {
						break;
					}
				}
				if (flag) {
					duplist.add(list.get(j));
					list.remove(j);
					j--;
					len--;
					yflag=true;
				}
			}
			if(yflag){
				duplist.add(list.get(i));
				list.remove(i);
				i--;
				len--;
			}
		}
		ret.put("duplist", duplist);
		ret.put("list", list);
		return ret;
	}

	public static <E> Object getValue(E temp, String field) {
		Class cl = (Class) temp.getClass();
		Field[] fs = cl.getDeclaredFields();
		Object value = null;
		for (Field f : fs) {
			f.setAccessible(true);
			if (f.getName().equals(field)) {
				try {
					value = f.get(temp);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		}
		return value;
	}

	public static void main(String[] args) {
		Student s = new Student();
		s.setAge(20);
		s.setName("1");

		List<Student> list = new ArrayList<Student>();
		list.add(s);

		s = new Student();
		s.setAge(20);
		s.setName("2");

		list.add(s);
		s = new Student();
		s.setAge(30);
		s.setName("3");

		list.add(s);
		s = new Student();
		s.setAge(30);
		s.setName("3");
		list.add(s);

		s = new Student();
		s.setAge(40);
		s.setName("3");

		list.add(s);

		for (Student student : list) {
			System.out.println(student.getAge() + "," + student.getName());
		}

		System.out.println("---------");
		Map<String, List<Student>> l1 = removeDuplicate(list, "age", "name");
		
		for (Student student : l1.get("list")) {
			System.out.println("list:"+student.getAge() + "," + student.getName());
		}
		for (Student student : l1.get("duplist")) {
			System.out.println("duplist:"+student.getAge() + "," + student.getName());
		}
		System.out.println("---------");
		List<Student> l2 = removeDuplicate(list, "name");

		for (Student student : l2) {

			System.out.println(student.getAge() + "," + student.getName());
		}

		System.out.println("+++++++++++");
		Map<String, Student> map = toMap(list, "name");
		for (Map.Entry<String, Student> student : map.entrySet()) {
			System.out.println(student.getValue().getName());
		}

		System.out.println("+++++++++++");
		String ls = list2String(list, "name");
		System.out.println(ls);
	}
}
