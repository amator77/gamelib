package com.cyp.transport.xmpp.google;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.Roster.SubscriptionMode;
import org.jivesoftware.smack.SASLAuthentication;
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

import com.cyp.application.Application;
import com.cyp.application.Context.PLATFORM;
import com.cyp.transport.xmpp.PingExtension;

public class XMPPConfigurationManager {
	
	private static final String GTALK_HOST = "talk.google.com";

	private static final int GTALK_PORT = 5222;
	
	private static final String GTALK_SERVICE = "gtalk";
	
	public static ConnectionConfiguration createMD5Configuration(){
		ConnectionConfiguration configuration = new ConnectionConfiguration(GTALK_HOST, GTALK_PORT,
				GTALK_SERVICE, ProxyInfo.forNoProxy());
		configuration.setSecurityMode(SecurityMode.enabled);
		configuration.setDebuggerEnabled(true);
		configuration.setRosterLoadedAtLogin(true);
		configuration.setSendPresence(true);
		Roster.setDefaultSubscriptionMode(SubscriptionMode.manual);

		if (Application.getContext().getPlatform() == PLATFORM.MOBILE_ANDROID) {
			configuration.setTruststoreType("BKS");
			configuration.setTruststorePath(getCacertsPath());
			configurePM(ProviderManager.getInstance());
		}
		
		return configuration;
	}
	
	public static ConnectionConfiguration createOauth2Configuration(){
		ConnectionConfiguration configuration = new ConnectionConfiguration(GTALK_HOST, GTALK_PORT,
				GTALK_SERVICE, ProxyInfo.forNoProxy());
		
		SASLAuthentication.registerSASLMechanism("X-OAUTH2", GoogleConnectSASLMechanism.class);
		SASLAuthentication.supportSASLMechanism("X-OAUTH2", 0);
		configuration.setSASLAuthenticationEnabled(true);
		configuration.setSecurityMode(SecurityMode.enabled);
		configuration.setReconnectionAllowed(true);
		
		configuration.setDebuggerEnabled(true);
		configuration.setRosterLoadedAtLogin(true);
		configuration.setSendPresence(true);
		Roster.setDefaultSubscriptionMode(SubscriptionMode.manual);

		if (Application.getContext().getPlatform() == PLATFORM.MOBILE_ANDROID) {
			configuration.setTruststoreType("BKS");
			configuration.setTruststorePath(getCacertsPath());
			configurePM(ProviderManager.getInstance());
		}
		
		return configuration;
	}
	
	private static void configurePM(ProviderManager pm) {
	
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
