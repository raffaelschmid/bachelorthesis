package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;

public interface IFileImporter {

	void importFiles(List<ILogFileDescriptor> selectedFiles);
	
//	void deleteFile();

}
