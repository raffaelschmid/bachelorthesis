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

	public GridFormPage(FormEditor editor, String id, String title, int maxNumColumns,
			int minNumColumns) {
		super(editor, id, title);
		this.maxNumColumns = maxNumColumns;
		this.minNumColumns = minNumColumns;
	}

	protected void createFormContent(IManagedForm managedForm) {
		ScrolledForm form = managedForm.getForm();
		form.getBody().setLayout(getLayout());
		createSections(managedForm);
	}

	protected abstract void createSections(IManagedForm managedForm);

	private Layout getLayout() {
		ColumnLayout layout = new ColumnLayout();
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

	protected Composite createGridSection(IManagedForm mform, String title, String desc,
			int numColumns, int widthHint, int heightHint) {

		final ScrolledForm form = mform.getForm();
		FormToolkit toolkit = mform.getToolkit();
		Section section = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR
				| Section.DESCRIPTION | Section.EXPANDED);
		section.setText(title);
		section.setDescription(desc);
		if (widthHint != -1 && heightHint != -1)
			section.setLayoutData(new ColumnLayoutData(widthHint, heightHint));
		Composite client = toolkit.createComposite(section);
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = numColumns;
		client.setLayout(layout);
		section.setClient(client);
		section.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				form.reflow(false);
			}
		});
		return client;
	}

	protected Composite createGridSection(IManagedForm form, String title, String desc,
			int numCol) {
		return createGridSection(form, title, desc, numCol, SWT.DEFAULT, SWT.DEFAULT);
	}

}
