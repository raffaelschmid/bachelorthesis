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
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
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
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;

public class ProfilesView extends ViewPart implements SelectedFilesChangeListener {

	public static final String ID = ProfilesView.class.getName();

	private static final Object[] EMPTY_ARRAY = new Object[] {};
	private static final String STORAGE_KEY = ID + ".profiles";

	private TreeViewer viewer;
	private Action doubleClickAction;

	private final IUiContext context;

	public ProfilesView() {
		this(UiLoganalysis.getUiContext());
	}

	public ProfilesView(final IUiContext context) {
		this.context = context;
		context.addLogFilesChangeListener(this);
	}

	class ProfilesContentAdapter implements ITreeContentProvider {

		public void dispose() {
		}

		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		}

		public Object[] getElements(final Object inputElement) {
			return UiLoganalysis.getConfigurations().toArray();
		}

		public Object[] getChildren(final Object parentElement) {
			Object[] retVal = EMPTY_ARRAY;
			if (parentElement instanceof IConfiguration) {
				final IConfiguration box = (IConfiguration) parentElement;
				retVal = box.getProfiles().toArray();
			}
			return retVal;
		}

		public Object getParent(final Object element) {
			return (element instanceof IProfile) ? ((IProfile) element).getConfiguration() : null;
		}

		public boolean hasChildren(final Object element) {
			return element != null && element instanceof IConfiguration
					&& ((IConfiguration) element).getProfiles().size() > 0;
		}
	}

	class ProfilesLabelAdapter extends LabelProvider {

		@Override
		public Image getImage(final Object obj) {
			return Activator.getDefault().getImageDescriptor("icons/profile.gif").createImage();
		}

		@Override
		public String getText(final Object obj) {
			String retVal;
			if (obj instanceof IConfiguration) {
				retVal = ((IConfiguration) obj).getLabel();
			} else if (obj instanceof IProfile) {
				retVal = ((IProfile) obj).getLabel();
			} else {
				retVal = getText(obj);
			}
			return retVal;
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		createListViewer(parent);
		makeActions();
		hookDoubleClickAction();
		registerContextMenu();
	}

	private void createListViewer(final Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ProfilesContentAdapter());
		viewer.setLabelProvider(new ProfilesLabelAdapter());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
	}

	private void registerContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		final Menu menu = menuMgr.createContextMenu(viewer.getControl());
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
			public void doubleClick(final DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void saveState(final IMemento memento) {
		super.saveState(memento);
		final StringBuffer buf = new StringBuffer();
		memento.putString(STORAGE_KEY, buf.toString());
	}

	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);
		context.addProfiles(UiLoganalysis.getConfigurations());
		// String value = memento.getString(STORAGE_KEY);
		// TODO: Dummy
		// context.addProfile(new Profile("Default"));
	}

	public void fileSelectionChanged() {
		if (viewer != null)
			viewer.refresh();
	}

}