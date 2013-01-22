package com.gamelib.game;

import java.io.IOException;
import java.util.Map;

public interface IGameController {

	/**
	 * 
	 * @param listener
	 */
	public void addGameControllerListener(IGameControllerListener listener);

	/**
	 * Send accept for this challange.The client of this method must wait for an
	 * challengeAccepted event in order to start the game.
	 * 
	 * @param challenge
	 */
	public void acceptChallenge(IChallenge challenge) throws IOException;

	/**
	 * 
	 * @param challenge
	 * @param reason
	 */
	public void rejectChallenge(IChallenge challenge) throws IOException;

	/**
	 * 
	 * @param remoteContact
	 * @param details
	 */
	public void sendChallenge(String remoteId, Map<String, String> details)
			throws IOException;
}
