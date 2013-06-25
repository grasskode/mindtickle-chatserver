package com.grasskode.chatserver.elements;


public interface IMessage {
	
	public long getTimestamp();
	public String getMessage();
	public IUser getSender();

}
