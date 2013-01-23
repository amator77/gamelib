package com.gamelib.game.chess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gamelib.game.IChallenge;
import com.gamelib.game.IGame;
import com.gamelib.game.IGameCommand;
import com.gamelib.game.IGameListener;

public class ChessGame implements IGame {
	
	private IChallenge challenge;
	
	private List<IGameListener> listeners;
	
	public ChessGame(IChallenge challenge){
		this.challenge = challenge;
		this.listeners = new ArrayList<IGameListener>();
		
	}
	
	@Override
	public IChallenge getChallenge() {
		return this.challenge;
	}

	@Override
	public void addGameListener(IGameListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGameListener(IGameListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendCommand(IGameCommand command) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	
}
