package com.gamelib.game.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gamelib.accounts.Account;
import com.gamelib.game.IChallenge;
import com.gamelib.game.IGame;
import com.gamelib.game.IGameCommand;
import com.gamelib.game.IGameListener;

public class GenericGame implements IGame {
	
	private Account account;
			
	private IChallenge challenge;
	
	private List<IGameListener> listeners;
	
	public GenericGame(Account account,IChallenge challenge){
		this.account = account;				
		this.challenge = challenge;
		this.listeners = new ArrayList<IGameListener>();
	}
	
	@Override
	public IChallenge getChallenge() {
		return this.challenge;
	}

	@Override
	public void addGameListener(IGameListener listener) {	
		if( !this.listeners.contains(listener)){
			this.listeners.add(listener);
		}		
	}

	@Override
	public void removeGameListener(IGameListener listener) {
		if( this.listeners.contains(listener)){
			this.listeners.add(listener);
		}		
	}

	@Override
	public void sendCommand(IGameCommand cmd) throws IOException {
		
		if (this.account.getConnection().isConnected()) {
			this.account.getConnection().sendMessage(cmd);
		}					
	}
	
	public List<IGameListener> getGameListeners(){
		return this.listeners;
	}
}
