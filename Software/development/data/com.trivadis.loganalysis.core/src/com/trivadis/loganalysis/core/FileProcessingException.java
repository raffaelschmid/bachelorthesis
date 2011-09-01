package com.trivadis.loganalysis.core;

import org.eclipse.core.runtime.CoreException;

public class FileProcessingException extends Exception {

	private static final long serialVersionUID = 1L;
	public FileProcessingException(CoreException cause) {
		super(cause);
	}
}
