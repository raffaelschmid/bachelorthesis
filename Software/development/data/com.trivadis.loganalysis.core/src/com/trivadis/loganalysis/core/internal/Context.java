package com.trivadis.loganalysis.core.internal;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.IFileImporter;
import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

/**
 * TODO refactor into smaller units.
 * 
 * @author els
 * 
 */
public class Context implements IContext {
	private final IFileImporter fileImporter;
	private final List<ILogFileDescriptor> selectedFiles;
	private final List<SelectedFilesChangeListener> listeners = new ArrayList<SelectedFilesChangeListener>();
	private IContentReader contentReader;

	public Context() {
		this.fileImporter = new FileImporter(this);
		this.selectedFiles = new ArrayList<ILogFileDescriptor>();
		this.contentReader = new ContentReader();
	}

	public IFileImporter fileImporter() {
		return fileImporter;
	}

	public List<ILogFileDescriptor> getSelectedFiles() {
		return selectedFiles;
	}

	public void addLogFilesChangeListener(SelectedFilesChangeListener listener) {
		listeners.add(listener);
	}

	public void add(ILogFileDescriptor file) {
		if (!selectedFiles.contains(file)) {
			selectedFiles.add(file);
			notifyListeners();
		}
		Assert.assertTrue(selectedFiles.contains(file));
	}

	public void remove(ILogFileDescriptor file) {
		if (selectedFiles.contains(file)) {
			selectedFiles.remove(file);
			notifyListeners();
		}
		Assert.assertTrue(!selectedFiles.contains(file));
	}

	private void notifyListeners() {
		for (SelectedFilesChangeListener listener : listeners) {
			listener.fileSelectionChanged();
		}
	}

	public IContentReader contentReader() {
		return this.contentReader;
	}

}
