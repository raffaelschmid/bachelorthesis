package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public interface IContext {

	IFileImporter fileImporter();

	void add(IFileDescriptor file);

	void remove(IFileDescriptor file);

	List<IFileDescriptor> getSelectedFiles();

	void addLogFilesChangeListener(SelectedFilesChangeListener listener);

	IContentReader contentReader();

}
