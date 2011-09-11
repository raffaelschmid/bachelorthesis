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

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;
import com.trivadis.loganalysis.ui.internal.view.ProfilesContentAdapter;
import com.trivadis.loganalysis.ui.internal.view.ProfilesLabelAdapter;

public class NewProfileWizardPage extends WizardPage {

	private TreeViewer viewer;
	private Text txtProfileName;

	protected NewProfileWizardPage(final String pageName) {
		super(pageName);
	}

	public void createControl(final Composite parent) {
		initializeDialogUnits(parent);
		final Composite topLevel = new Composite(parent, SWT.NONE);
		topLevel.setLayout(new GridLayout());
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());

		createConfigurationSelectionPanel(topLevel);
		createProfileConfigurationPanel(topLevel);

		setErrorMessage(null);
		setMessage(null);
		setControl(topLevel);
	}

	private void createProfileConfigurationPanel(final Composite root) {
		final Composite parent = new Composite(root, SWT.NONE);
		parent.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.FILL_HORIZONTAL));
		parent.setLayout(fileSelectionLayout());
		final Label lblProfileName = new Label(parent, SWT.NONE);
		lblProfileName.setText("Profile Name:");
		txtProfileName = new Text(parent, SWT.BORDER);
		txtProfileName.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	final void createConfigurationSelectionPanel(final Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		final GridData data = new GridData(GridData.FILL_BOTH);
		viewer.getTree().setLayoutData(data);
		viewer.setContentProvider(new ProfilesContentAdapter(false));
		viewer.setLabelProvider(new ProfilesLabelAdapter());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(parent);
	}

	private GridLayout fileSelectionLayout() {
		final GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 2;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		return fileSelectionLayout;
	}

	public IConfiguration getSelectedConfiguration() {
		IConfiguration retVal = null;
		if (viewer.getSelection() instanceof StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) viewer.getSelection();
			retVal = (IConfiguration) ss.getFirstElement();
		}
		return retVal;
	}

	public String getProfileLabel() {
		return txtProfileName.getText();
	}
}
