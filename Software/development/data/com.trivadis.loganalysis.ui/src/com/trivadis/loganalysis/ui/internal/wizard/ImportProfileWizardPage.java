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

import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.domain.profile.IStandardProfile;
import com.trivadis.loganalysis.ui.internal.Activator;

public class ImportProfileWizardPage extends WizardPage implements ICheckStateListener {

	private static final String[] FILTER_NAMES = { "Loganalysis Profile (*.lap)", "All Files (*.*)" };
	private static final String[] FILTER_EXTS = { "*.lap", "*.*" };

	private CheckboxTableViewer listViewer;
	private final List<IProfile> profiles = new ArrayList<IProfile>();

	private final Predicate<IProfile> noStandardProfiles = new Predicate<IProfile>() {
		public boolean matches(final IProfile item) {
			return !(item instanceof IStandardProfile);
		}
	};
	private final ClosureIO<IConfiguration, List<IProfile>> collectProfiles = new ClosureIO<IConfiguration, List<IProfile>>() {
		public List<IProfile> call(final IConfiguration in) {
			return in.getProfiles();
		}
	};
	private Text fileName;

	public ImportProfileWizardPage() {
		super("Import Garbage Collection Analysis Profile", "Garbage Collection Analysis Profile", Activator
				.getDefault().getImageDescriptor("icons/gclog_import.gif")); //$NON-NLS-1$
		setDescription("Import Garbage Collection Analysis Profiles from a file.");
	}

	public void createControl(final Composite parent) {
		initializeDialogUnits(parent);
		final Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout());
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());

		createSourceGroup(topLevel);
		createDestinationGroup(topLevel);

		setErrorMessage(null);
		setMessage(null);
		setControl(topLevel);
	}

	final void createDestinationGroup(final Composite parent) {
		listViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		final GridData data = new GridData(GridData.FILL_BOTH);
		listViewer.getTable().setLayoutData(data);
		listViewer.getTable().setFont(parent.getFont());
		listViewer.setContentProvider(new IStructuredContentProvider() {

			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
			}

			public void dispose() {
			}

			@SuppressWarnings("unchecked")
			public Object[] getElements(final Object inputElement) {
				return ((List<IProfile>) inputElement).toArray();
			}
		});
		listViewer.setLabelProvider(new LabelProvider() {
			@Override
			public boolean isLabelProperty(final Object element, final String property) {
				return false;
			}

			@Override
			public Image getImage(final Object element) {
				return Activator.getDefault().getImage("icons/document.gif"); //$NON-NLS-1$
			}

			@Override
			public String getText(final Object element) {
				final String retVal;
				if (element instanceof IProfile) {
					final IProfile profile = (IProfile) element;
					retVal = profile.getLabel() + " (" + profile.getConfiguration().getLabel() + ")";
				} else {
					retVal = element.toString();
				}
				return retVal;
			}

		});
		listViewer.addCheckStateListener(this);
	}

	private void createSourceGroup(final Composite parent) {
		final Composite directorySelection = new Composite(parent, SWT.NONE);
		directorySelection.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		directorySelection.setLayout(fileSelectionLayout());
		fileName = new Text(directorySelection, SWT.BORDER);
		final Button save = new Button(directorySelection, SWT.PUSH);
		save.setText("Select File");
		fileName.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				// User has selected to save a file
				final FileDialog dlg = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						SWT.SAVE);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				final String fn = dlg.open();
				if (fn != null) {
					fileName.setText(fn);
					listViewer.setInput(findAll(UiLoganalysis.getProfilesFromFile(fn), new Predicate<IProfile>() {
						public boolean matches(final IProfile item) {
							return !(item instanceof IStandardProfile);
						}
					}));
				}
			}
		});
	}

	private GridLayout fileSelectionLayout() {
		final GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 3;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		return fileSelectionLayout;
	}

	public void checkStateChanged(final CheckStateChangedEvent event) {
		if (event.getElement() instanceof IProfile) {
			final IProfile profile = (IProfile) event.getElement();
			if (event.getChecked() && !getProfiles().contains(profile)) {
				profiles.add(profile);
			} else if (!event.getChecked() && getProfiles().contains(profile)) {
				profiles.remove(profile);
			}
		}
	}

	public List<IProfile> getProfiles() {
		return profiles;
	}

	public String getFileName() {
		return fileName.getText();
	}

}
