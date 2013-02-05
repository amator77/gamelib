package com.cyp.transport;

public interface RosterListener {
	
	public void presenceChanged(Presence presence);
	
	public void contactUpdated(Contact contact);
	
	public void contactDisconected(Contact contact);
}
