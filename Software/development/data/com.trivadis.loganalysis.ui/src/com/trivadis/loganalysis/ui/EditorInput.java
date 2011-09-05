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
