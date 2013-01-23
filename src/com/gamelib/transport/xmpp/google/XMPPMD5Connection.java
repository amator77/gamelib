package com.gamelib.transport.xmpp.google;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.proxy.ProxyInfo;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.provider.DelayInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.pubsub.provider.EventProvider;
import org.jivesoftware.smackx.pubsub.provider.ItemProvider;
import org.jivesoftware.smackx.pubsub.provider.ItemsProvider;
import org.jivesoftware.smackx.pubsub.provider.PubSubProvider;

import com.gamelib.application.Application;
import com.gamelib.application.Context.PLATFORM;
import com.gamelib.application.Logger;
import com.gamelib.transport.Connection;
import com.gamelib.transport.ConnectionListener;
import com.gamelib.transport.Message;
import com.gamelib.transport.Presence.MODE;
import com.gamelib.transport.RosterListener;
import com.gamelib.transport.Util;
import com.gamelib.transport.exceptions.LoginException;
import com.gamelib.transport.xmpp.PingExtension;
import com.gamelib.transport.xmpp.XMPPContact;
import com.gamelib.transport.xmpp.XMPPMessage;
import com.gamelib.transport.xmpp.XMPPPresence;
import com.gamelib.transport.xmpp.XMPPRoster;

/**
 * XMPP Google Connection using MD-DIGEST for auth
 * 
 * @author leo
 * 
 */
public class XMPPMD5Connection implements Connection,
		org.jivesoftware.smack.ConnectionListener {

	private static final String TAG = "XMPPConnectionManager";

	private static final String GTALK_HOST = "talk.google.com";

	private static final int GTALK_PORT = 5222;

	private static final String GTALK_SERVICE = "gtalk";

	private XMPPConnection xmppConnection;

	private List<ConnectionListener> listeners;

	private String user;

	private XMPPRoster roster;

	private ConnectionConfiguration configuration;

	private static final Logger Log = Application.getContext().getLogger();

	public XMPPMD5Connection() {
		this.listeners = new ArrayList<ConnectionListener>();
		configuration = new ConnectionConfiguration(GTALK_HOST, GTALK_PORT,
				GTALK_SERVICE, ProxyInfo.forNoProxy());
		configuration.setSecurityMode(SecurityMode.enabled);
		configuration.setDebuggerEnabled(true);
		configuration.setRosterLoadedAtLogin(true);
		configuration.setSendPresence(true);
		Roster.setDefaultSubscriptionMode(SubscriptionMode.manual);

		if (Application.getContext().getPlatform() == PLATFORM.MOBILE_ANDROID) {
			configuration.setTruststoreType("BKS");
			configuration.setTruststorePath(getCacertsPath());
			this.configurePM(ProviderManager.getInstance());
		}
	}

	@Override
	public boolean isConnected() {
		return this.xmppConnection != null && this.xmppConnection.isConnected()
				&& this.xmppConnection.isAuthenticated();
	}

	@Override
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
						new PacketTypeFilter(PingExtension.class));
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
			this.user = xmppConnection.getUser();
			this.roster = new XMPPRoster(xmppConnection.getRoster());
			Log.debug(TAG, "Success on login as :" + this.user);
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
			this.user = null;
		}
	}

	@Override
	public com.gamelib.transport.Roster getRoster() {

		if (this.roster == null || this.roster.getContacts().size() == 0) {
			this.roster = new XMPPRoster(xmppConnection.getRoster());
		}

		return this.roster;
	}

	@Override
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

	@Override
	public void sendPresence(MODE status) throws IOException {
		if (this.xmppConnection.isConnected()
				&& this.xmppConnection.isAuthenticated()) {
			this.xmppConnection.sendPacket(getPresence(status));
		}
	}

	@Override
	public void addConnectionListener(ConnectionListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	@Override
	public void connectionClosed() {
		for (ConnectionListener listener : listeners) {
			listener.onDisconect(this);
		}
	}

	@Override
	public void connectionClosedOnError(Exception arg0) {

		for (ConnectionListener listener : listeners) {
			listener.onDisconect(this);
		}
	}

	@Override
	public void reconnectingIn(int arg0) {
		Log.debug(TAG, "reconnectingIn :" + arg0);
	}

	@Override
	public void reconnectionFailed(Exception arg0) {
		Log.error(TAG, "reconnectionFailed", arg0);
	}

	@Override
	public void reconnectionSuccessful() {
		Log.debug(TAG, "reconnectionSuccessful");
	}

	public XMPPConnection getXmppConnection() {
		return xmppConnection;
	}

	private class MessageListener implements PacketListener {
		@Override
		public void processPacket(Packet packet) {
			if (packet instanceof org.jivesoftware.smack.packet.Message) {
				Log.debug(TAG, "Message received :" + packet.toXML());

				for (ConnectionListener listener : listeners) {
					listener.messageReceived(
							XMPPMD5Connection.this,
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

			XMPPPresence xmppPresence = new XMPPPresence(presence);

			if (resource != null) {

				for (XMPPContact contact : roster.getContacts()) {
					if (presence.getFrom().startsWith(contact.getId())) {
						contact.setPresense(xmppPresence);
						contact.updateResource(resource);
					}
				}
			}

			for (RosterListener listener : roster.getListeners()) {
				listener.presenceChanged(xmppPresence);
			}
		}
	}

	private class PingListener implements PacketListener {

		@Override
		public void processPacket(Packet packet) {
			if (!(packet instanceof PingExtension))
				return;
			PingExtension p = (PingExtension) packet;
			if (p.getType() == IQ.Type.GET) {
				PingExtension pong = new PingExtension();
				pong.setType(IQ.Type.RESULT);
				pong.setTo(p.getFrom());
				pong.setPacketID(p.getPacketID());
				Log.debug(TAG, "Send pong response");
				xmppConnection.sendPacket(pong);
			}
		}
	}

	private void configurePM(ProviderManager pm) {
		Log.debug(TAG, "configure ProviderManager");

		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
				new DiscoverItemsProvider());
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
				new DiscoverInfoProvider());
		pm.addExtensionProvider("delay", "urn:xmpp:delay",
				new DelayInfoProvider());
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
				new DiscoverItemsProvider());
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
				new DiscoverInfoProvider());
		ChatStateExtension.Provider chatState = new ChatStateExtension.Provider();
		pm.addExtensionProvider("active",
				"http://jabber.org/protocol/chatstates", chatState);
		pm.addExtensionProvider("composing",
				"http://jabber.org/protocol/chatstates", chatState);
		pm.addExtensionProvider("paused",
				"http://jabber.org/protocol/chatstates", chatState);
		pm.addExtensionProvider("inactive",
				"http://jabber.org/protocol/chatstates", chatState);
		pm.addExtensionProvider("gone",
				"http://jabber.org/protocol/chatstates", chatState);
		pm.addIQProvider("pubsub", "http://jabber.org/protocol/pubsub",
				new PubSubProvider());
		pm.addExtensionProvider("items", "http://jabber.org/protocol/pubsub",
				new ItemsProvider());
		pm.addExtensionProvider("items", "http://jabber.org/protocol/pubsub",
				new ItemsProvider());
		pm.addExtensionProvider("item", "http://jabber.org/protocol/pubsub",
				new ItemProvider());
		pm.addExtensionProvider("items",
				"http://jabber.org/protocol/pubsub#event", new ItemsProvider());
		pm.addExtensionProvider("item",
				"http://jabber.org/protocol/pubsub#event", new ItemProvider());
		pm.addExtensionProvider("event",
				"http://jabber.org/protocol/pubsub#event", new EventProvider());
		pm.addIQProvider(PingExtension.ELEMENT, PingExtension.NAMESPACE,
				PingExtension.class);
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

	private static String getCacertsPath() {
		try {
			InputStream fis = Application.getContext()
					.getResourceAsInputStream("cacerts.bks");
			File f = File.createTempFile("cacerts", "bks");
			byte[] buffer = new byte[1024];
			FileOutputStream fos = new FileOutputStream(f);
			int read = 0;

			while ((read = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, read);
			}

			fis.close();
			fos.close();
			return f.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
