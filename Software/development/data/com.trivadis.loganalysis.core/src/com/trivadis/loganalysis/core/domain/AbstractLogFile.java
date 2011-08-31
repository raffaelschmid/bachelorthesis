package com.trivadis.loganalysis.core.domain;

public abstract class AbstractLogFile implements ILogFile {
	private final ILogFileDescriptor logFileDescriptor;
	
	public AbstractLogFile(final ILogFileDescriptor logFileDescriptor) {
		this.logFileDescriptor = logFileDescriptor;
	}

	public ILogFileDescriptor getLogFileDescriptor() {
		return logFileDescriptor;
	}
}
