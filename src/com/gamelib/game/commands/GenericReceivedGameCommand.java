package com.gamelib.game.commands;

import com.gamelib.game.IGameCommand;
import com.gamelib.transport.Message;

public abstract class GenericReceivedGameCommand implements IGameCommand {
	
	private Message message;
	
	public GenericReceivedGameCommand(Message message){		
		this.message = message;
	}
	
	public int getId(){
		String headerId = message.getHeader("id");
		
		if( headerId != null ){
			return Integer.parseInt(message.getHeader("id"));
		}
		else{
			return UNKNOWN_COMMAND_ID;
		}
	}
	
	public String getCommand(){
		return this.message.getBody();
	}

	public Message getMessage() {
		return message;
	}	
}
