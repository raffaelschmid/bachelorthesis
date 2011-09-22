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

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.trivadis.loganalysis.core.domain.FileDescriptor;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.common.binding.IListChangeListener;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;

public class LogFilesView extends ViewPart implements IListChangeListener, ISelectionListener {

	public static final String ID = LogFilesView.class.getName();

	private static final String STORAGE_KEY = ID + ".logFiles";

	private TableViewer viewer;
	private Action doubleClickAction;

	private final IUiContext context;

	public LogFilesView() {
		this(UiLoganalysis.getDefault().getUiContext());
	}

	public LogFilesView(final IUiContext context) {
		this.context = context;
	}

	class LogFilesContentProvider implements IStructuredContentProvider {
		public void inputChanged(final Viewer v, final Object oldInput, final Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(final Object parent) {
			return context.getSelectedFiles().toArray();
		}
	}

	class LogFilesLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(final Object obj, final int index) {
			String retVal;
			if (obj instanceof IFileDescriptor) {
				retVal = ((IFileDescriptor) obj).getFileName();
			} else {
				retVal = getText(obj);
			}
			return retVal;
		}

		public Image getColumnImage(final Object obj, final int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(final Object obj) {
			return Activator.getDefault().getImageDescriptor("icons/gclog.gif").createImage();
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
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new LogFilesContentProvider());
		viewer.setLabelProvider(new LogFilesLabelProvider());
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
		for (final IFileDescriptor file : context.getSelectedFiles()) {
			buf.append(file.getAbsolutePath() + File.pathSeparator);
		}
		memento.putString(STORAGE_KEY, buf.toString());
	}

	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			final String value = memento.getString(STORAGE_KEY);
			if (value != null) {
				for (final String filePath : value.split(File.pathSeparator)) {
					final IFileDescriptor file = FileDescriptor.fromFile(filePath);
					if (file != null)
						context.addSelectedFile(file);
				}
			}
		}
		getSite().getPage().addSelectionListener(this);
		context.getSelectedFiles().addChangeListener(this);
	}

	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (selection instanceof StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) selection;
			if (ss.getFirstElement() instanceof IFileDescriptor) {
				final IFileDescriptor fd = (IFileDescriptor) ss.getFirstElement();
				context.setSelectedLogFile(fd);
			}
		}

	}

	public void listChanged() {
		if (viewer != null)
			viewer.refresh();
	}

}