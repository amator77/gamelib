package com.cyp.server;

import java.util.List;

import com.cyp.transport.Contact;
import com.cyp.transport.Room;

public interface RoomsManagerListener {
	
	public void roomsReceived(List<Room> rooms);
	
	public void joinSuccess( Room room ,  List<Contact> roomContacts);
	
	public void joinFailure( Room room );
}
