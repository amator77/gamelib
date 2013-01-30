package com.cyp.transport.xmpp;

import java.util.Collection;

public interface XMPPListener {

	public void newEntriesAdded(Collection<String> jabberIds);

	public void entriesDeleted(Collection<String> jabberIds);

	public void entriesUpdated(Collection<String> jabberIds);
}
