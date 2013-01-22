package com.gamelib.game;

import java.util.Map;

public interface IChallenge {
	
	public String getRemoteId();
	
	public String getLocalId();
	
	public long getTime();
	
	public Map<String, String> getDetails();
}
