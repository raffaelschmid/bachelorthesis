package com.trivadis.loganalysis.core.domain;

public abstract class AbstractLogFile implements ILogFile {
	private final IFileDescriptor logFileDescriptor;
	
	public AbstractLogFile(final IFileDescriptor logFileDescriptor) {
		this.logFileDescriptor = logFileDescriptor;
	}

	public IFileDescriptor getLogFileDescriptor() {
		return logFileDescriptor;
	}
}
