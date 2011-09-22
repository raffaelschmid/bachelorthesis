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
package com.trivadis.loganalysis.ui.internal.editor.intro;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import com.trivadis.loganalysis.ui.GridFormPage;
import com.trivadis.loganalysis.ui.Help;
import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;
import com.trivadis.loganalysis.ui.internal.Messages;

public class DashboardEditorPageOverview extends GridFormPage {

	private static final String HELP_ABOUT = "/com.trivadis.loganalysis.help/html/about.html";
	private static final String HELP_OVERVIEW = "/com.trivadis.loganalysis.help/html/overview.html";
	private static final String HELP_GETTING_STARTED = "/com.trivadis.loganalysis.help/html/gettingstarted/openanalysis.html";
	public static final String ID = DashboardEditorPageOverview.class.getName();

	public DashboardEditorPageOverview(final DashboardEditor editor) {
		super(editor, ID, Messages.DashboardEditor_Title, 2, 1);
	}

	@Override
	protected void createSections(final IManagedForm managedForm) {
		final FormToolkit toolkit = managedForm.getToolkit();
		createAnalysisSection(managedForm, toolkit);
		createProfileSection(managedForm, toolkit);
		createHelpAndDocumentation(managedForm, toolkit);
	}

	protected void createHelpLink(final Composite client, final FormToolkit toolkit, final String text,
			final String resource) {
		createLink(client, toolkit, text, Activator.getDefault().getImage("icons/help.gif"), new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Help.getDefault().displayHelpResource(resource);
			}
		});
	}

	private void createHelpAndDocumentation(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite client = createGridSection(managedForm, Messages.DashboardEditor_Section_HelpAndDocumentation,
				"", 1, true);
		createHelpLink(client, managedForm.getToolkit(), "Overview", HELP_OVERVIEW);
		createHelpLink(client, managedForm.getToolkit(), "Getting Started", HELP_GETTING_STARTED);
		createHelpLink(client, managedForm.getToolkit(), "About", HELP_ABOUT);
	}

	private void createAnalysisSection(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite client = createGridSection(managedForm, Messages.DashboardEditor_Section_General, "", 1, true);

		linkSwitchPerspective(client, toolkit);
		linkImportGcLog(client, toolkit);
	}

	private void createProfileSection(final IManagedForm managedForm, final FormToolkit toolkit) {
		final Composite client = createGridSection(managedForm, "Profile", "", 1, true);
		linkImportProfile(client, toolkit);
		linkExportProfile(client, toolkit);
	}

	private ImageHyperlink linkSwitchPerspective(final Composite client, final FormToolkit toolkit) {
		return createLink(client, toolkit, Messages.DashboardEditorPageOverview_5,
				Activator.getDefault().getImage("icons/switch_perspective.gif"), new HyperlinkAdapter() {
					@Override
					public void linkActivated(final HyperlinkEvent e) {
						Command.execute("com.trivadis.loganalysis.ui.openPerspective");
					}
				});
	}

	protected ImageHyperlink createLink(final Composite client, final FormToolkit toolkit, final String text,
			final Image image, final HyperlinkAdapter listener) {
		final ImageHyperlink link = toolkit.createImageHyperlink(client, SWT.NONE);
		link.setText(text);
		link.setImage(image);
		link.addHyperlinkListener(listener);
		return link;
	}

	private ImageHyperlink linkImportGcLog(final Composite client, final FormToolkit toolkit) {
		final ImageHyperlink link = toolkit.createImageHyperlink(client, SWT.NONE);
		link.setText(Messages.DashboardEditorPageOverview_8);
		link.setImage(Activator.getDefault().getImage("icons/import.gif"));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.openImportDialog");
			}
		});
		return link;
	}

	private ImageHyperlink linkImportProfile(final Composite client, final FormToolkit toolkit) {
		final ImageHyperlink link = toolkit.createImageHyperlink(client, SWT.NONE);
		link.setText("Import Analysis Profile");
		link.setImage(Activator.getDefault().getImage("icons/import.gif"));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.importProfile");
			}
		});
		return link;
	}

	private ImageHyperlink linkExportProfile(final Composite client, final FormToolkit toolkit) {
		final ImageHyperlink link = toolkit.createImageHyperlink(client, SWT.NONE);
		link.setText("Export Analysis Profile");
		link.setImage(Activator.getDefault().getImage("icons/import.gif"));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.exportProfile");
			}
		});
		return link;
	}

}