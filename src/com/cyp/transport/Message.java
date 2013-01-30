package com.cyp.transport;

import java.util.Collection;


public interface Message {
			
	public String getFrom();
	
	public String getTo();
			
	public String getBody();
	
	public String getHeader(String key);
	
	public void setHeader(String key,String value);
	
	public Collection<String> getHeadersKeys();
}
