package com.cyp.transport.xmpp;

import com.cyp.application.Context.PLATFORM;
import com.cyp.transport.Contact;
import com.cyp.transport.Presence;
import com.cyp.transport.RosterListener;
import com.cyp.transport.Util;

public class XMPPContact implements Contact {

	private String id;

	private String name;

	private String resource;

	private XMPPPresence presense;
	
	private byte[] avatar;
	
	private boolean compatible;
	
	private XMPPRoster roster;
	
	public XMPPContact( XMPPRoster roster , String id, String name, XMPPPresence presense) {
		this.roster = roster;
		this.id = id;
		this.name = name;
		this.presense = presense;
	}

	public XMPPContact(XMPPRoster roster , String id, String name) {
		this( roster , id, name, new XMPPPresence(null));
	}

	public XMPPContact(XMPPRoster roster , String id) {
		this( roster, id, null, new XMPPPresence(null));
	}

	public Presence getPresence() {
		return this.presense;
	}

	public void setPresense(XMPPPresence presense) {
		this.presense = presense;
	}

	public String getName() {
		return name;
	}

	public void setResource(String resource) {
		this.resource = resource;
		String[] parts = this.id.split("/");
		this.id = parts[0] + ( (resource != null && resource.trim().length() > 0 ) ? ("/"+resource) : ""  );
	}

	public String getId() {
		return this.id;
	}

	public PLATFORM getClientPlatform() {
		return Util.getPlatform(this.resource);
	}

	public String getClientVersion() {
		return Util.getClientVersion(this.resource);
	}

	public String getClientType() {
		return Util.getClientType(this.resource);
	}
			
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
		
		for( RosterListener listener :  this.roster.getListeners()){
			listener.contactUpdated(this);
		}		
	}

	public byte[] getAvatar() {	
		return this.avatar;
	}

	public boolean isCompatible() {
		return compatible;
	}

	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}

	public String getResource() {
		return resource;
	}
}