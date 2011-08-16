package com.trivadis.loganalysis.core.internal;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.IFileImporter;

public class FileImporter implements IFileImporter {

	private final IContext context;

	public FileImporter(IContext context) {
		Assert.isNotNull(context);
		this.context = context;
	}

	public void importFiles(List<File> files) {
		for (File file : files) {
			importFile(file);
		}
	}

	public void importFile(File file) {
		context.add(file);
	}

}
