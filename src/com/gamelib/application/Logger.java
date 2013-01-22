package com.gamelib.application;

public interface Logger {
	
	public void debug(String component,String message);
	
	public void info(String component,String message);
	
	public void error(String component,String message,Throwable ex);
}
