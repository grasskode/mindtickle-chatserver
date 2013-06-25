package com.grasskode.chatserver.dao;

import java.util.List;

import com.grasskode.chatserver.elements.IUser;
import com.grasskode.chatserver.exceptions.UserDaoException;

/**
 * We are assuming the existence of two stores here :
 * 
 * 1. A list that keeps track of active logins. This will probably be
 * a shared list that will be accessed by multiple such servers. Possibly
 * a common cache instance.
 * 
 * 2. A datastore that manages user information. We use it here to get a user's
 * connections and possibly populate user details.
 * 
 * @author karan
 */
public interface IUserDao {

	boolean isActive(IUser user) throws UserDaoException;
	void setActive(IUser user, boolean active) throws UserDaoException;
	List<IUser> getConnections(IUser user) throws UserDaoException;

}
