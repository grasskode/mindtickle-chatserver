package com.grasskode.chatserver.dao;

import java.util.List;

import com.grasskode.chatserver.elements.IMessage;
import com.grasskode.chatserver.elements.IUser;
import com.grasskode.chatserver.exceptions.MessageDaoException;

/**
 * A message DAO that stores and retrieves messages.
 * Storing is straight forward - based on the receiver
 * Fetches are for a user between a given timeslice and can be filtered by a sender user.
 * 
 * @author karan
 *
 */
public interface IMessageDao {

	void put(IUser to, IMessage msg) throws MessageDaoException;
	
	/**
	 * Returns all the msgs by user2 to user1 between the from and to timestamps.
	 * Returns all msgs to user1 by any user if user2 is null.
	 * 
	 * @param user1
	 * @param user2
	 * @param from
	 * @param to
	 * @return
	 * @throws MessageDaoException
	 */
	List<IMessage> get(IUser user1, IUser user2, long from, long to) throws MessageDaoException;

}
