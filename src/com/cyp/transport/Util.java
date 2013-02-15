package com.cyp.transport;

import com.cyp.application.Application;
import com.cyp.application.Context;
import com.cyp.application.Context.PLATFORM;
import com.cyp.transport.xmpp.XMPPContact;

public class Util {
	
	public static String getApplicationResource(){
		return getPlatformIdentifier(Application.getContext().getPlatform())+"_"+Application.getContext().getApplicationName()+"_"+Application.getContext().getVersion()+"_";
	}
	
	public static PLATFORM getPlatform(String resource){
		
		if( resource != null ){
			String parts[] = resource.split("_");
			
			try{
				String platform = parts[0];
				
				if( platform.equals(Context.DESKTOP_JAVA) ){
					return PLATFORM.DESKTOP_JAVA;
				}
				else if( platform.equals(Context.DESKTOP_WEB) ){
					return PLATFORM.DESKTOP_WEB;
				}
				else if( platform.equals(Context.DESKTOP_NATIVE) ){
					return PLATFORM.DESKTOP_NATIVE;
				}
				else if( platform.equals(Context.MOBILE_ANDROID) ){
					return PLATFORM.MOBILE_ANDROID;
				}
				else if( platform.equals(Context.MOBILE_IPHONE) ){
					return PLATFORM.MOBILE_IPHONE;
				}
				else{
					return PLATFORM.UNKNOWN;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				return PLATFORM.UNKNOWN;
			}			
		}
		else{
			return PLATFORM.UNKNOWN;
		}
	}
	
	public static String getClientType(String resource){
		if( resource != null ){
			String parts[] = resource.split("_");
			
			try{
				if( parts.length > 1){
					return  parts[1];
				}
				else{
					return null;
				}								
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}			
		}
		else{
			return null;
		}
	}
	
	public static String getClientVersion(String resource){		
		if( resource != null ){
			String parts[] = resource.split("_");
			
			try{
				if( parts.length > 2){
					return  parts[2];
				}
				else{
					return null;
				}								
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}			
		}
		else{
			return null;
		}
	}
	
	public static String getResourceFromId(String id){
		String parts[] = id.split("/");
		
		if( parts.length > 1 ){
			return parts[1];
		}
		else{
			return null;
		}
	}
	
	public static String getContactFromId(String id){
		String parts[] = id.split("/");
		return parts[0];
	}
	
	public static String getUsernameFromId(String id){
		String parts[] = id.split("@");
		return parts[0];
	}
	
	private static String getPlatformIdentifier(PLATFORM platform){
		switch (platform) {
		case DESKTOP_JAVA : return Context.DESKTOP_JAVA;						
		case DESKTOP_WEB : return Context.DESKTOP_WEB;
		case DESKTOP_NATIVE : return Context.DESKTOP_NATIVE;
		case MOBILE_ANDROID : return Context.MOBILE_ANDROID;
		case MOBILE_IPHONE : return Context.MOBILE_IPHONE;
		
		default:
			return Context.DESKTOP_JAVA;			
		}
	}

	public static boolean isCompatible(XMPPContact xmppContact) {
		return getPlatform(getResourceFromId(xmppContact.getId())) != PLATFORM.UNKNOWN;
	}
}
