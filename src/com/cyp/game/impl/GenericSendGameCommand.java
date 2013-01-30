package com.cyp.game.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cyp.game.IGameCommand;

public class GenericSendGameCommand implements IGameCommand {

	private int commandId;

	private String from;

	private String to;

	private Map<String, String> header;

	private String body;

	public GenericSendGameCommand(int commandId) {
		this.commandId = commandId;
		this.header = new HashMap<String, String>();
		this.body = "";
	}

	@Override
	public int getId() {
		return this.commandId;
	}

	public String getFrom() {
		return this.from;
	}

	public String getTo() {
		return this.to;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getHeader(String key) {
		return this.header.get(key);
	}

	public Collection<String> getHeadersKeys() {
		return this.header.keySet();
	}

	@Override
	public String getBody() {
		return this.body;
	}	

	public void setBody(String body) {
		this.body = body;
	}	
	
	@Override
	public void setHeader(String key, String value) {
		this.header.put(key, value);
	}
	
	@Override
	public String toString() {
		return "GenericSendGameCommand [commandId=" + commandId + ", from="
				+ from + ", to=" + to + ", header=" + header + ", body=" + body
				+ "]";
	}
}
