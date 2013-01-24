package com.gamelib.game.chess;

import com.gamelib.game.IGameListener;

public interface ChessGameListener extends IGameListener {
	
	public void readyReceived();
	
	public void resignReceived();
	
	public void chatReceived(String text);
	
	public void moveReceived(Move move);
	
	public void drawRequestReceived();
	
	public void drawAcceptedReceived();
	
	public void abortRequestReceived();
	
	public void abortAcceptedReceived();
	
	public void rematchRequestReceived();
	
	public void rematchAcceptedReceived();
	
	public void gameClosedReceived();
}
