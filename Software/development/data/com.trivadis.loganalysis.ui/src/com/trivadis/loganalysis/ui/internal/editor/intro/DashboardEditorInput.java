package com.trivadis.loganalysis.ui.internal.editor.intro;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.trivadis.loganalysis.ui.internal.Activator;

public class DashboardEditorInput implements IEditorInput {

	public DashboardEditorInput() {
	}

	public ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("/icons/chart.png");
	}

	public String getName() {
		return "Dashboard";
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