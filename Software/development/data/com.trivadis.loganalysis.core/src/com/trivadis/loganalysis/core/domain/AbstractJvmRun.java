package com.trivadis.loganalysis.core.domain;

public abstract class AbstractJvmRun implements IJvmRun {
	private final IFileDescriptor logFileDescriptor;
	
	public AbstractJvmRun(final IFileDescriptor logFileDescriptor) {
		this.logFileDescriptor = logFileDescriptor;
	}

	public IFileDescriptor getDescriptor() {
		return logFileDescriptor;
	}
}
