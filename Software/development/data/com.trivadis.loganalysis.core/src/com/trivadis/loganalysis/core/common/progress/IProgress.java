package com.trivadis.loganalysis.core.common.progress;

public interface IProgress {

	void subTask(String subTask);

	void worked(int progress);

	void setTaskName(String taskName);

	void beginTask(String name, int totalWork);

	void done();
	
	

}
