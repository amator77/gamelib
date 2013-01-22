package com.gamelib.accounts.impl;

import com.gamelib.accounts.Account;
import com.gamelib.game.IGameController;
import com.gamelib.transport.Connection;
import com.gamelib.transport.Roster;
import com.gamelib.transport.exceptions.ConnectionException;
import com.gamelib.transport.exceptions.LoginException;
import com.gamelib.transport.xmpp.google.XMPPMD5Connection;

public class GoogleChessAccount implements Account {

	private String id;

	private String credentials;

	private XMPPMD5Connection connection;

	private STATUS status;

	private LoginCallback loginCallback;

	public GoogleChessAccount(String id, String credentials) {
		this.id = id;
		this.credentials = credentials;
		this.connection = new XMPPMD5Connection();
		this.status = STATUS.OFFLINE;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public Roster getRoster() {
		if (this.connection.isConnected()) {
			return this.connection.getRoster();
		} else {
			return null;
		}
	}

	@Override
	public TYPE getType() {
		return TYPE.XMPP_GOOGLE;
	}

	@Override
	public STATUS getStatus() {
		return this.status;
	}

	@Override
	public void login(LoginCallback callback) {
		if (!this.connection.isConnected() && this.loginCallback == null) {
			this.loginCallback = callback;
			this.runLoginTask();
		}
	}

	@Override
	public void logout() {
		if (this.connection.isConnected()) {
			this.connection.logout();
		}
	}

	private void runLoginTask() {
		try {
			connection.login(id, credentials);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "GoogleAccount [id=" + id + ", credentials=" + credentials
				+ ", connection=" + connection + ", status=" + status
				+ ", loginCallback=" + loginCallback + "]";
	}

	@Override
	public IGameController getGameController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIconTypeResource() {
		return null;
	}
}
