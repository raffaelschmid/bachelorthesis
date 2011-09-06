package com.trivadis.loganalysis.ui;

import java.util.List;

import com.trivadis.loganalysis.core.SelectedFilesChangeListener;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.ui.domain.IProfile;

public interface IUiContext {
	List<IFileDescriptor> getSelectedFiles();

	void addSelectedFile(IFileDescriptor file);

	void remove(IFileDescriptor file);

	List<IProfile> getProfiles();

	void addProfile(IProfile profile);

	void addLogFilesChangeListener(SelectedFilesChangeListener listener);

}
