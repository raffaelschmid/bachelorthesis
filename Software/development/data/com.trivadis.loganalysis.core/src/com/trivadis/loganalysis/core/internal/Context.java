package com.trivadis.loganalysis.core.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.core.internal.process.FileProcessor;
import com.trivadis.loganalysis.core.process.IFileProcessor;

/**
 * TODO refactor into smaller units.
 * 
 * @author els
 * 
 */
public class Context implements IContext {
	private final IFileProcessor importProcessor;

	private final List<File> selectedFiles;
	private final List<SelectedFilesChangeListener> listeners = new ArrayList<SelectedFilesChangeListener>();

	public Context() {
		this.importProcessor = new FileProcessor(this);
		this.selectedFiles = new ArrayList<File>();
	}

	public IFileProcessor getImportProcessor() {
		return importProcessor;
	}

	public List<File> getSelectedFiles() {
		return selectedFiles;
	}

	public void addLogFilesChangeListener(SelectedFilesChangeListener listener) {
		listeners.add(listener);
	}

	public void add(File file) {
		if (!selectedFiles.contains(file)) {
			selectedFiles.add(file);
			notifyListeners();
		}
		Assert.isTrue(selectedFiles.contains(file));
	}

	public void remove(File file) {
		if (selectedFiles.contains(file)) {
			selectedFiles.remove(file);
			notifyListeners();
		}
		Assert.isTrue(!selectedFiles.contains(file));
	}

	private void notifyListeners() {
		for (SelectedFilesChangeListener listener : listeners) {
			listener.fileSelectionChanged();
		}
	}
}
