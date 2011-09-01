package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.Messages;

public class JRockitAnalysisEditorPageOverview extends GridFormPage {

	public static final String ID = JRockitAnalysisEditorPageOverview.class.getName();

	public JRockitAnalysisEditorPageOverview(JRockitAnalysisEditor editor) {
		super(editor, ID, "Overview", 2, 1);
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		createGeneralSection(managedForm, toolkit);
	}

	private void createGeneralSection(IManagedForm managedForm, FormToolkit toolkit) {
		Composite client = createGridSection(managedForm, Messages.DashboardEditor_Section_General,
				"", 1);

		linkSwitchPerspective(client, toolkit);
	}

	private Hyperlink linkSwitchPerspective(Composite client, FormToolkit toolkit) {
		Hyperlink link = toolkit.createHyperlink(client, "asdf", SWT.NONE);
		link.setText(Messages.DashboardEditorPageOverview_5);
		link.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
			}
		});
		return link;
	}

}