package com.gamelib.game;

public interface IGameControllerListener {
	
	/**
	 * New challenge received from an remote contact.
	 * 
	 * @param challenge
	 */
	public void challengeReceived(IChallenge challenge);

	/**
	 * Challenge aborted by the owner.
	 * 
	 * @param challenge
	 */
	public void challengeCanceled(IChallenge challenge);
	
	/**
	 * Event for challenge accepted.In this moment is safe to start the game
	 * activity.
	 * 
	 * @param gameStarted
	 */
	public void challengeAccepted(IChallenge challenge);
	
	/**
	 * 
	 * @param challeng
	 */
	public void challengeRejected(IChallenge challeng);
}
