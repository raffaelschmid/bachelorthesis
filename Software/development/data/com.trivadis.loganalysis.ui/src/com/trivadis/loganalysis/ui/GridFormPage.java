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
package com.trivadis.loganalysis.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public abstract class GridFormPage extends FormPage {

	private final int maxNumColumns, minNumColumns;

	public GridFormPage(final FormEditor editor, final String id, final String title, final int maxNumColumns,
			final int minNumColumns) {
		super(editor, id, title);
		this.maxNumColumns = maxNumColumns;
		this.minNumColumns = minNumColumns;
	}

	@Override
	public void setPartName(final String partName) {
		super.setPartName(partName);
		firePartPropertyChanged("name", null, partName);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.getBody().setLayout(getLayout());
		createSections(managedForm);
	}

	protected abstract void createSections(IManagedForm managedForm);

	private Layout getLayout() {
		final ColumnLayout layout = new ColumnLayout();
		layout.topMargin = 0;
		layout.bottomMargin = 5;
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		layout.maxNumColumns = maxNumColumns;
		layout.minNumColumns = minNumColumns;
		return layout;
	}

	protected Composite createGridSection(final IManagedForm mform, final String title, final String desc,
			final int numColumns, final int widthHint, final int heightHint, final boolean expanded) {

		final ScrolledForm form = mform.getForm();
		final FormToolkit toolkit = mform.getToolkit();
		final Section section = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR
				| Section.DESCRIPTION | Section.EXPANDED);
		section.setText(title);
		section.setDescription(desc);
		if (widthHint != -1 && heightHint != -1)
			section.setLayoutData(new ColumnLayoutData(widthHint, heightHint));
		final Composite client = toolkit.createComposite(section);
		final GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = numColumns;
		client.setLayout(layout);
		section.setClient(client);
		section.setExpanded(expanded);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(final ExpansionEvent e) {
				form.reflow(false);
			}
		});
		return client;
	}

	protected Composite createGridSection(final IManagedForm form, final String title, final String desc,
			final int numCol, final boolean expanded) {
		return createGridSection(form, title, desc, numCol, SWT.DEFAULT, SWT.DEFAULT, expanded);
	}

}
