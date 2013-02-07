package com.cyp.transport.xmpp;

import java.util.LinkedList;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import com.cyp.application.Application;

public class XMPPAvatarManager implements Runnable {
	
	private static final XMPPAvatarManager manager = new XMPPAvatarManager();
	
	private static final Object lock = new Object();
	
	private LinkedList<Job> jobs;
	
	private Thread avatarThread;
	
	private XMPPAvatarManager(){
		this.jobs = new LinkedList<XMPPAvatarManager.Job>();		
	}
		
	public static XMPPAvatarManager getManager(){
		return XMPPAvatarManager.manager;
	}

	public void run() {
		while(true){
			
			Job job = null;
			
			while( (job = jobs.poll()) != null ){
				byte[] data = job.loadAvatar();
				job.contact.setAvatar(data);
			}
			
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {					
					Application.getContext().getLogger().debug("XMPPAvatarManager", "Closing avatar thread");
					return;
				}
			}
		}
	}
	
	public void loadAvatar(XMPPContact contact, XMPPConnection connection){		
		this.jobs.add(new Job(contact, connection));
		lock.notify();
	}
	
	public synchronized void start(){
		if( this.avatarThread == null ){
			this.avatarThread = new Thread(this, "Avatar thread");
			this.avatarThread.start();
		}
	}
	
	public synchronized void stop(){
		if( this.avatarThread != null ){
			this.avatarThread.interrupt();
			this.avatarThread = null;
		}
	}
	
	private class Job{
		
		XMPPContact contact;
		XMPPConnection conn;
		
		public Job(XMPPContact contact,XMPPConnection conn){
			this.contact = contact;
			this.conn = conn;
		}
		
		public byte[] loadAvatar() {
			
			VCard card = new VCard();
			
			try {
				card.load(conn, contact.getId());
				Application.getContext().getLogger().debug("XMPPAvatarManager", "Avatar loaded.Size for "+contact.getId()+" :"+( card.getAvatar() !=null ? card.getAvatar().length : 0 ));
				return card.getAvatar();
			} catch (XMPPException e) {
				Application.getContext().getLogger().error("XMPPAvatarManager", "Error on loading avatar for "+contact.getId(),e);				
				return null;
			}
		}		
	}
}
