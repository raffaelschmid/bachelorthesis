package com.trivadis.loganalysis.core;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IAnalyzer<T> {
	boolean isResponsible(ILogFileDescriptor content);
	T process(ILogFileDescriptor logFileDescriptor);
	String getEditorId();
}
