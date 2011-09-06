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

import com.trivadis.loganalysis.ui.internal.view.LogFilesView;
import com.trivadis.loganalysis.ui.internal.view.ProfilesView;

public class GarbageCollectionAnalysisPerspective implements IPerspectiveFactory {

	public static final String ID = GarbageCollectionAnalysisPerspective.class.getName();
	private IPageLayout layout;

	public void createInitialLayout(IPageLayout layout) {
		this.layout = layout;
		initializeContent();
		initializeMenu();
	}

	private void initializeMenu() {
		layout.addShowViewShortcut(LogFilesView.ID);
	}

	private void initializeContent() {
		addViews();
		addEditor();
	}

	private void addEditor() {
		try {
			IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getService(IHandlerService.class);
			handlerService.executeCommand("com.trivadis.loganalysis.ui.openDashboard", null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void addViews() {
		layout.createFolder("lefttop", IPageLayout.LEFT, 0.26f, layout.getEditorArea()).addView(LogFilesView.ID);
		layout.createFolder("leftbottom", IPageLayout.BOTTOM, 0.5f, LogFilesView.ID).addView(ProfilesView.ID);
	}

}
