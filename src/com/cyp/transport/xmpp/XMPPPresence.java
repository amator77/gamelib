package com.cyp.transport.xmpp;

import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;

import com.cyp.transport.Presence;

public class XMPPPresence implements Presence {

	private org.jivesoftware.smack.packet.Presence xmppPresence;

	public XMPPPresence(org.jivesoftware.smack.packet.Presence xmppPresence) {
		this.xmppPresence = xmppPresence;
	}

	public String getContactId() {
		if( xmppPresence != null ){
			return xmppPresence.getFrom();
		}
		else{
			return null;
		}
	}

	public MODE getMode() {
		if( xmppPresence != null ){
			return this.convertMode(xmppPresence.getType(), xmppPresence.getMode());
		}
		else{
			return MODE.OFFLINE;
		}
	}
	
	public String getStatus() {
		if( this.xmppPresence != null ){
			return this.xmppPresence.getStatus();
		}
		else{
			return "";
		}
	}

	private MODE convertMode(Type type, Mode mode) {
		if (mode != null) {

			switch (mode) {
			case available:
				return MODE.ONLINE;
			case away:
				return MODE.AWAY;
			case dnd:
				return MODE.BUSY;
			case chat:
				return MODE.ONLINE;
			case xa:
				return MODE.AWAY;

			default:
				return MODE.OFFLINE;
			}
		} else {
			if (type != null) {
				switch (type) {
				case available:
					return MODE.ONLINE;
				default:
					return MODE.OFFLINE;
				}
			} else {
				return MODE.OFFLINE;
			}
		}
	}

	@Override
	public String toString() {
		return xmppPresence != null ? xmppPresence.toString() : Type.unavailable.toString();
	}
}
