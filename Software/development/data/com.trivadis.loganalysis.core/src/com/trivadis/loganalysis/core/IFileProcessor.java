package com.trivadis.loganalysis.core;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IFileProcessor<T> {
	boolean isResponsible(String content);
	T process(ILogFileDescriptor logFileDescriptor, String content);
}
