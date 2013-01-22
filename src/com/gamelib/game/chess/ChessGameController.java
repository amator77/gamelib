package com.gamelib.game.chess;
import java.util.ArrayList;
import java.util.List;

import com.gamelib.accounts.Account;
import com.gamelib.game.IChallenge;
import com.gamelib.game.IGame;
import com.gamelib.game.IGameController;
import com.gamelib.game.IGameControllerListener;
import com.gamelib.transport.Connection;
import com.gamelib.transport.ConnectionListener;
import com.gamelib.transport.Message;


public class ChessGameController implements IGameController , ConnectionListener {
	
	public static final int GAME_CHAT = 1;
	
	public static final int GAME_MOVE = 2;
	
	public static final int GAME_START_REQUEST = 3;
	
	public static final int GAME_START_ACCEPTED = 4;
	
	public static final int GAME_DRAW_REQUEST = 5;
	
	public static final int GAME_DRAW_ACCEPTED = 6;
	
	public static final int GAME_ABORT_REQUEST = 7;
	
	public static final int GAME_ABORT_ACCEPTED = 8;
	
	public static final int GAME_RESIGN = 10;
	
	public static final int GAME_UI_CLOSED = 11;

	public static final String GAME_COMMAND_PAYLOAD = "gcmp";
	
	public static final String GAME_COMMAND = "gcm";
	
	public static final String GAME_WHITE_PLAYER = "wp";
	
	public static final String GAME_BLACK_PLAYER = "bp";
	
	private List<IGameControllerListener> listeners;
	
	private Account account;
	
	private IGame game; 
	
	public ChessGameController(Account account){
		this.account = account;
		this.listeners = new ArrayList<IGameControllerListener>();
		this.account.getConnection().addConnectionListener(this);
	}
	
	@Override
	public void addGameControllerListener(IGameControllerListener listener) {
		if( !this.listeners.contains(listener)){
			this.listeners.add(listener);
		}		
	}

	public void removeGameControllerListener(IGameControllerListener listener) {		
		if( this.listeners.contains(listener)){
			this.listeners.remove(listener);
		}		
	}
	
	@Override
	public void acceptChallenge(IChallenge challenge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rejectChallenge(IChallenge challenge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageReceived(Connection source, Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconect(Connection source) {
	}
}
