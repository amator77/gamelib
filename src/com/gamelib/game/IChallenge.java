package com.gamelib.game;

import java.util.Map;

public interface IChallenge {
	
	public static final String HEADER_ID_KEY = "id";
	
	public static final String HEADER_TIME_KEY = "time";
	
	/**
	 * The remote account id
	 * @return
	 */
	public String getRemoteId();
	
	/**
	 * The local account id
	 * @return
	 */
	public String getLocalId();
	
	/**
	 * UTC time in miliseconds
	 * @return
	 */
	public long getTime();
	
	/**
	 * Challenge extra details
	 * @return
	 */
	public Map<String, String> getDetails();
	
	/**
	 * Return true if this challenge is received over transport
	 * @return
	 */
	public boolean isReceived();
}
