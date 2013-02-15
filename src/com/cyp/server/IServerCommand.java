package com.cyp.server;

import com.cyp.transport.Message;

public interface IServerCommand  extends Message {
			
	public static final String SERVER_COMMAND_HEADER_KEY = "scmd";
	
	public static final String JOIN_ROOM_RESPONSE_KEY = "jrrk";
	
	public static final String JOIN_ROOM_RESPONSE_JIDS_KEY = "jids";
	
	public static final String JOIN_ROOM_JID_KEY = "jid";
	
	public static final String ROOM_KEY = "room";
	
	public static final int UNKNOWN_COMMAND_ID = -1;
	
	public static final int JOIN_ROOM_COMMAND_ID = 0;	
			
	public static final int LEAVE_ROOM_COMMAND_ID = 1;
			
	public static final int LIST_ROOMS_COMMAND_ID = 2;
	
	public static final int LIST_CONTACTS_ROOM_COMMAND_ID = 3;		
	
	public static final int CONTACT_JOINED_ROOM_COMMAND_ID = 4;
	
	public static final int CONTACT_LEAVED_ROOM_COMMAND_ID = 5;
	
	public static final int PING_ROOM_CONTACT_COMMAND_ID = 6;
	
	public static final int PONG_ROOM_CONTACT_COMMAND_ID = 7;
	
	public static final int UPDATE_ROOM_CONTACT_COMMAND_ID = 8;
	
	public static final int ROOM_CONTACT_UPDATED_COMMAND_ID = 9;
	
	public static final int ROOM_NOT_AUTHORIZED_COMMAND_ID = 10;
	
	public static final int JOIN_ROOM_RESPONSE_COMMAND_ID = 11;
	
	public static final int JOIN_ROOM_RESPONSE_OK = 12;
	
	public static final int JOIN_ROOM_RESPONSE_FORBIDEN = 13;
	
	public static final int JOIN_ROOM_RESPONSE_ROOM_FULL = 14;
	
	public static final int ROOM_SYSTEM_MESSAGE_COMMAND_ID = 15;
	
	public int getId();	
}