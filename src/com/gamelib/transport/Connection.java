package com.gamelib.transport;


import com.gamelib.transport.Presence.MODE;
import com.gamelib.transport.exceptions.ConnectionException;
import com.gamelib.transport.exceptions.LoginException;

public interface Connection {
	
	public void sendMessage(Message message) throws ConnectionException;
	
	public void sendPresence(MODE status) throws ConnectionException;
		
	public void login(String id,String credentials) throws ConnectionException,LoginException;
	
	public void logout();
	
	public void addConnectionListener(ConnectionListener listener);
	
	public Roster getRoster();
	
	public boolean isConnected();
}
