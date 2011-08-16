package com.trivadis.loganalysis.core;

import java.io.File;
import java.util.List;

import com.trivadis.loganalysis.core.process.IFileProcessor;

public interface IContext {

	IFileProcessor getImportProcessor();

	void add(File file);

	void remove(File file);

	List<File> getSelectedFiles();

	void addLogFilesChangeListener(SelectedFilesChangeListener listener);

}
