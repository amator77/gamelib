package com.gamelib.game.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gamelib.accounts.Account;
import com.gamelib.application.Application;
import com.gamelib.application.Logger;
import com.gamelib.game.IChallenge;
import com.gamelib.game.IGameCommand;
import com.gamelib.game.IGameController;
import com.gamelib.game.IGameControllerListener;
import com.gamelib.game.commands.GenericSendGameCommand;
import com.gamelib.transport.Connection;
import com.gamelib.transport.ConnectionListener;
import com.gamelib.transport.Message;

public class GameController implements IGameController, ConnectionListener {
	
	private Account account;

	private List<IGameControllerListener> listeners;

	private static final Logger log = Application.getContext().getLogger();

	public GameController(Account account) {
		this.account = account;
		this.listeners = new ArrayList<IGameControllerListener>();
		this.account.getConnection().addConnectionListener(this);
	}

	@Override
	public void addGameControllerListener(IGameControllerListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	@Override
	public void acceptChallenge(IChallenge challenge) {

	}

	@Override
	public void rejectChallenge(IChallenge challenge) {
		
		
//		this.account.getConnection().sendMessage(new GenericSendGameCommand(IGameCommand.CHALLENGE_COMMAND_ID));
	}

	@Override
	public void sendChallenge(String remoteId, Map<String, String> details)
			throws IOException {
		log.debug(this.getClass().getName(), "Send challenge command to :"
				+ remoteId);
		GenericSendGameCommand cmd = new GenericSendGameCommand(IGameCommand.CHALLENGE_COMMAND_ID);
		cmd.setHeaderProperty("time",String.valueOf(System.currentTimeMillis()));
		this.account.getConnection().sendMessage(cmd);
	}

	@Override
	public void messageReceived(Connection source, Message message) {
		String commandId = message.getHeader(IGameCommand.GAME_COMMAND_HEADER_KEY);

		if (commandId != null) {

			log.debug(this.getClass().getName(), "New game command recevied :"
					+ commandId);

			switch (Integer.parseInt(commandId)) {
			case IGameCommand.CHALLENGE_COMMAND_ID:
				this.fireChallengeReceivedEvent(new GameChallenge(message
						.getTo(), message.getFrom()));
				break;
			default:
				break;
			}
		} else {
			log.debug(this.getClass().getName(), "Message discarded :"
					+ message.toString());
		}
	}

	@Override
	public void onDisconect(Connection source) {

	}

	protected void fireChallengeReceivedEvent(GameChallenge challenge) {
		for (IGameControllerListener listener : this.listeners) {
			listener.challengeReceived(challenge);
		}
	}
}
