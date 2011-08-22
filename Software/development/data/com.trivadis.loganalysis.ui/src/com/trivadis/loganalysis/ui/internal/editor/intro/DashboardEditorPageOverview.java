package com.trivadis.loganalysis.ui.internal.editor.intro;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import com.trivadis.loganalysis.ui.Messages;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;
import com.trivadis.loganalysis.ui.internal.editor.GridFormPage;

public class DashboardEditorPageOverview extends GridFormPage {

	public static final String ID = DashboardEditorPageOverview.class.getName();

	public DashboardEditorPageOverview(DashboardEditor editor) {
		super(editor, ID, Messages.DashboardEditor_Title, 2, 1);
	}

	protected void createSections(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		createGeneralSection(managedForm, toolkit);
		createUpdatesSection(managedForm, toolkit);
		createHelpAndDocumentation(managedForm, toolkit);
		createFeedsSection(managedForm, toolkit);
	}

	private void createUpdatesSection(IManagedForm managedForm, FormToolkit toolkit) {
		createGridSection(managedForm, Messages.DashboardEditor_Section_Updates, "", 1);
		// TODO put contents here

	}

	private void createHelpAndDocumentation(IManagedForm managedForm, FormToolkit toolkit) {
		createGridSection(managedForm, Messages.DashboardEditor_Section_HelpAndDocumentation, "", 1);
		// TODO put contents here

	}

	private void createFeedsSection(IManagedForm managedForm, FormToolkit toolkit) {
		createGridSection(managedForm, Messages.DashboardEditor_Section_Feeds, "", 1);
		// TODO put contents here

	}

	private void createGeneralSection(IManagedForm managedForm, FormToolkit toolkit) {
		Composite client = createGridSection(managedForm, Messages.DashboardEditor_Section_General,
				"", 1);

		linkSwitchPerspective(client, toolkit);
		linkImportGcLog(client, toolkit);
	}

	private ImageHyperlink linkSwitchPerspective(Composite client, FormToolkit toolkit) {
		ImageHyperlink link = toolkit.createImageHyperlink(client, SWT.NONE);
		link.setText(Messages.DashboardEditorPageOverview_5);
		link.setImage(Activator.getDefault().getImage("icons/new_document.gif"));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.openPerspective");
			}
		});
		return link;
	}

	private ImageHyperlink linkImportGcLog(Composite client, FormToolkit toolkit) {
		ImageHyperlink link = toolkit.createImageHyperlink(client, SWT.NONE);
		link.setText(Messages.DashboardEditorPageOverview_8);
		link.setImage(Activator.getDefault().getImage(Messages.DashboardEditorPageOverview_9));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.openImportDialog");
			}
		});
		return link;
	}

}