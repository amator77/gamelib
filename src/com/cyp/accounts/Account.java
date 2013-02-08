package com.cyp.accounts;

import com.cyp.game.IGameController;
import com.cyp.transport.Connection;
import com.cyp.transport.Roster;

public interface Account {
	
	public enum TYPE { XMPP_GOOGLE , XMPP_FACEBOOK }
	
	public enum STATUS { ONLINE , AWAY , BUSY , OFFLINE }
		
	public Roster getRoster();
			
	public TYPE getType();
	
	public STATUS getStatus();
	
	public void login(LoginCallback callback) ;
	
	public void logout();
	
	public String getIconTypeResource();
	
	public IGameController getGameController();
	
	public Connection getConnection();
	
	public interface LoginCallback{
		
		public void onLogginSuccess();
		
		public void onLogginError(String errorMessage);
	}
}
