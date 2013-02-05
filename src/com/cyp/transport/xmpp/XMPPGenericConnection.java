package com.cyp.transport.xmpp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.ServiceDiscoveryManager;

import com.cyp.application.Application;
import com.cyp.application.Logger;
import com.cyp.transport.Connection;
import com.cyp.transport.ConnectionListener;
import com.cyp.transport.Message;
import com.cyp.transport.Presence.MODE;
import com.cyp.transport.RosterListener;
import com.cyp.transport.Util;
import com.cyp.transport.exceptions.LoginException;

public class XMPPGenericConnection implements Connection,
		org.jivesoftware.smack.ConnectionListener {

	private static final String TAG = "XMPPConnectionManager";

	private XMPPConnection xmppConnection;
	
	private ServiceDiscoveryManager sdm;
	
	private ConnectionConfiguration configuration;

	private List<ConnectionListener> listeners;

	private String accountId;

	private XMPPRoster roster;

	private static final Logger Log = Application.getContext().getLogger();

	public XMPPGenericConnection(ConnectionConfiguration configuration) {
		this.listeners = new ArrayList<ConnectionListener>();
		this.configuration = configuration;
	}
	
	public String getAccountId() {
		return this.accountId;
	}
	
	public boolean isConnected() {
		return this.xmppConnection != null && this.xmppConnection.isConnected()
				&& this.xmppConnection.isAuthenticated();
	}

	public void login(String id, String credentials) throws IOException,
			LoginException {

		if (this.xmppConnection == null) {
			xmppConnection = new XMPPConnection(configuration);
		}

		if (xmppConnection.isAuthenticated()) {
			return;
		}

		if (!xmppConnection.isConnected()) {

			try {
				xmppConnection.connect();
				xmppConnection.addConnectionListener(this);
				xmppConnection.addPacketListener(new PingListener(),
						new PacketTypeFilter(XMPPPingExtension.class));
				xmppConnection.addPacketListener(new PresenceListener(),
						new PacketTypeFilter(Presence.class));
				xmppConnection.addPacketListener(new MessageListener(),
						new PacketTypeFilter(
								org.jivesoftware.smack.packet.Message.class));												
			} catch (XMPPException e) {
				Log.error(TAG, "Error on connection", e);
				throw new IOException("Error on coonecting!", e);
			}
		}

		try {
			xmppConnection
					.login(id, credentials, Util.getApplicationResource());			
			this.accountId = xmppConnection.getUser();
			this.roster = new XMPPRoster(xmppConnection.getRoster());
			Log.debug(TAG, "Success on login as :" + this.accountId);
			sdm = new ServiceDiscoveryManager(xmppConnection);
			sdm.addFeature("http://jabber.org/protocol/games/chess/v1");
		} catch (XMPPException e) {
			Log.error(TAG, "Error on login", e);
			throw new LoginException("Error on login!", e);
		}
	}

	public void logout() {
		if (this.xmppConnection != null && this.xmppConnection.isConnected()) {
			this.xmppConnection.disconnect();
			this.xmppConnection.removeConnectionListener(this);
			this.xmppConnection = null;
			this.accountId = null;
		}
	}

	public com.cyp.transport.Roster getRoster() {

		if (this.roster == null || this.roster.getContacts().size() == 0) {
			this.roster = new XMPPRoster(xmppConnection.getRoster());
		}

		return this.roster;
	}

	public void sendMessage(Message message) throws IOException {
		if (this.xmppConnection.isConnected()
				&& this.xmppConnection.isAuthenticated()) {
			org.jivesoftware.smack.packet.Message xmppMessage = new org.jivesoftware.smack.packet.Message();
			xmppMessage.setTo(message.getTo());
			xmppMessage.setBody(message.getBody());

			for (String key : message.getHeadersKeys()) {
				xmppMessage.setProperty(key, message.getHeader(key));
			}

			this.xmppConnection.sendPacket(xmppMessage);
		}
	}

	public void sendPresence(MODE status) throws IOException {
		if (this.xmppConnection.isConnected()
				&& this.xmppConnection.isAuthenticated()) {
			this.xmppConnection.sendPacket(getPresence(status));
		}
	}

	public void addConnectionListener(ConnectionListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void connectionClosed() {
		for (ConnectionListener listener : listeners) {
			listener.onDisconect(this);
		}
	}

	public void connectionClosedOnError(Exception arg0) {

		for (ConnectionListener listener : listeners) {
			listener.onDisconect(this);
		}
	}

	public void reconnectingIn(int arg0) {
		Log.debug(TAG, "reconnectingIn :" + arg0);
	}

	public void reconnectionFailed(Exception arg0) {
		Log.error(TAG, "reconnectionFailed", arg0);
	}

	public void reconnectionSuccessful() {
		Log.debug(TAG, "reconnectionSuccessful");
	}

	public XMPPConnection getXmppConnection() {
		return xmppConnection;
	}

	private class MessageListener implements PacketListener {

		public void processPacket(Packet packet) {
			if (packet instanceof org.jivesoftware.smack.packet.Message) {
				Log.debug(TAG, "Message received :" + packet.toXML());

				for (ConnectionListener listener : listeners) {
					listener.messageReceived(
							XMPPGenericConnection.this,
							new XMPPMessage(
									(org.jivesoftware.smack.packet.Message) packet));
				}
			}
		}
	}

	private class PresenceListener implements PacketListener {
		public void processPacket(Packet packet) {
			if (!(packet instanceof Presence))
				return;
			Log.debug(TAG, "Presence received :" + packet.toXML());
			Presence presence = (Presence) packet;
			String resource = Util.getResourceFromId(presence.getFrom());
			String contact = Util.getContactFromId(presence.getFrom());
			Log.debug(TAG, "Resource :" + resource + " Contact :" + contact);
			XMPPPresence xmppPresence = new XMPPPresence(presence);

			for (XMPPContact xmppContact : roster.getContacts()) {
				if (xmppContact.getId().startsWith(contact)) {
					xmppContact.setPresense(xmppPresence);
					xmppContact
							.updateResource(resource != null ? resource : "");
					
					if( !xmppContact.isCompatible() ){
						try {
							xmppContact.setCompatible(sdm.discoverInfo(packet.getFrom()).containsFeature("http://jabber.org/protocol/games/chess/v1"));							
						} catch (XMPPException e) {
							e.printStackTrace();
						}
					}
				}
			}

			for (RosterListener listener : roster.getListeners()) {
				listener.presenceChanged(xmppPresence);
			}			
		}
	}

	private class PingListener implements PacketListener {

		public void processPacket(Packet packet) {
			if (!(packet instanceof XMPPPingExtension))
				return;
			XMPPPingExtension p = (XMPPPingExtension) packet;
			if (p.getType() == IQ.Type.GET) {
				XMPPPingExtension pong = new XMPPPingExtension();
				pong.setType(IQ.Type.RESULT);
				pong.setTo(p.getFrom());
				pong.setPacketID(p.getPacketID());
				Log.debug(TAG, "Send pong response");
				xmppConnection.sendPacket(pong);
			}
		}
	}

	public Presence getPresence(MODE status) {
		Presence p = null;
		switch (status) {
		case ONLINE:
			p = new Presence(Type.available);
			p.setMode(Mode.available);
			break;
		case OFFLINE:
			p = new Presence(Type.unavailable);
			break;
		case AWAY:
			p = new Presence(Type.available);
			p.setMode(Mode.away);
			break;
		case BUSY:
			p = new Presence(Type.available);
			p.setMode(Mode.dnd);
			break;

		default:
			p = new Presence(Type.available);
			break;
		}

		return p;
	}
}
