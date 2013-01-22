package com.gamelib.game.chess;
import com.gamelib.accounts.Account;
import com.gamelib.game.impl.GameController;
import com.gamelib.transport.Connection;
import com.gamelib.transport.Message;


public class ChessGameController extends GameController {
	 	
	public ChessGameController(Account account){
		super(account);	
	}
	
	@Override
	public void messageReceived(Connection source, Message message) {
		super.messageReceived(source, message);
		
	}
	
}
