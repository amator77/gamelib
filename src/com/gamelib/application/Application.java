package com.gamelib.application;


public class Application {
		
	private static Context applicationContext;
	
	private Application(){
		
	}
	
	public synchronized static void configure( String contextClassName ,  Object contextData ) throws Exception{
		Class<Context> contextClass =  (Class<Context>)Class.forName(contextClassName);
		Application.applicationContext =  contextClass.newInstance();
		Application.applicationContext.initialize(contextData);		
	}
	
	public static Context getContext(){
		if( Application.applicationContext  == null ){
			throw new RuntimeException("Application is not configured!");
		}
		else{
			return Application.applicationContext;
		}
	}
}
