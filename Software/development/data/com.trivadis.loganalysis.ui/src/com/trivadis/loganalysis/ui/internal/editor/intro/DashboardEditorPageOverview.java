package com.trivadis.loganalysis.ui.internal.editor.intro;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.trivadis.loganalysis.ui.internal.Activator;
import com.trivadis.loganalysis.ui.internal.Command;

public class DashboardEditorPageOverview extends FormPage {

	public static final String ID = DashboardEditorPageOverview.class.getName();

	private static final Integer[] HEIGHTS = new Integer[] { 120, 120, 120 };
	private static final int WIDTH = 350;
	private static final int TOP = 20;

	public DashboardEditorPageOverview(DashboardEditor editor) {
		super(editor, ID, "Overview");
	}

	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		sectionCreate(managedForm, toolkit);
		sectionUpdates(managedForm, toolkit);
		sectionHelpAndDocumentation(managedForm, toolkit);
		sectionFeeds(managedForm, toolkit);
	}

	private void sectionFeeds(IManagedForm managedForm, FormToolkit toolkit) {
		Section section = toolkit.createSection(
				managedForm.getForm().getBody(), Section.TWISTIE
						| Section.TITLE_BAR);
		section.setBounds(calculateSectionSize(370, TOP, WIDTH, 0));
		toolkit.paintBordersFor(section);
		section.setText("Help and Documentation");
		ScrolledForm form = toolkit.createScrolledForm(section);
		toolkit.paintBordersFor(form);
		section.setClient(form);
		section.setExpanded(true);
	}

	private void sectionHelpAndDocumentation(IManagedForm managedForm,
			FormToolkit toolkit) {
		Section section = toolkit.createSection(
				managedForm.getForm().getBody(), Section.TWISTIE
						| Section.TITLE_BAR);
		section.setBounds(calculateSectionSize(10, TOP, WIDTH, 2));
		toolkit.paintBordersFor(section);
		section.setText("Help and Documentation");
		ScrolledForm form = toolkit.createScrolledForm(section);
		toolkit.paintBordersFor(form);
		section.setClient(form);
		section.setExpanded(true);
	}

	private void sectionUpdates(IManagedForm managedForm, FormToolkit toolkit) {
		Section section = toolkit.createSection(
				managedForm.getForm().getBody(), Section.TWISTIE
						| Section.TITLE_BAR);
		section.setBounds(calculateSectionSize(10, TOP, WIDTH, 1));
		toolkit.paintBordersFor(section);
		section.setText("Updates");
		ScrolledForm form = toolkit.createScrolledForm(section);
		toolkit.paintBordersFor(form);
		section.setClient(form);
		section.setExpanded(true);
	}

	private void sectionCreate(IManagedForm managedForm, FormToolkit toolkit) {
		Section section = toolkit.createSection(
				managedForm.getForm().getBody(), Section.TWISTIE
						| Section.TITLE_BAR);
		section.setBounds(calculateSectionSize(10, TOP, WIDTH, 0));
		toolkit.paintBordersFor(section);
		section.setText("General");
		ScrolledForm form = toolkit.createScrolledForm(section);
		form.setLayout(new FillLayout(SWT.VERTICAL));
		toolkit.paintBordersFor(form);
		section.setClient(form);

		linkSwitchPerspective(toolkit, form);
		linkImportGcLog(toolkit, form);

		section.setExpanded(true);
	}

	private Rectangle calculateSectionSize(int left, int top, int width,
			int index) {
		int position = top;
		for (int i = 0; i < index; i++) {
			position += HEIGHTS[i];
		}
		return new Rectangle(left, position, width, HEIGHTS[index]);
	}

	private ImageHyperlink linkSwitchPerspective(FormToolkit toolkit,
			ScrolledForm form) {
		ImageHyperlink link = toolkit.createImageHyperlink(form.getBody(),
				SWT.NONE);
		link.setBounds(calculateLinkSize(0, 300, 20));
		link.setText("Switch to Garbage Collection Analysis Perspective");
		link.setImage(Activator.getDefault().getImage("icons/new_document.gif"));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.openPerspective");
			}
		});
		toolkit.paintBordersFor(link);
		return link;
	}

	private ImageHyperlink linkImportGcLog(FormToolkit toolkit,
			ScrolledForm form) {
		ImageHyperlink link = toolkit.createImageHyperlink(form.getBody(),
				SWT.NONE);
		link.setBounds(calculateLinkSize(1, 300, 20));
		link.setText("Import Garbage Collection Log File");
		link.setImage(Activator.getDefault().getImage("icons/chart.gif"));
		link.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				Command.execute("com.trivadis.loganalysis.ui.openImportDialog");
			}
		});
		toolkit.paintBordersFor(link);
		return link;
	}

	private Rectangle calculateLinkSize(int index, int width, int height) {
		int top = 10;
		int x = 10;
		int y = top;
		for (int i = 0; i < index; i++) {
			y += height;
		}
		Rectangle rectangle = new Rectangle(x, y, width, height);
		return rectangle;
	}

	public DashboardEditor getEditor() {
		return (DashboardEditor) super.getEditor();
	}
}