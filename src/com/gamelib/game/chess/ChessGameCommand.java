package com.gamelib.game.chess;

import com.gamelib.game.IGameCommand;

public interface ChessGameCommand extends IGameCommand {
		
	public static final int MOVE_COMMAND_ID = 2;
	
	public static final int READY_COMMAND_ID = 3;
		
	public static final int DRAW_COMMAND_ID = 5;
	
	public static final int DRAW_ACCEPTED_COMMAND_ID = 6;
		
	public static final int RESIGN_COMMAND_ID = 10;		
}
