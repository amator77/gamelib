package com.cyp.transport;

import java.util.List;


public interface RoomListener {
	
	public void contactList(List<? extends Contact> list);
	
	public void contactJoined(Contact contact);
	
	public void contactLeaved(Contact contact);
	
	public void chatMessageReceived(String jid,String message);	
}
