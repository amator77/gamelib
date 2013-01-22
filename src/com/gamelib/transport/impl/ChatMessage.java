package com.gamelib.transport.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gamelib.transport.Message;

public class ChatMessage implements Message {
	
	private String from;
	
	private String to;
	
	private String body;
	
	private Map<String, String> header;
	
	public ChatMessage(String from, String to,String body){
		this.header = new HashMap<String, String>();
		this.from = from;
		this.to = to;
		this.body = body;
	}
	
	public ChatMessage(String to,String body){
		this(null,to,body);
	}
	
	public ChatMessage(){
		this(null,null,null);
	}
	
	@Override
	public String getFrom() {
		return this.from;
	}

	@Override
	public String getTo() {
		return this.to;
	}

	@Override
	public String getBody() {
		return this.body;
	}

	@Override
	public String getHeader(String key) {
		return this.header.get(key);
	}

	@Override
	public Collection<String> getHeadersKeys() {
		return this.header.keySet();
	}
}
