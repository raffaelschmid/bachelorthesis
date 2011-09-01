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
