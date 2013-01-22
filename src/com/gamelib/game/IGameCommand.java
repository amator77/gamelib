package com.gamelib.game;

import com.gamelib.transport.Message;


public interface IGameCommand extends Message {
	
	public static final String GAME_COMMAND_HEADER_KEY = "cmd";
	
	public static final int UNKNOWN_COMMAND_ID = -1;
	
	public static final int CHALLENGE_COMMAND_ID = 0;
	
	public static final int CHALLENGE_ACCEPTED_COMMAND_ID = 1;
	
	public static final int CHALLENGE_CANCELED_COMMAND_ID = 2;
	
	public static final int CHALLENGE_REJECTED_COMMAND_ID = 3;
	
	public static final int ABORT_COMMAND_ID = 4;
	
	public static final int ABORT_ACCEPTED_COMMAND_ID = 5;
	
	public static final int CHAT_COMMAND_ID = 6;
	
	public int getId();	
}
