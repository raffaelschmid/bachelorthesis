/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.ui.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.trivadis.loganalysis.ui.internal.messages"; //$NON-NLS-1$
	public static String OpenGcLoganalysis_progress_message;
	public static String DashboardEditor_Title;
	public static String DashboardEditor_Section_Feeds;
	public static String DashboardEditor_Section_HelpAndDocumentation;
	public static String DashboardEditor_Section_Updates;
	public static String DashboardEditor_Section_General;
	public static String DashboardEditorPageOverview_5;
	public static String DashboardEditorPageOverview_8;
	public static String DeleteFileDialog_0;
	public static String DeleteFileDialog_1;
	public static String DeleteFileDialog_3;
	public static String ImportGCLogWizard_1;
	public static String ImportGCLogWizard_2;
	public static String ImportGCLogWizard_4;
	public static String ImportGCLogWizardSelectionPage_0;
	public static String ImportGCLogWizardSelectionPage_1;
	public static String ImportGCLogWizardSelectionPage_3;
	public static String ImportGCLogWizardSelectionPage_6;
	public static String OpenGcLoganalysis_implementation_not_found_title;
	public static String OpenGcLoganalysis_implementation_not_found_text;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
