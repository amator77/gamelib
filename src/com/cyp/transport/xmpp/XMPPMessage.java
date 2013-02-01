package com.cyp.transport.xmpp;

import java.util.Collection;

import com.cyp.transport.Message;

public class XMPPMessage implements Message {

	private org.jivesoftware.smack.packet.Message xmppPachet;

	public XMPPMessage(org.jivesoftware.smack.packet.Message xmppPachet) {
		this.xmppPachet = xmppPachet;
	}

	public String getHeader(String key) {
		return this.xmppPachet.getProperty(key) != null ? this.xmppPachet
				.getProperty(key).toString() : null;
	}

	public String getBody() {
		return this.xmppPachet.getBody();
	}

	public String getFrom() {
		return this.xmppPachet.getFrom();
	}

	public String getTo() {
		return this.xmppPachet.getTo();
	}

	public Collection<String> getHeadersKeys() {
		return this.xmppPachet.getPropertyNames();
	}

	public void setHeader(String key, String value) {
		xmppPachet.setProperty(key, value);		
	}
}
