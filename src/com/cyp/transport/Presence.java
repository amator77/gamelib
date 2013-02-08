package com.cyp.transport;

public interface Presence {

	public enum MODE {
		ONLINE, BUSY, AWAY, OFFLINE
	}

	public String getContactId();

	public MODE getMode();

	public String getStatus();
}
