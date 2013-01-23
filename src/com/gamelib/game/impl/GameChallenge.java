package com.gamelib.game.impl;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

import com.gamelib.game.IChallenge;

public class GameChallenge implements IChallenge {

	private String remoteId;

	private String localId;

	private long time;

	private boolean received;
	
	public GameChallenge(String localId, String remoteId) {
		this(localId, remoteId, Calendar.getInstance(TimeZone
				.getTimeZone("UTC")).getTimeInMillis(), false);
	}

	public GameChallenge(String localId, String remoteId, long time) {
		this(localId, remoteId, time, true);
	}

	public GameChallenge(String localId, String remoteId, long time,
			boolean received) {
		this.localId = localId;
		this.remoteId = remoteId;
		this.time = time;
		this.received = received;
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

	@Override
	public boolean isReceived() {
		return this.received;
	}

	@Override
	public String toString() {
		return "GameChallenge [remoteId=" + remoteId + ", localId=" + localId
				+ ", time=" + time + ", received=" + received + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localId == null) ? 0 : localId.hashCode());
		result = prime * result + (received ? 1231 : 1237);
		result = prime * result
				+ ((remoteId == null) ? 0 : remoteId.hashCode());
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
		if (remoteId == null) {
			if (other.remoteId != null)
				return false;
		} else if (!remoteId.equals(other.remoteId))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
}
