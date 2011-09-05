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
package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

public class AnalysisEditorContribution extends
		MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;


	public void setActivePage(IEditorPart part) {
		if (activeEditorPart == part)
			return;
		activeEditorPart = part;
	}

}
