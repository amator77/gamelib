package com.gamelib.game.chess;

import com.gamelib.game.IGameCommand;

public interface ChessGameCommand extends IGameCommand {
	
	public static final String HEADER_ID_KEY = "gid";
	
	public static final int MOVE_COMMAND_ID = 8;	
		
	public static final int DRAW_COMMAND_ID = 9;
	
	public static final int DRAW_ACCEPTED_COMMAND_ID = 10;
		
	public static final int RESIGN_COMMAND_ID = 11;
	
	public static final int ABORT_COMMAND_ID = 12;
	
	public static final int ABORT_ACCEPTED_COMMAND_ID = 13;
	
	public static final int REMATCH_COMMAND_ID = 14;
	
	public static final int REMATCH_ACCEPTED_COMMAND_ID = 15;
	
	public static final int GAME_CLOSED_COMMAND_ID = 16;
}
