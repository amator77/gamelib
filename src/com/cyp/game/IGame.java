package com.cyp.game;

import com.cyp.accounts.Account;


public interface IGame {
		
	public enum STATE { NOT_READY , READY , IN_PROGRESS }
	
	public Account getAccount();
	
	public IChallenge getChallenge();
	
	public void addGameListener(IGameListener listener);
	
	public void removeGameListener(IGameListener listener);	
}
