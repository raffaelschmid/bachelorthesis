package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
import com.trivadis.loganalysis.ui.internal.Activator;

public class AnalysisEditorInput implements IEditorInput {
	
	private final ILogFileDescriptor logFile;

	public AnalysisEditorInput(ILogFileDescriptor logFile) {
		this.logFile = logFile;
	}

	public ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("/icons/chart.png");
	}

	public String getName() {
		return logFile.toString();
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

}
