package com.trivadis.loganalysis.jrockit;

public class Context {
	private static class Holder{
		private static Context INSTANCE = new Context();
	}
	private Context(){
		
	}
	public static Context getDefault(){
		return Holder.INSTANCE;
	}

}
