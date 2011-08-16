package com.trivadis.loganalysis.core.internal.process;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.process.IFileProcessor;

public class FileProcessor implements IFileProcessor {

	private final IContext context;

	public FileProcessor(IContext context) {
		Assert.isNotNull(context);
		this.context = context;
	}

	public void processFiles(List<File> files) {
		for (File file : files) {
			processFile(file);
		}
	}

	public void processFile(File file) {
		context.add(file);
	}

}
