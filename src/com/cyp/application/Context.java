package com.cyp.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.cyp.accounts.Account;

public interface Context {
	
	public enum PLATFORM {
		DESKTOP_JAVA , DESKTOP_WEB , DESKTOP_NATIVE , MOBILE_ANDROID, MOBILE_IPHONE, UNKNOWN
	}
	
	public static final String DESKTOP_JAVA = "dj";
	
	public static final String DESKTOP_WEB = "dw";
	
	public static final String DESKTOP_NATIVE = "dn";
	
	public static final String MOBILE_ANDROID = "ma";
	
	public static final String MOBILE_IPHONE = "mi";
	
	public void initialize(Object contextData);
	
	public InputStream getResourceAsInputStream(String resource) throws IOException;
	
	public Logger getLogger();
	
	public List<Account> listAccounts();
	
	public void registerAccount(Account account);
	
	public void removeAccount(Account account);
	
	public String getApplicationName();
	
	public String getVersion();
	
	public PLATFORM getPlatform();	
	
	public List<String> getApplicationFutures();
}
