package com.gamelib.game;

import java.io.IOException;

public interface IGame {
		
	public enum STATE { NOT_READY , READY , IN_PROGRESS }
		
	public IChallenge getChallenge();
	
	public void addGameListener(IGameListener listener);
	
	public void removeGameListener(IGameListener listener);
	
	public void sendCommand(IGameCommand command) throws IOException;
}
