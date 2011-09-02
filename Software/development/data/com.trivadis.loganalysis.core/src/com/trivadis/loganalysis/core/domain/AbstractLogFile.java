package com.trivadis.loganalysis.core.domain;

public abstract class AbstractLogFile implements IJvmRun {
	private final IFileDescriptor logFileDescriptor;
	
	public AbstractLogFile(final IFileDescriptor logFileDescriptor) {
		this.logFileDescriptor = logFileDescriptor;
	}

	public IFileDescriptor getDescriptor() {
		return logFileDescriptor;
	}
}
