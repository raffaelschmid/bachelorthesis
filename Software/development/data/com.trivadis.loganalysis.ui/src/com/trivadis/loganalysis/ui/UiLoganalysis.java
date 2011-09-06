package com.trivadis.loganalysis.ui;

import com.trivadis.loganalysis.ui.internal.UiContext;


public class UiLoganalysis {
	private static class Holder {
		private static IUiContext INSTANCE = new UiContext();
	}
	
	public static IUiContext getContext(){
		return Holder.INSTANCE;
	}
}
