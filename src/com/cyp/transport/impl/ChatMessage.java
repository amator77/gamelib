package com.cyp.transport.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cyp.transport.Message;

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
	
	
	public String getFrom() {
		return this.from;
	}

	
	public String getTo() {
		return this.to;
	}

	
	public String getBody() {
		return this.body;
	}

	
	public String getHeader(String key) {
		return this.header.get(key);
	}

	
	public Collection<String> getHeadersKeys() {
		return this.header.keySet();
	}

	
	public void setHeader(String key, String value) {
		this.header.put(key, value);		
	}
}
