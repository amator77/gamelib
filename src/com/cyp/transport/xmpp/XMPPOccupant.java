package com.cyp.transport.xmpp;

import org.jivesoftware.smackx.muc.Occupant;

import com.cyp.transport.Contact;
import com.cyp.transport.Presence;

public class XMPPOccupant implements Contact {
	
	private Occupant occupant;
	
	private byte[] avatar;
	
	private static final Presence presence = new Presence() {
		
		public String getStatus() {			
			return "available";
		}
		
		public MODE getMode() {
			return MODE.ONLINE;
		}
		
		public String getContactId() {
			return "";
		}
	};
	
	public XMPPOccupant(Occupant occupant){
		this.occupant = occupant;
	}
	
	public String getId() {
		return occupant.getJid();
	}

	public String getName() {
		return occupant.getNick();
	}

	public Presence getPresence() {
		return presence;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public boolean isCompatible() {
		return true;
	}
}
