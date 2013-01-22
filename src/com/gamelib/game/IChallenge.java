package com.gamelib.game;

import java.util.Date;
import java.util.Map;

import com.gamelib.transport.Contact;

public interface IChallenge {
	
	public Contact getRemoteContact();
	
	public Contact getLocalContact();
	
	public Date getSendTime();
	
	public Map<String, String> getDetails();
}
