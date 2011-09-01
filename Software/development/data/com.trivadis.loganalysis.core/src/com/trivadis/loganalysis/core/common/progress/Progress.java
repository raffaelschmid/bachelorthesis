package com.trivadis.loganalysis.core.common.progress;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

public class Progress implements IProgress {
	private IProgressMonitor monitor;

	public Progress(IProgressMonitor monitor, String taskName) {
		this.monitor = monitor;
		Assert.isNotNull(this.monitor);
		this.monitor.setTaskName(taskName);
		this.monitor.beginTask(taskName, 100);
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

	public void setTaskName(String taskName) {
		monitor.setTaskName(taskName);
	}

	public void beginTask(String name, int totalWork) {
		monitor.beginTask(name, totalWork);
	}

	public void done() {
		monitor.done();
	}
	
	

}
