package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IContext {

	IFileImporter fileImporter();

	void add(ILogFileDescriptor file);

	void remove(ILogFileDescriptor file);

	List<ILogFileDescriptor> getSelectedFiles();

	void addLogFilesChangeListener(SelectedFilesChangeListener listener);

	IContentReader contentReader();

}
