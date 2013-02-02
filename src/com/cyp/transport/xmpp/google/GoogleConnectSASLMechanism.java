package com.cyp.transport.xmpp.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.sasl.Sasl;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;

public class GoogleConnectSASLMechanism extends SASLMechanism {
	public static final String NAME = "X-OAUTH2";
	private String username = "";
	private String accessToken = "";
	
	public GoogleConnectSASLMechanism(SASLAuthentication saslAuthentication) {
		super(saslAuthentication);
	}

	@Override
	protected String getName() {
		return NAME;
	}

	static void enable() {
	}
	
	@Override
	public void authenticate(String username, String host, String accessToken)
			throws IOException, XMPPException {
		
		this.accessToken = accessToken;
		this.hostname = host;

		authenticate();
	}
	
	protected void authenticate() throws IOException, XMPPException {
	    final StringBuilder stanza = new StringBuilder();
	    byte response[] = null;

	    stanza.append("<auth xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"" +
	            "mechanism=\"X-OAUTH2\"" +
	            "auth:service=\"oauth2\"" +
	            "xmlns:auth= \"http://www.google.com/talk/protocol/auth\">");

	    String composedResponse =  "\0" + username + "\0" + accessToken;
	    response = composedResponse.getBytes("UTF-8");
	    String authenticationText = "";
	    if (response != null) {
	        authenticationText = Base64.encodeBytes(response, Base64.DONT_BREAK_LINES);
	    }

	    stanza.append(authenticationText);
	    stanza.append("</auth>");

	    // Send the authentication to the server
	    Packet p=new Packet() {
	        @Override
	        public String toXML() {
	            return stanza.toString();
	        }
	    };
	    getSASLAuthentication().send(p);
	}

	public class Auth2Mechanism extends Packet {
		String stanza;

		public Auth2Mechanism(String txt) {
			stanza = txt;
		}

		public String toXML() {
			return stanza;
		}
	}

	/**
	 * Initiating SASL authentication by select a mechanism.
	 */
	public class AuthMechanism extends Packet {
		final private String name;
		final private String authenticationText;

		public AuthMechanism(String name, String authenticationText) {
			if (name == null) {
				throw new NullPointerException(
						"SASL mechanism name shouldn't be null.");
			}
			this.name = name;
			this.authenticationText = authenticationText;
		}

		public String toXML() {
			StringBuilder stanza = new StringBuilder();
			stanza.append("<auth mechanism=\"").append(name);
			stanza.append("\" xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">");
			if (authenticationText != null
					&& authenticationText.trim().length() > 0) {
				stanza.append(authenticationText);
			}
			stanza.append("</auth>");
			return stanza.toString();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
