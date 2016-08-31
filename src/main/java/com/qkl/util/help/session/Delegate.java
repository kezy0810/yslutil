package com.qkl.util.help.session;


import com.qkl.util.help.UUId;

/**
 * @author liuyang
 *
 */
public class Delegate {

	protected Session session;
	
	public Delegate() {
	}
	
	public Delegate(Session session) {
		this.session = session;
	}
	
	public Session getSession(){
		return getSession(true);
	}
	
	public Session getSession(boolean create){
		if(this.session == null && create){
			Session session = createSession();
			this.session = session;
		}
		return this.session;
	}
	
	private Session createSession(){
		String sessionId = UUId.getUUID();
		Session session = new StandardSession(sessionId);
		return session;
	}
	
}
