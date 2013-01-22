package com.gamelib.game.impl;

import java.util.Map;

import com.gamelib.game.IChallenge;

public class GameChallenge implements IChallenge{
	
	private String remoteId;
	
	private String localId;
	
	private long time;
	
	public GameChallenge(String localId,String remoteId){
		this.localId = localId;
		this.remoteId = remoteId;
		this.time = System.currentTimeMillis();
	}
	
	@Override
	public String getRemoteId() {
		return this.remoteId;
	}

	@Override
	public String getLocalId() {
		return this.localId;
	}

	@Override
	public long getTime() {
		return this.time;
	}

	@Override
	public Map<String, String> getDetails() {
		return null;
	}
}
