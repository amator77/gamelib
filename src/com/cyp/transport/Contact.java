package com.cyp.transport;


public interface Contact {
	
	/**
	 * Contact unique ID
	 * 
	 * @return
	 */
	public String getId();
	
	/**
	 * Contact name.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Contact presence status.
	 * 
	 * @return
	 */
	public Presence getPresence();
	
	/**
	 * 
	 * @return
	 */
	public byte[] getAvatar();
	
	/**
	 * 
	 * @return
	 */
	public boolean  isCompatible();
}
