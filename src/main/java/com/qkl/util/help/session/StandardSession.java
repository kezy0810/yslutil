package com.qkl.util.help.session;


import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**session操作实现
 * @author liuyang
 *
 */
public class StandardSession implements Session,Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Map attributes = new ConcurrentHashMap();
	
	protected String id = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public StandardSession(String id) {
		this.id = id;
	}

	@Override
	public Object getAttribute(String name) {
		if(null == name)  return null;
		return attributes.get(name);
	}

	@Override
	public Object setAttribut(String name, Object value) {
		if(null == name)
			throw new IllegalArgumentException("seesion must be not null");
		if(null == value){
			removeAttribute(name);
			return null;
		}
		Object unbound = attributes.put(name, value);
		return unbound;
	}

	@Override
	public Object removeAttribute(String name) {
		if(null == name){
			return null;
		}else{
			Object value = attributes.remove(name);
			return value;
		}
	}

}
