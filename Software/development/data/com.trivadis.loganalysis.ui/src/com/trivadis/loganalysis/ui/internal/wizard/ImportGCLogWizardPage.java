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
package com.trivadis.loganalysis.ui.internal.wizard;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.FileDescriptor;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Messages;

public class ImportGCLogWizardPage extends WizardPage implements
		ICheckStateListener {

	private static final Object[] EMPTY = new Object[] {};
	private CheckboxTableViewer listViewer;
	private final List<IFileDescriptor> files = new ArrayList<IFileDescriptor>();

	public ImportGCLogWizardPage() {
		super(Messages.ImportGCLogWizardSelectionPage_0, Messages.ImportGCLogWizardSelectionPage_1,
				Activator.getDefault().getImageDescriptor("icons/gclog_import.gif")); //$NON-NLS-1$
		setDescription(Messages.ImportGCLogWizardSelectionPage_3);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout());
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());

		createSourceGroup(topLevel);
		createDestinationGroup(topLevel);

		setErrorMessage(null);
		setMessage(null);
		setControl(topLevel);
	}

	private void createSourceGroup(Composite parent) {
		Composite directorySelection = new Composite(parent, SWT.NONE);
		directorySelection.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL));
		directorySelection.setLayout(fileSelectionLayout());
		addDirectoryFieldEditor(directorySelection);
	}

	final void createDestinationGroup(Composite parent) {
		listViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		listViewer.getTable().setLayoutData(data);
		listViewer.getTable().setFont(parent.getFont());
		listViewer.setContentProvider(new IStructuredContentProvider() {

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object inputElement) {
				Object[] retVal = EMPTY;
				if (inputElement instanceof IPath) {
					IPath path = (IPath) inputElement;
					File file = path.toFile();
					if (file.isDirectory()) {
						return file.listFiles(new FileFilter() {
							public boolean accept(File pathName) {
								return pathName.isFile();
							}
						});
					}
				}
				return retVal;
			}
		});
		listViewer.setLabelProvider(new ILabelProvider() {
			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {

			}

			public Image getImage(Object element) {
				return Activator.getDefault().getImage("icons/document.gif"); //$NON-NLS-1$
			}

			public String getText(Object element) {
				return element.toString();
			}

		});
		listViewer.addCheckStateListener(this);

	}

	private void addDirectoryFieldEditor(Composite directorySelection) {
		final DirectoryFieldEditor directory = new DirectoryFieldEditor(
				"fileSelect", Messages.ImportGCLogWizardSelectionPage_6, directorySelection); //$NON-NLS-1$
		directory.getTextControl(directorySelection).addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						IPath path = new Path(directory.getStringValue());
						listViewer.setInput(path);
					}
				});
	}

	private GridLayout fileSelectionLayout() {
		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 3;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		return fileSelectionLayout;
	}

	public void checkStateChanged(CheckStateChangedEvent event) {
		if (event.getElement() instanceof File) {
			File file = (File) event.getElement();
			if (event.getChecked() && !getFiles().contains(file)) {
				files.add(FileDescriptor.fromFile(file));
			} else if (!event.getChecked() && getFiles().contains(file)) {
				files.remove(file);
			}
		}
	}

	public List<IFileDescriptor> getFiles() {
		return files;
	}

}
