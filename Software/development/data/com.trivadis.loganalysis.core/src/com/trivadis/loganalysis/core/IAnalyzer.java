package com.trivadis.loganalysis.core;

import com.trivadis.loganalysis.core.common.progress.IProgress;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IAnalyzer<T> {
	boolean canHandleLogFile(ILogFileDescriptor descriptor);
	T process(ILogFileDescriptor descriptor, IProgress progress);
	String getEditorId();
}
