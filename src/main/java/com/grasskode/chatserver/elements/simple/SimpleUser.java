package com.grasskode.chatserver.elements.simple;

import com.grasskode.chatserver.elements.IUser;

/**
 * Simple implementation of a User.
 * Just contains the user login which is an identifiable attribute.
 * 
 * @author karan
 */
public class SimpleUser implements IUser {
	
	private String login;
	
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getLogin() {
		return login;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IUser)
			return login.equals(((IUser) obj).getLogin());
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return login.hashCode();
	}

}
