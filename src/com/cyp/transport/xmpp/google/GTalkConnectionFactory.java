package com.cyp.transport.xmpp.google;

public class GTalkConnectionFactory {
	
	public static GTalkConnection createMD5Connection(){
		return new GTalkConnection(GTalkConfigurationManager.createMD5Configuration());
	}
	
	public static GTalkConnection createOpenAuthConnection(){
		return new GTalkConnection(GTalkConfigurationManager.createOauth2Configuration());
	}
}
