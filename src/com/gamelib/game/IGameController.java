package com.gamelib.game;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.gamelib.accounts.Account;

public interface IGameController {
	
	/**
	 * Get the account of this controller
	 * @return
	 */
	public Account getAccount();
	
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
	 * @param remoteId
	 * @param details
	 * @return
	 * @throws IOException
	 */
	public IChallenge sendChallenge(String remoteId, Map<String, String> details)
			throws IOException;
	
	/**
	 * 
	 * @param challenge
	 * @throws IOException
	 */
	public void abortChallenge(IChallenge challenge) throws IOException;
	
	/**
	 * Return the current list of challenges( send or received )
	 * @return
	 */
	public List<IChallenge> listChallanges();
}
