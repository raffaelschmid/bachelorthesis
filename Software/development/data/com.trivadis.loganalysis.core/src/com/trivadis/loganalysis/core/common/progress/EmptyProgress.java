package com.trivadis.loganalysis.core.common.progress;

/**
 * Do not update a progress monitor.
 * @author els
 *
 */
public class EmptyProgress implements IProgress{

	public void subTask(String message) {
	
	}

	public void worked(int progress) {
	
	}

	public void setTaskName(String taskName) {
		
	}

	public void beginTask(String name, int totalWork) {
		
	}

	public void done() {
		
	}

}
