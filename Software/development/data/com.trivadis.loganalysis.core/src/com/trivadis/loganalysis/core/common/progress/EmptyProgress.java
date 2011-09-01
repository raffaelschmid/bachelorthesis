package com.trivadis.loganalysis.core.common.progress;

/**
 * Do not update a progress monitor.
 * @author els
 */
public class EmptyProgress implements IProgress{

	public void subTask(String subTask) {
		
	}

	public void worked(int progress) {
		
	}

	public void beginTask(int totalWork) {
		
	}

	public void done() {
		
	}

	public String getTaskName() {
		return null;
	}

	

}
