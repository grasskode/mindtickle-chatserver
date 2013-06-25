package com.grasskode.chatserver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.grasskode.chatserver.dao.IMessageDao;
import com.grasskode.chatserver.dao.IUserDao;
import com.grasskode.chatserver.elements.IMessage;
import com.grasskode.chatserver.elements.IUser;
import com.grasskode.chatserver.exceptions.MessageDaoException;
import com.grasskode.chatserver.exceptions.UserDaoException;

/**
 * A simple implementation of IChatDao. This is mostly an intermediate layer.
 * Needs to be fronted by a server that receives requests and calls this API.
 * 
 * @author karan
 *
 */
public class ChatAPI implements IChatAPI {
	
	IUserDao userDao;
	IMessageDao messageDao;
	
	/**
	 * The initialization will be done with instances
	 * of user and message DAOs. Actual design around whether
	 * this will be a class or a singleton or a static class will
	 * depend on how the entire project is bootstrapped.
	 * 
	 * @param userDao
	 * @param messageDao
	 */
	public ChatAPI(IUserDao userDao, IMessageDao messageDao) {
		this.userDao = userDao;
		this.messageDao = messageDao;
	}

	@Override
	public boolean getLoginStatus(IUser user) {
		try{
			return userDao.isActive(user);
		}
		catch (UserDaoException e) {
			// Log the exception
		}
		return false;
	}

	@Override
	public void login(IUser user) {
		try{
			userDao.setActive(user, true);
		}
		catch (UserDaoException e){
			// Log the exception
			// possibly retry
		}
	}

	@Override
	public void logout(IUser user) {
		try{
			userDao.setActive(user, false);
		}
		catch (UserDaoException e){
			// Log the exception
			// possibly retry
		}
	}

	@Override
	public List<IUser> getFriendList(IUser user) {
		try{
			return userDao.getConnections(user);
		}
		catch (UserDaoException e) {
			// Log the exception
		}
		return null;
	}

	@Override
	public void sendMessage(IUser to, IMessage msg) {
		try{
			messageDao.put(to, msg);
		}
		catch (MessageDaoException e){
			// Log the exception
			// maybe the sender should be notified of the message delivery failure
			// retry if retry is not implemented at the DAO layer, which it should be
		}
	}

	@Override
	public Map<IUser, List<IMessage>> getAllMessages(IUser user, long from, long to) {
		try{
			List<IMessage> allMessages = messageDao.get(user, null, from, to);
			Map<IUser, List<IMessage>> msgMap = new HashMap<IUser, List<IMessage>>();
			for(IMessage msg : allMessages){
				if(!msgMap.containsKey(msg.getSender()))
					msgMap.put(msg.getSender(), new LinkedList<IMessage>());
				msgMap.get(msg.getSender()).add(msg);
			}
			return msgMap;
		}
		catch (MessageDaoException e){
			// Log the exception
		}
		return null;
	}

	@Override
	public List<IMessage> getConversation(IUser user1, IUser user2, long from, long to) {
		try{
			List<IMessage> msgList1 = messageDao.get(user1, user2, from, to);
			List<IMessage> msgList2 = messageDao.get(user2, user1, from, to);
			
			return weave(msgList1, msgList2);
		}
		catch (MessageDaoException e){
			// Log the exception
		}
		return null;
	}

	/**
	 * Weaves two message lists that are in sorted order (by timestamp)
	 * 
	 * @param msgList1
	 * @param msgList2
	 * @return merged message list
	 */
	private List<IMessage> weave(List<IMessage> msgList1, List<IMessage> msgList2) {
		Iterator<IMessage> iter1 = msgList1.iterator();
		Iterator<IMessage> iter2 = msgList2.iterator();
		
		List<IMessage> merged = new LinkedList<IMessage>();
		IMessage msg1 = null;
		IMessage msg2 = null;
		while(iter1.hasNext() && iter2.hasNext()){
			if(!iter1.hasNext()){
				while(iter2.hasNext())
					merged.add(iter2.next());
			}
			
			if(!iter2.hasNext()){
				while(iter1.hasNext())
					merged.add(iter1.next());
			}
			
			if(msg1 == null) msg1 = iter1.next();
			if(msg2 == null) msg2 = iter2.next();
			
			if(msg1.getTimestamp() < msg2.getTimestamp()){
				merged.add(msg1);
				msg1 = null;
			}
			else {
				merged.add(msg2);
				msg2 = null;
			}
		}
		
		return merged;
	}

}
