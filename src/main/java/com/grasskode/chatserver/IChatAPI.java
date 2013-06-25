package com.grasskode.chatserver;

import java.util.List;
import java.util.Map;

import com.grasskode.chatserver.elements.IMessage;
import com.grasskode.chatserver.elements.IUser;

public interface IChatAPI {
	
	/**
	 * @param user
	 * @return whether the user is currently logged in
	 */
	public boolean getLoginStatus(IUser user);
	/**
	 * Activate the user's login
	 * @param user
	 */
	public void login(IUser user);
	/**
	 * Deactivate the user's login
	 * @param user
	 */
	public void logout(IUser user);
	
	/**
	 * @param user
	 * @return the user's list of friends/connections
	 */
	public List<IUser> getFriendList(IUser user);
	
	/**
	 * Send the given message to the user.
	 * Publisher to the user's message queue.
	 * @param user
	 * @param msg
	 */
	public void sendMessage(IUser user, IMessage msg);
	
	/**
	 * @param user
	 * @param from
	 * @param to
	 * @return All messages to the given user mapped by the sender
	 */
	public Map<IUser, List<IMessage>> getAllMessages(IUser user, long from, long to);
	
	/**
	 * 
	 * @param user1
	 * @param user2
	 * @param from
	 * @param to
	 * @return Conversation between user1 and user2 sorted by timestamp
	 */
	public List<IMessage> getConversation(IUser user1, IUser user2, long from, long to);

}
