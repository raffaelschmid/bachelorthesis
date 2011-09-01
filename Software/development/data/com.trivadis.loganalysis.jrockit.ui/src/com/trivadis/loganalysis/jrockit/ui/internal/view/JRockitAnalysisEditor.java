package com.trivadis.loganalysis.jrockit.ui.internal.view;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.trivadis.loganalysis.jrockit.domain.JRockitLog;
import com.trivadis.loganalysis.jrockit.ui.internal.view.heapusage.PageHeapUsageAfterGC;
import com.trivadis.loganalysis.jrockit.ui.internal.view.overview.PageOverview;
import com.trivadis.loganalysis.ui.EditorInput;

public class JRockitAnalysisEditor extends MultiPageEditorPart implements
		IResourceChangeListener {

	private JRockitAnalysisEditorData data = new JRockitAnalysisEditorData();
	private JRockitLog logFile;

	public JRockitAnalysisEditor() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	protected void createPages() {
		setPageText(addPage(new PageHeapUsageAfterGC(getContainer(), SWT.NONE,logFile)), "Heap Usage after GC");
		setPageText(addPage(new PageOverview(getContainer(), SWT.NONE)), "Overview");
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof EditorInput))
			throw new PartInitException(
					"Invalid Input: Must be AnalysisEditorInput");
		EditorInput input = (EditorInput) editorInput;
		if(!(input.getLogFile() instanceof JRockitLog))
			throw new PartInitException("Invalid Log File Input");
		this.logFile = (JRockitLog)input.getLogFile();
		super.init(site, editorInput);
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
							.getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) data.editor.getEditorInput())
								.getFile().getProject()
								.equals(event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(data.editor
									.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

}
