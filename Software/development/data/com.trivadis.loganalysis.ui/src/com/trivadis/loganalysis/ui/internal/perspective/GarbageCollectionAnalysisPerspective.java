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
package com.trivadis.loganalysis.ui.internal.perspective;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

import com.trivadis.loganalysis.ui.internal.Constants;
import com.trivadis.loganalysis.ui.internal.view.LogFilesView;

public class GarbageCollectionAnalysisPerspective implements
		IPerspectiveFactory {

	private IPageLayout factory;

	public void createInitialLayout(IPageLayout factory) {
		this.factory = factory;
		initializeContent();
		initializeMenu();
	}

	private void initializeMenu() {
		factory.addShowViewShortcut(LogFilesView.ID);
	}

	private void initializeContent() {
		addViews();
		addEditor();
	}

	private void addEditor() {
		try {
			IHandlerService handlerService = (IHandlerService) PlatformUI
					.getWorkbench().getActiveWorkbenchWindow()
					.getService(IHandlerService.class);
			handlerService.executeCommand("com.trivadis.loganalysis.ui.openDashboard", null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void addViews() {
		factory.createFolder(Constants.FOLDER_TOP_LEFT, IPageLayout.LEFT,
				0.30f, factory.getEditorArea()).addView(LogFilesView.ID);
	}

}
