package com.trivadis.loganalysis.jrockit.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.trivadis.loganalysis.jrockit.ui.messages"; //$NON-NLS-1$
	public static String Axis_X;
	public static String Axis_Y;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
