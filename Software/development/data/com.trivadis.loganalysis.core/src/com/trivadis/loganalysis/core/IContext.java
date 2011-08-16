package com.trivadis.loganalysis.core;

import java.io.File;
import java.util.List;

public interface IContext {

	IFileImporter fileImporter();

	void add(File file);

	void remove(File file);

	List<File> getSelectedFiles();

	void addLogFilesChangeListener(SelectedFilesChangeListener listener);

}
