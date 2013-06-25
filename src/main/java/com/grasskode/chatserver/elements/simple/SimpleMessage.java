package com.grasskode.chatserver.elements.simple;

import com.grasskode.chatserver.elements.IMessage;
import com.grasskode.chatserver.elements.IUser;

/**
 * Simple default implementation of a message.
 * Contains the sender info, message and timestamp as provided by the client.
 * 
 * @author karan
 */
public class SimpleMessage implements IMessage {
	
	private IUser sender;
	private String message;
	private long timestamp;
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	public void setSender(IUser sender) {
		this.sender = sender;
	}

	@Override
	public IUser getSender() {
		return sender;
	}

}
