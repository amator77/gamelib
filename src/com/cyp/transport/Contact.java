package com.cyp.transport;

import com.cyp.application.Context.PLATFORM;

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
	 * Get the contact client running platform.
	 * 
	 * @return
	 */
	public PLATFORM getClientPlatform();

	/**
	 * The contact client version.
	 * 
	 * @return
	 */
	public String getClientVersion();

	/**
	 * The contact client type.
	 * 
	 * @return
	 */
	public String getClientType();
}
