package com.cyp.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ServerCommand implements IServerCommand {

	private int commandId;

	private String from;

	private String to;

	private Map<String, String> header;

	private String body;

	public ServerCommand(int commandId) {
		this.commandId = commandId;
		this.header = new HashMap<String, String>();
		this.body = "";
	}

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

	public String getBody() {
		return this.body;
	}	

	public void setBody(String body) {
		this.body = body;
	}	
	
	public void setHeader(String key, String value) {
		this.header.put(key, value);
	}
	
	@Override
	public String toString() {
		return "ServerCommand [commandId=" + commandId + ", from="
				+ from + ", to=" + to + ", header=" + header + ", body=" + body
				+ "]";
	}
}
