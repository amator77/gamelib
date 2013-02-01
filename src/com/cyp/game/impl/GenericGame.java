package com.cyp.game.impl;

import java.util.ArrayList;
import java.util.List;

import com.cyp.accounts.Account;
import com.cyp.game.IChallenge;
import com.cyp.game.IGame;
import com.cyp.game.IGameListener;

public class GenericGame implements IGame {

	private Account account;

	private IChallenge challenge;

	private List<IGameListener> listeners;

	public GenericGame(Account account, IChallenge challenge) {
		this.account = account;
		this.challenge = challenge;
		this.listeners = new ArrayList<IGameListener>();
	}
	
	public Account getAccount() {
		return account;
	}

	public IChallenge getChallenge() {
		return this.challenge;
	}

	public void addGameListener(IGameListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}
	
	public void removeGameListener(IGameListener listener) {
		if (this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public List<IGameListener> getGameListeners() {
		return this.listeners;
	}
}
