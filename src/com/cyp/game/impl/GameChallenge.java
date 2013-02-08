package com.cyp.game.impl;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import com.cyp.game.IChallenge;
import com.cyp.transport.Contact;

public class GameChallenge implements IChallenge {

	private Contact remoteContact;

	private String localId;

	private long time;

	private boolean received;
	
	public GameChallenge(String localId, Contact remoteContact) {
		this(localId, remoteContact, Calendar.getInstance(TimeZone
				.getTimeZone("UTC")).getTimeInMillis(), false);
	}

	public GameChallenge(String localId, Contact remoteContact, long time) {
		this(localId, remoteContact, time, true);
	}

	public GameChallenge(String localId, Contact remoteContact , long time,
			boolean received) {
		this.localId = localId;
		this.remoteContact = remoteContact;
		this.time = time;
		this.received = received;
	}

	public Contact getRemoteContact() {
		return this.remoteContact;
	}

	public String getLocalId() {
		return this.localId;
	}

	public long getTime() {
		return this.time;
	}

	public Map<String, String> getDetails() {
		return null;
	}

	public boolean isReceived() {
		return this.received;
	}

	@Override
	public String toString() {
		return "GameChallenge [remoteId=" + remoteContact + ", localId=" + localId
				+ ", time=" + time + ", received=" + received + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localId == null) ? 0 : localId.hashCode());
		result = prime * result + (received ? 1231 : 1237);
		result = prime * result
				+ ((remoteContact == null) ? 0 : remoteContact.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameChallenge other = (GameChallenge) obj;
		if (localId == null) {
			if (other.localId != null)
				return false;
		} else if (!localId.equals(other.localId))
			return false;
		if (received != other.received)
			return false;
		if (remoteContact == null) {
			if (other.remoteContact != null)
				return false;
		} else if (!remoteContact.equals(other.remoteContact))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
}
