package com.qkl.util.help.session;


/**封装session
 * @author liuyang
 *
 */
public interface Session {

	/**
	 * sessionId
	 * @return String
	 */
	String getId();
	/**
	 * 获取Session
	 * @param name key
	 * @return Object
	 */
	Object getAttribute(String name);
	/**
	 * session放入值
	 * @param name key
	 * @param value value
	 * @return Object
	 */
	Object setAttribut(String name, Object value);
	/**
	 * session移除
	 * @param name key
	 * @return Object
	 */
	Object removeAttribute(String name);
}
