package com.qkl.util.help.excel.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
  
import org.jdom.Document;  
import org.jdom.Element;  
import org.jdom.JDOMException;  
import org.jdom.input.SAXBuilder;
/**
 * xml和对象互转
 *
 * @author liuzh
 */
public class XmlUtil {
	private static final String ENCODING = "UTF-8";

	/**
	 * 从xml文件构建
	 *
	 * @param xmlPath
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T fromXml(File xmlPath, Class<T> type) {
		BufferedReader reader = null;
		StringBuilder sb = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(xmlPath), ENCODING));
			String line = null;
			sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return fromXml(sb.toString(), type);
	}

	/**
	 * 从xml构建
	 *
	 * @param xml
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T fromXml(String xml, Class<T> type) {
		if (xml == null || xml.trim().equals("")) {
			return null;
		}
		JAXBContext jc = null;
		Unmarshaller u = null;
		T object = null;
		try {
			jc = JAXBContext.newInstance(type);
			u = jc.createUnmarshaller();
			object = (T) u.unmarshal(new ByteArrayInputStream(xml
					.getBytes(ENCODING)));
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return object;
	}

	/**
	 * 对象转xml并保存到文件
	 *
	 * @param object
	 * @return
	 */
	public static boolean toXml(Object object, File xml) {
		if (object == null) {
			throw new NullPointerException("object对象不存在!");
		}
		JAXBContext jc = null;
		Marshaller m = null;
		try {
			jc = JAXBContext.newInstance(object.getClass());
			m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
			m.marshal(object, xml);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对象转xml
	 *
	 * @param object
	 * @return
	 */
	public static String toXml(Object object) {
		if (object == null) {
			throw new NullPointerException("object对象不存在!");
		}
		JAXBContext jc = null;
		Marshaller m = null;
		String xml = null;
		try {
			jc = JAXBContext.newInstance(object.getClass());
			m = jc.createMarshaller();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			m.marshal(object, bos);
			xml = new String(bos.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return xml;
	}
	
	 
	 /** 
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。 
     * @param strxml 
     * @return 
     * @throws JDOMException 
     * @throws IOException 
     */  
      public static Map doXMLParse(String strxml) throws JDOMException, IOException {  
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");  
  
        if(null == strxml || "".equals(strxml)) {  
            return null;  
        }  
          
        Map m = new HashMap();  
          
        InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));  
        SAXBuilder builder = new SAXBuilder();  
        Document doc = builder.build(in);  
        Element root = doc.getRootElement();  
        List list = root.getChildren();  
        Iterator it = list.iterator();  
        while(it.hasNext()) {  
            Element e = (Element) it.next();  
            String k = e.getName();  
            String v = "";  
            List children = e.getChildren();  
            if(children.isEmpty()) {  
                v = e.getTextNormalize();  
            } else {  
                v = getChildrenText(children);  
            }  
              
            m.put(k, v);  
        }  
          
        //关闭流  
        in.close();  
          
        return m;  
    }  
      
    /** 
     * 获取子结点的xml 
     * @param children 
     * @return String 
     */  
    public static String getChildrenText(List children) {  
        StringBuffer sb = new StringBuffer();  
        if(!children.isEmpty()) {  
            Iterator it = children.iterator();  
            while(it.hasNext()) {  
                Element e = (Element) it.next();  
                String name = e.getName();  
                String value = e.getTextNormalize();  
                List list = e.getChildren();  
                sb.append("<" + name + ">");  
                if(!list.isEmpty()) {  
                    sb.append(getChildrenText(list));  
                }  
                sb.append(value);  
                sb.append("</" + name + ">");  
            }  
        }  
          
        return sb.toString();  
    }  
      
	
      
	
}
