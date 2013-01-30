package com.cyp.transport;

public interface ConnectionListener {
	
	/*
	 * The implementation need to return true only if this message is consumed 
	 */
	public boolean messageReceived( Connection source, Message message);
		
	public void onDisconect(Connection source);		
}
