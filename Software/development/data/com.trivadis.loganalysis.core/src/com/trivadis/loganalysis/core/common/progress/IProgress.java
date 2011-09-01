package com.trivadis.loganalysis.core.common.progress;

public interface IProgress {

	void subTask(String subTask);

	void worked(int progress);

	void beginTask(int totalWork);

	void done();

	String getTaskName();

	
	

}
