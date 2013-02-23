package com.cyp.transport.xmpp;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.cyp.application.Application;
import com.cyp.transport.Room;
import com.cyp.transport.RoomListener;

public class XMPPRoom implements Room {

	private MultiUserChat muc;

	private List<RoomListener> listeners;

	public XMPPRoom(MultiUserChat muc) {
		this.muc = muc;
		this.listeners = new ArrayList<RoomListener>();
		this.muc.addParticipantListener(new ParticipantsListener());
		this.muc.addMessageListener(new MessagesListener());		
	}

	public String getName() {
		return muc.getRoom();
	}

	public String getMasterId() {
		return muc.getNickname();
	}

	public void addRoomListener(RoomListener listener) {
		this.listeners.add(listener);
	}

	public void removeRoomListener(RoomListener listener) {
		this.listeners.remove(listener);
	}

	private class ParticipantsListener implements PacketListener {

		public void processPacket(Packet packet) {
			Application.getContext().getLogger()
					.debug("XMPPRoom", packet.toXML());

			if (packet instanceof Presence) {
				Presence presence = (Presence) packet;
				String username = getUserFomrJid(packet.getFrom());

				if (presence.getType() == Type.available) {
					System.out.println("user joined :" + username);
				} else if (presence.getType() == Type.unavailable) {
					System.out.println("user leaved :" + username);
				}
			}
		}

		private String getUserFomrJid(String from) {
			if (from.contains("/")) {
				String parts[] = from.split("//");
				return parts[1];
			} else
				return from;
		}
	}

	private class MessagesListener implements PacketListener {

		public void processPacket(Packet pachet) {
			if (pachet instanceof Message) {

				for (RoomListener listener : listeners) {
					listener.chatMessageReceived(pachet.getFrom(),
							((Message) pachet).getBody());
				}
			}
		}
	}

	public boolean join(String nickname) {

		if (!this.muc.isJoined()) {

			try {
				if (!this.muc.isJoined()) {
					this.muc.join(nickname, "cyppassword",
							new DiscussionHistory(), 5000);
					// Collection<Occupant> occupants =
					// this.muc.getParticipants();
					// List<XMPPOccupant> contacts = new
					// ArrayList<XMPPOccupant>();
					//
					// for (Occupant occupant : occupants) {
					// contacts.add(new XMPPOccupant(occupant));
					// }
					//
					// for (RoomListener listener : listeners) {
					// listener.contactList(contacts);
					// }
				}
				else{
					Application.getContext().getLogger().debug("XMPPRoom", "Allready joined!");
				}

			} catch (XMPPException e) {
				Application.getContext().getLogger()
						.error("XMPPRoom", "Error on joinning room", e);
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	public void leave() {
		this.muc.leave();
	}

	@Override
	public String toString() {
		return "XMPPRoom [muc=" + muc.getNickname() + ", listeners="
				+ listeners + "]";
	}

	public void sendChatMessage(String body) {
		try {
			this.muc.sendMessage(body);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
