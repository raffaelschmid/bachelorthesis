package com.trivadis.loganalysis.core;

import java.util.List;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public interface IFileImporter {

	void importFiles(List<IFileDescriptor> selectedFiles);
	
//	void deleteFile();

}
