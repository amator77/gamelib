package com.cyp.transport;


import java.io.IOException;

import com.cyp.transport.Presence.MODE;
import com.cyp.transport.exceptions.ConnectionException;
import com.cyp.transport.exceptions.LoginException;

public interface Connection {
	
	public void sendMessage(Message message) throws IOException;
	
	public void sendPresence(MODE status) throws IOException;
		
	public void login(String id,String credentials) throws IOException,LoginException;
	
	public void logout();
	
	public void addConnectionListener(ConnectionListener listener);
	
	public Roster getRoster();
	
	public boolean isConnected();
}
