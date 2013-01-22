package com.gamelib.transport;

public interface ConnectionListener {
	
	public void messageReceived( Connection source, Message message);
		
	public void onDisconect(Connection source);		
}
