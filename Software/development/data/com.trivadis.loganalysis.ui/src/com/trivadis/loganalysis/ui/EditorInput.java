package com.trivadis.loganalysis.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.ui.internal.Activator;

public class EditorInput implements IEditorInput {

	private final IJvmRun logFile;

	public EditorInput(IJvmRun logFile) {
		this.logFile = logFile;
	}

	public ImageDescriptor getImageDescriptor() {
		return Activator.getDefault().getImageDescriptor("/icons/chart.png");
	}

	public String getName() {
		return getLogFile().toString();
	}

	public String getToolTipText() {
		return getName();
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public boolean exists() {
		return true;
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapterTarget) {
		return null;
	}

	public IJvmRun getLogFile() {
		return logFile;
	}


}
