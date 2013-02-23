package com.cyp.transport.xmpp;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.cyp.transport.RoomsManager;

public class XMPPRoomsManager implements RoomsManager {

	private XMPPConnection xmppConnection;

	private List<XMPPRoom> rooms;

	public XMPPRoomsManager(XMPPConnection xmppConnection) {
		this.xmppConnection = xmppConnection;
		this.rooms = new ArrayList<XMPPRoom>();		
	}

	public void initialize() {

//		try {
//			HttpClientResponse response = HttpClient.getInstance().readEntity(
//					Application.getContext().getGameBaseURL() + "/rooms", "");
//
//			JSONParser parser = new JSONParser();
//			Object obj = parser.parse(response.getBody());
//			JSONArray jsonArray = (JSONArray) obj;
//
//			for (int i = 0; i < jsonArray.size(); i++) {
//				JSONArray jsonArray2 = (JSONArray) jsonArray.get(i);
//				System.out.println(jsonArray2.get(1).toString());
//				XMPPRoom xmppRoom = new XMPPRoom(new MultiUserChat(
//						xmppConnection, jsonArray2.get(1).toString()));
//				rooms.add(xmppRoom);
//			}
//
//			Application.getContext().getLogger()
//					.debug("XMPPRoomsManager", "Rooms :" + rooms.toString());
//		} catch (Exception e) {
//			Application.getContext().getLogger()
//			.error("XMPPRoomsManager", "Error on rading rooms!",e);
//		}
		
		XMPPRoom room = new XMPPRoom(new MultiUserChat(xmppConnection, "cypmainroom@conference.jabber.org"));
		this.rooms.add(room);
	}

	public List<XMPPRoom> listRooms() {
		return this.rooms;
	}
}
