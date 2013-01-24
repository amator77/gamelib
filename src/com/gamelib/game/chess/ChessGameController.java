package com.gamelib.game.chess;

import java.util.ArrayList;
import java.util.List;

import com.gamelib.accounts.Account;
import com.gamelib.application.Application;
import com.gamelib.application.Logger;
import com.gamelib.game.IChallenge;
import com.gamelib.game.IGameCommand;
import com.gamelib.game.IGameListener;
import com.gamelib.game.impl.GameController;
import com.gamelib.transport.Connection;
import com.gamelib.transport.Message;

public class ChessGameController extends GameController {

	private List<ChessGame> games;

	private static final Logger log = Application.getContext().getLogger();

	public ChessGameController(Account account) {
		super(account);
		this.games = new ArrayList<ChessGame>();
	}

	@Override
	public boolean messageReceived(Connection source, Message message) {
		boolean isConsumed = super.messageReceived(source, message);

		if (!isConsumed) {
			String commandId = message
					.getHeader(IGameCommand.GAME_COMMAND_HEADER_KEY);

			if (commandId != null) {

				log.debug(this.getClass().getName(),
						"New chess game command recevied :" + commandId);

				String gameId = message
						.getHeader(ChessGameCommand.HEADER_ID_KEY);

				if (gameId != null) {

					ChessGame game = findGame(message.getFrom(),
							Long.parseLong(gameId));

					if (game != null) {

						switch (Integer.parseInt(commandId)) {
						case ChessGameCommand.READY_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener).readyReceived();
							}
							break;
						case ChessGameCommand.MOVE_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.moveReceived(new Move(message
												.getBody()));
							}
							break;
						case ChessGameCommand.CHAT_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.chatReceived(message.getBody());
							}
							break;
						case ChessGameCommand.DRAW_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.drawRequestReceived();
							}
							break;
						case ChessGameCommand.DRAW_ACCEPTED_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.drawAcceptedReceived();
							}
							break;
						case ChessGameCommand.RESIGN_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener).resignReceived();
							}
							break;
						case ChessGameCommand.ABORT_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.abortRequestReceived();
							}
							break;
						case ChessGameCommand.ABORT_ACCEPTED_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.abortAcceptedReceived();
							}
							break;
						case ChessGameCommand.REMATCH_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.rematchRequestReceived();
							}
							break;
						case ChessGameCommand.REMATCH_ACCEPTED_COMMAND_ID:
							for (IGameListener listener : game
									.getGameListeners()) {
								((ChessGameListener) listener)
										.rematchAcceptedReceived();
							}
							break;
						default:
							break;
						}
					} else {
						log.debug(this.getClass().getName(), "No game for id :"
								+ gameId);
					}

				} else {
					log.debug(this.getClass().getName(), "Game id is missing!");
				}
			} else {
				log.debug(this.getClass().getName(),
						"Unknown chess game command:" + message.toString());
			}
		}

		return false;
	}

	public ChessGame createChessGame(IChallenge challenge) {
		ChessGame cg = new ChessGame(this, challenge);
		this.games.add(cg);
		return cg;
	}

	public void closeChessGame(ChessGame game) {
		this.games.remove(game);
	}

	private ChessGame findGame(String fromId, long time) {
		for (ChessGame game : this.games) {
			if (game.getChallenge().getRemoteId().equals(fromId)
					&& game.getChallenge().getTime() == time) {
				return game;
			}
		}

		return null;
	}
}
