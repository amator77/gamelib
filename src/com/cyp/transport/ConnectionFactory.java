package com.cyp.transport;

import com.cyp.transport.xmpp.XMPPGenericConnection;
import com.cyp.transport.xmpp.configuration.ConfigurationManager;

public class ConnectionFactory {

	private static final ConnectionFactory factory = new ConnectionFactory();

	public enum CONNECTION_TYPE {
		XMPP_GTALK_MD5, XMPP_GTALK_OAUTH2, XMPP_FACEBOOK_OAUTH2,XMPP_FACEBOOK_MD5
	}
	
	public static ConnectionFactory getFactory(){
		return ConnectionFactory.factory;
	}
	
	public Connection createConnection(CONNECTION_TYPE type) {
		Connection connection = null;

		switch (type) {
		case XMPP_GTALK_OAUTH2:
			return new XMPPGenericConnection(
					ConfigurationManager.createGTalkOauth2Configuration());
		case XMPP_GTALK_MD5:
			return new XMPPGenericConnection(
					ConfigurationManager.createGTalkMD5Configuration());
		case XMPP_FACEBOOK_OAUTH2:
			return new XMPPGenericConnection(
					ConfigurationManager.createFacebookOauth2Configuration());
		case XMPP_FACEBOOK_MD5:
			return new XMPPGenericConnection(
					ConfigurationManager.createFacebookkMD5Configuration());
		default:
			break;
		}

		return connection;
	}
}
