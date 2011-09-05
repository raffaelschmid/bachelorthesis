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
package com.trivadis.loganalysis.core.common.progress;

import org.eclipse.core.runtime.IProgressMonitor;

import com.trivadis.loganalysis.core.common.Assert;

public class Progress implements IProgress {
	private IProgressMonitor monitor;
	private String taskName = null;

	public Progress(IProgressMonitor monitor, String taskName) {
		this.monitor = monitor;
		Assert.assertNotNull(this.monitor);
		this.taskName = taskName;
		this.monitor.setTaskName(taskName);
	}

	public Progress(IProgressMonitor progress) {
		this.monitor = progress;
	}

	public void subTask(String subTask) {
		monitor.subTask(subTask);
	}

	public void worked(int progress) {
		monitor.worked(progress);
	}

	public void beginTask(int totalWork){
		monitor.beginTask(taskName, totalWork);
	}
	
	public void done() {
		monitor.done();
	}

	public String getTaskName() {
		return taskName;
	}

}
