package com.trivadis.loganalysis.core.domain;

public abstract class AbstractLog implements ILog {
	private final ILogFileDescriptor logFileDescriptor;
	
	public AbstractLog(final ILogFileDescriptor logFileDescriptor) {
		this.logFileDescriptor = logFileDescriptor;
	}

	public ILogFileDescriptor getLogFileDescriptor() {
		return logFileDescriptor;
	}
}
