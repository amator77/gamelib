package com.cyp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.cyp.application.Application;
import com.cyp.application.Logger;
import com.cyp.transport.Connection;
import com.cyp.transport.ConnectionListener;
import com.cyp.transport.Message;
import com.cyp.transport.Room;
import com.cyp.utils.HttpClient;
import com.cyp.utils.HttpClientResponse;

public class RoomsManagerA implements ConnectionListener {

	public boolean messageReceived(Connection source, Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onDisconect(Connection source) {
		// TODO Auto-generated method stub
		
	}

//	private static final String CHESSYOUP_URL = "http://api.chessyoup.com";
//
//	private Connection connection;
//
//	private List<RoomImpl> rooms;
//	
//	private static final Logger log = Application.getContext().getLogger();
//	
//	public RoomsManagerA(Connection connection) {
//		this.connection = connection;
//		this.connection.addConnectionListener(this);
//		this.rooms = new ArrayList<RoomImpl>();
//	}
//	
//	public boolean initialize() throws Exception {
//		HttpClientResponse response = HttpClient.getInstance().readEntity(
//				CHESSYOUP_URL + "/rooms", "");
//			
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(response.getBody());
//		JSONArray jsonArray = (JSONArray) obj;
//
//		for (int i = 0; i < jsonArray.size(); i++) {
//			JSONArray jsonArray2 = (JSONArray) jsonArray.get(i);
//			RoomImpl room = new RoomImpl(jsonArray2.get(0).toString(),
//					jsonArray2.get(1).toString());
//			rooms.add(room);
//		}				
//		
//		Application.getContext().getLogger().debug("RoomsManager", "Rooms :"+rooms.toString());
//		
//		return true;
//	}
//
//	public boolean messageReceived(Connection source, Message message) {
//		
//		String commandId = message
//				.getHeader(IServerCommand.SERVER_COMMAND_HEADER_KEY);
//
//		if (commandId != null) {
//
//			log.debug(this.getClass().getName(), "New game command recevied :"
//					+ commandId);
//
//			switch (Integer.parseInt(commandId)) {
//			case IServerCommand.JOIN_ROOM_RESPONSE_COMMAND_ID:
//				handleJoinResponseCommand(message.getHeader(IServerCommand.JOIN_ROOM_RESPONSE_KEY),message.getHeader(IServerCommand.ROOM_KEY),message.getBody());
//				return true;			
//			default:
//				log.debug(this.getClass().getName(), "Unknonw command:"
//						+ message.toString());
//				return false;
//			}
//		} else {
//			log.debug(this.getClass().getName(), "Message discarded :"
//					+ message.toString());
//		}
//
//		
//		return false;
//	}
//
//	private void handleJoinResponseCommand(String header, String header2,
//			String body) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void joinRoom(Room room) throws IOException {
//		ServerCommand scm = new ServerCommand(IServerCommand.JOIN_ROOM_COMMAND_ID);
//		scm.setHeader(IServerCommand.ROOM_KEY, room.getName());
//		scm.setTo(room.getMasterId());
//		scm.setFrom(this.connection.getAccountId());
//		this.connection.sendMessage(scm);
//	}
//
//	public void leaveRoom(Room room) throws IOException {
//		ServerCommand scm = new ServerCommand(IServerCommand.LEAVE_ROOM_COMMAND_ID);
//		scm.setHeader(IServerCommand.ROOM_KEY, room.getName());
//		scm.setTo(room.getMasterId());
//		scm.setFrom(this.connection.getAccountId());
//		this.connection.sendMessage(scm);
//	}
//
//	public List<RoomImpl> listRooms() {
//		return this.rooms;
//	}
//
//	public void onDisconect(Connection source) {
//
//	}
}
