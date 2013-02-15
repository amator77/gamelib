package com.cyp.transport;

import java.util.List;


public interface RoomsManager {
	
	public void initialize();
	
	public List<? extends Room> listRooms();
		
}
