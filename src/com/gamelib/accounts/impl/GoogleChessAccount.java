package com.gamelib.accounts.impl;

import java.io.IOException;

import com.gamelib.accounts.Account;
import com.gamelib.application.Application;
import com.gamelib.game.chess.ChessGameController;
import com.gamelib.transport.Connection;
import com.gamelib.transport.Roster;
import com.gamelib.transport.exceptions.LoginException;
import com.gamelib.transport.xmpp.google.XMPPMD5Connection;

public class GoogleChessAccount implements Account {

	private String id;

	private String credentials;

	private XMPPMD5Connection connection;

	private STATUS status;

	private LoginCallback loginCallback;
	
	private ChessGameController chessCtrl;
	
	public GoogleChessAccount(String id, String credentials) {
		this.id = id;
		this.credentials = credentials;
		this.connection = new XMPPMD5Connection();
		this.status = STATUS.OFFLINE;
		this.chessCtrl = new ChessGameController(this);
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
			
			try {
				connection.login(id, credentials);
				
				if( callback != null ){
					callback.onLogginSuccess();
				}
				
			} catch (IOException e) {
				Application.getContext().getLogger().error("GoogleChessAccount", "Connection error!", e);
				
				if( callback != null ){
					callback.onLogginError("Connection error!");
				}
				
			} catch (LoginException e) {
				Application.getContext().getLogger().error("GoogleChessAccount", "Invalid username or password!", e);
				
				if( callback != null ){
					callback.onLogginError("Invalid username or password!");
				}
			}
		}
	}

	@Override
	public void logout() {
		if (this.connection.isConnected()) {
			this.connection.logout();
		}
	}	

	@Override
	public ChessGameController getGameController() {
		return this.chessCtrl;
	}

	@Override
	public String getIconTypeResource() {
		return null;
	}
	
	@Override
	public String toString() {
		return "GoogleAccount [id=" + id + ", credentials=" + credentials
				+ ", connection=" + connection + ", status=" + status
				+ ", loginCallback=" + loginCallback + "]";
	}
}
