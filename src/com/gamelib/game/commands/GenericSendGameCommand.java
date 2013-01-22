package com.gamelib.game.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gamelib.game.IGameCommand;

public class GenericSendGameCommand implements IGameCommand {
	
	private int commandId;
	
	private String from;
	
	private String to;
	
	private Map<String, String> header;
	
	public GenericSendGameCommand(int commandId){
		this.commandId = commandId;
		this.header = new HashMap<String, String>();
	}
	
	@Override
	public int getId() {		
		return this.commandId;
	}		
	
	public String getFrom(){
		return this.from;
	}
	
	public String getTo(){
		return this.to;
	}
			
	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setHeaderProperty(String key,String value){
		this.header.put(key, value);
	}
			
	public String getHeader(String key){
		return this.header.get(key);
	}
	
	public Collection<String> getHeadersKeys(){
		return this.header.keySet();
	}

	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}	
}
