package com.cyp.game.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cyp.accounts.Account;
import com.cyp.application.Application;
import com.cyp.application.Logger;
import com.cyp.game.IChallenge;
import com.cyp.game.IGame;
import com.cyp.game.IGameCommand;
import com.cyp.game.IGameController;
import com.cyp.game.IGameControllerListener;
import com.cyp.transport.Connection;
import com.cyp.transport.ConnectionListener;
import com.cyp.transport.Message;

public abstract class GameController implements IGameController, ConnectionListener {

	private Account account;

	private List<IGameControllerListener> listeners;

	private List<IChallenge> challenges;
	
	private List<IGame> games;
	
	private static final Logger log = Application.getContext().getLogger();

	public GameController(Account account) {
		this.account = account;
		this.listeners = new ArrayList<IGameControllerListener>();
		this.challenges = new ArrayList<IChallenge>();
		this.games = new ArrayList<IGame>();
		this.account.getConnection().addConnectionListener(this);
	}
		
	public Account getAccount() {		
		return this.account;
	}
		
	public void addGameControllerListener(IGameControllerListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}
	
	public boolean sendGameCommand(IGameCommand cmd) throws IOException {
		log.debug(this.getClass().getName(),
				"Send command :" + cmd.toString());

		if (this.account.getConnection().isConnected()) {
			this.account.getConnection().sendMessage(cmd);
			return true;
		}
		else{
			return false;
		}
	}
		
	public List<IChallenge> listChallanges() {
		return this.challenges;
	}

	public void acceptChallenge(IChallenge challenge) throws IOException {
		this.sendChallengeCommand(IGameCommand.CHALLENGE_ACCEPTED_COMMAND_ID,
				challenge);
		this.challenges.remove(challenge);
	}

	public void rejectChallenge(IChallenge challenge) throws IOException {
		this.sendChallengeCommand(IGameCommand.CHALLENGE_REJECTED_COMMAND_ID,
				challenge);
		this.challenges.remove(challenge);
	}

	public void abortChallenge(IChallenge challenge) throws IOException {
		this.sendChallengeCommand(IGameCommand.CHALLENGE_CANCELED_COMMAND_ID,
				challenge);
		this.challenges.remove(challenge);
	}

	public IChallenge sendChallenge(String remoteId, Map<String, String> details)
			throws IOException {
		GameChallenge gchallenge = new GameChallenge(this.account.getId(),
				remoteId);
		this.sendChallengeCommand(IGameCommand.CHALLENGE_COMMAND_ID, gchallenge);
		this.challenges.add(gchallenge);
		return gchallenge;
	}

	public boolean messageReceived(Connection source, Message message) {
		String commandId = message
				.getHeader(IGameCommand.GAME_COMMAND_HEADER_KEY);

		if (commandId != null) {

			log.debug(this.getClass().getName(), "New game command recevied :"
					+ commandId);

			switch (Integer.parseInt(commandId)) {
			case IGameCommand.CHALLENGE_COMMAND_ID:
			case IGameCommand.CHALLENGE_REJECTED_COMMAND_ID:
			case IGameCommand.CHALLENGE_CANCELED_COMMAND_ID:
			case IGameCommand.CHALLENGE_ACCEPTED_COMMAND_ID:
				this.handleChallengeCommand(Integer.parseInt(commandId),
						message.getFrom(),
						message.getHeader(IChallenge.HEADER_TIME_KEY));
				return true;				
			default:
				break;
			}
		} else {
			log.debug(this.getClass().getName(), "Message discarded :"
					+ message.toString());
		}
		
		return false;
	}

	private void handleChallengeCommand(int commandId, String accountId,
			String utcTime) {
		if (accountId != null && utcTime != null) {
			log.debug(this.getClass().getName(), "New challange from :"
					+ accountId + " , cmd :" + commandId);

			try {
				long time = Long.parseLong(utcTime);
				IChallenge challenge = findChallenge(accountId, time);

				switch (commandId) {
				case IGameCommand.CHALLENGE_COMMAND_ID:
					GameChallenge gc = new GameChallenge(this.account.getId(),
							accountId, time);
					this.challenges.add(gc);

					for (IGameControllerListener listener : this.listeners) {
						listener.challengeReceived(gc);
					}

					break;
				case IGameCommand.CHALLENGE_REJECTED_COMMAND_ID:

					if (challenge != null) {
						this.challenges.remove(challenge);

						for (IGameControllerListener listener : this.listeners) {
							listener.challengeRejected(challenge);
						}
					}

					break;
				case IGameCommand.CHALLENGE_CANCELED_COMMAND_ID:
					if (challenge != null) {
						this.challenges.remove(challenge);
						
						for (IGameControllerListener listener : this.listeners) {
							listener.challengeCanceled(challenge);
						}
					}

					break;
				case IGameCommand.CHALLENGE_ACCEPTED_COMMAND_ID:
					if (challenge != null) {
						this.challenges.remove(challenge);

						for (IGameControllerListener listener : this.listeners) {
							listener.challengeAccepted(challenge);
						}
					}
					break;
				default:
					break;
				}

			} catch (Exception e) {
				log.error(getClass().toString(),
						"Exception on processing challenge command!", e);
			}
		} else {
			log.debug(getClass().toString(), "Invalid challenge command");
		}
	}

	public void onDisconect(Connection source) {

	}

	private IChallenge findChallenge(String accountId, long time) {
		for (IChallenge challenge : this.challenges) {

			if (challenge.getRemoteId().equals(accountId)
					&& challenge.getTime() == time) {

				return challenge;
			}
		}

		return null;
	}

	private void sendCommand(GenericSendGameCommand cmd) throws IOException {
		log.debug(this.getClass().getName(),
				"Send command :" + cmd.toString());

		if (this.account.getConnection().isConnected()) {
			this.account.getConnection().sendMessage(cmd);
		}
	}

	private void sendChallengeCommand(int commandId, IChallenge challenge)
			throws IOException {
		GenericSendGameCommand cmd = new GenericSendGameCommand(commandId);
		cmd.setTo(challenge.getRemoteId());
		cmd.setHeader(IGameCommand.GAME_COMMAND_HEADER_KEY,
				String.valueOf(commandId));
		cmd.setHeader(IChallenge.HEADER_TIME_KEY,
				String.valueOf(challenge.getTime()));
		this.sendCommand(cmd);
	}


	public void closeGame(IGame game) {
		this.games.remove(game);		
	}


	public List<IGame> listGames() {		
		return this.games;
	}
}
