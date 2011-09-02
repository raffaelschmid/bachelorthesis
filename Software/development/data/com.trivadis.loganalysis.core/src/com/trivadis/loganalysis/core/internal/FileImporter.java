package com.trivadis.loganalysis.core.internal;

import java.util.List;

import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.IFileImporter;
import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public class FileImporter implements IFileImporter {

	private final IContext context;

	public FileImporter(IContext context) {
		Assert.assertNotNull(context);
		this.context = context;
	}

	public void importFiles(List<IFileDescriptor> files) {
		for (IFileDescriptor file : files) {
			importFile(file);
		}
	}

	public void importFile(IFileDescriptor file) {
		context.add(file);
	}

}
