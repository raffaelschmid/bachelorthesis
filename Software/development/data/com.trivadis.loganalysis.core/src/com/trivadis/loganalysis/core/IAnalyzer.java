package com.trivadis.loganalysis.core;

import com.trivadis.loganalysis.core.common.progress.IProgress;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public interface IAnalyzer<T> {
	boolean canHandleLogFile(IFileDescriptor descriptor);
	T process(IFileDescriptor descriptor, IProgress progress);
	String getEditorId();
}
