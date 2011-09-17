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

import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.part.ViewPart;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.ui.IUiContext;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.common.binding.IListChangeListener;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.internal.Command;

public class ProfilesView extends ViewPart implements ISelectionListener, IListChangeListener {

	public static final String ID = ProfilesView.class.getName();

	private TreeViewer viewer;
	private Action doubleClickAction;

	private final IUiContext context;

	public ProfilesView() {
		this(UiLoganalysis.getUiContext());
	}

	public ProfilesView(final IUiContext context) {
		this.context = context;
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
		viewer.setContentProvider(new ProfilesContentAdapter(true));
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
		foreach(context.getProfiles(), new ClosureI<IConfiguration>() {
			public void call(final IConfiguration in) {
				in.save(memento);
			}
		});
		showXml(memento);
	}

	@SuppressWarnings("unused")
	private void showXml(final IMemento memento) {
		final Writer sw = new StringWriter();
		final XMLMemento xmlMemento = (XMLMemento) memento;
		try {
			xmlMemento.save(sw);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		System.out.println(sw.toString());
	}

	@Override
	public void init(final IViewSite site, final IMemento memento) throws PartInitException {
		super.init(site, memento);
		showXml(memento);
		context.addConfigurations(UiLoganalysis.getConfigurations(memento));
		context.getProfiles().addChangeListener(this); // data binding
		for (final IConfiguration configuration : context.getProfiles()) {
			configuration.getProfiles().addChangeListener(this);
		}
		getSite().getPage().addSelectionListener(this);
	}

	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (selection instanceof StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) selection;
			if (ss.getFirstElement() instanceof IProfile) {
				final IProfile profile = (IProfile) ss.getFirstElement();
				context.setSelectedProfile(profile);
			}
		}
	}

	public void listChanged() {
		if (viewer != null)
			viewer.refresh();
	}
}