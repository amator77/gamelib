package com.cyp.game;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cyp.accounts.Account;
import com.cyp.transport.Contact;

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
	public IChallenge sendChallenge(Contact remoteContact, Map<String, String> details)
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
	
	/**
	 * 
	 * @param challenge
	 * @return
	 */
	public IGame startGame(IChallenge challenge);
	
	/**
	 * 
	 * @param game
	 * @return
	 */
	public void closeGame(IGame game);
	
	/**
	 * Get the list with started games
	 * @return
	 */
	public List<IGame> listGames();
}
