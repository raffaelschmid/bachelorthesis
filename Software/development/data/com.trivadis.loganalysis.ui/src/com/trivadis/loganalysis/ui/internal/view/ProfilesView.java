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
package com.trivadis.loganalysis.ui.internal.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.domain.Profile;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;

public class ProfilesView extends ViewPart implements SelectedFilesChangeListener {

	public static final String ID = ProfilesView.class.getName();

	private static final String STORAGE_KEY = ID + ".profiles";

	private TableViewer viewer;
	private Action doubleClickAction;

	private final IUiContext context;

	public ProfilesView() {
		this(UiLoganalysis.getContext());
	}

	public ProfilesView(IUiContext context) {
		this.context = context;
		context.addLogFilesChangeListener(this);
	}

	class ProfilesContentAdapter implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return context.getProfiles().toArray();
		}
	}

	class ProfilesLabelAdapter extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return Activator.getDefault().getImageDescriptor("icons/profile.gif").createImage();
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		createListViewer(parent);
		makeActions();
		hookDoubleClickAction();
		registerContextMenu();
	}

	private void createListViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ProfilesContentAdapter());
		viewer.setLabelProvider(new ProfilesLabelAdapter());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
	}

	private void registerContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		getSite().setSelectionProvider(viewer);
	}

	private void makeActions() {
		doubleClickAction = new Action() {
			@Override
			public void run() {
				Command.execute("com.trivadis.loganalysis.ui.openAnalysis");
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		StringBuffer buf = new StringBuffer();
		memento.putString(STORAGE_KEY, buf.toString());
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
//		String value = memento.getString(STORAGE_KEY);
		// TODO: Dummy
		context.addProfile(new Profile("Default"));
	}

	public void fileSelectionChanged() {
		if (viewer != null)
			viewer.refresh();
	}

}