package com.cyp.transport;



public interface Room {
	
	public boolean join(String nickname);
	
	public void leave();
	
	public String getName();
	
	public String getMasterId();
	
	public void addRoomListener(RoomListener listener);
	
	public void removeRoomListener(RoomListener listener);
	
	public void sendChatMessage(String mbody);
}