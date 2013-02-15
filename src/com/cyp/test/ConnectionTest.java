package com.cyp.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cyp.accounts.Account;
import com.cyp.application.Application;
import com.cyp.application.Context;
import com.cyp.application.Logger;
import com.cyp.transport.Connection;
import com.cyp.transport.ConnectionFactory;
import com.cyp.transport.ConnectionFactory.CONNECTION_TYPE;
import com.cyp.transport.impl.ChatMessage;

public class ConnectionTest implements Context {	

	private DesktopLogger logger;
	
	private List<Account> accounts;
	
	public ConnectionTest(){
		this.logger = new DesktopLogger();		
	}
		
	public void initialize(Object contextData) {		
		this.accounts = new ArrayList<Account>();		
	}


	public InputStream getResourceAsInputStream(String resource) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
	}

	public Logger getLogger() {
		return this.logger;
	}

	public List<Account> listAccounts() {
		return this.accounts;
	}

	public String getApplicationName() {		
		return "cyp";
	}

	public String getVersion() {
		return "1";
	}

	public PLATFORM getPlatform() {
		return PLATFORM.DESKTOP_JAVA;
	}
	
	class DesktopLogger implements Logger {

		public void debug(String component, String message) {
			System.out.println( new Date().toString() +" "+ component+" -> "+message);
			
		}

		public void info(String component, String message) {
			System.out.println( new Date().toString() +" "+ component+" -> "+message);
		}

		public void error(String component, String message, Throwable ex) {
			System.out.println( new Date().toString() +" "+ component+" -> "+message);
			ex.printStackTrace();		
		}
	}
	
	public static void main(String[] args) throws Exception {
		Application.configure("com.cyp.test.ConnectionTest", null);
//		Connection conn = ConnectionFactory.getFactory().createConnection(CONNECTION_TYPE.XMPP_GTALK_MD5);
//		conn.login("amator77@gmail.com", "leo@1977");
//		conn.login("florea.leonard@gmail.com", "mirela76");
		Connection conn = ConnectionFactory.getFactory().createConnection(CONNECTION_TYPE.XMPP_FACEBOOK_OAUTH2);
		conn.login("438318006237702", "AAAGOpd0PegYBAIe6ZCZAAcBz3k3gzyebjcG6kAQicywmSnfKUbVo44wOiS0rwzuT4LFbJ7LMwkcVEyCdgkeJKZAQOdFHfZBHTOtrukkc8HPP14mr8qck");
		conn.sendMessage(new ChatMessage("-100000008632078@chat.facebook.com","test"));
//		Connection conn = ConnectionFactory.getFactory().createConnection(CONNECTION_TYPE.XMPP_FACEBOOK_OAUTH2);
//		conn.login("438318006237702", "AAAGOpd0PegYBAIttbQNG4QruBSbdNu3XbdcggrDZAf1UmCciI0X83VQl2c7NpEfPaHmFTGK0ZAq1FzqDX0uvBN3cA8toslveH6V3zy7QZDZD");
//		
	}

	public List<String> getApplicationFutures() {		
		return new ArrayList<String>();
	}

	public void registerAccount(Account account) {		
	}

	public void removeAccount(Account account) { 
	}

	public String getGameBaseURL() {
		return "http://api.chessyoup.com";
	}
}
