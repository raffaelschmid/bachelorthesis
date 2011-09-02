package com.trivadis.loganalysis.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.trivadis.loganalysis.core.common.Assert;
import com.trivadis.loganalysis.core.domain.ILogFile;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.exception.FileProcessingException;
import com.trivadis.loganalysis.core.internal.Context;

/**
 * TODO refactor into smaller units
 * 
 * @author els
 * 
 */
public class Loganalysis {

	private static final String ELEMENT_NAME = "analyzer";

	private static final String EXTENSION_POINT_ID = "com.trivadis.loganalysis.analyzer";

	private static class Holder {
		private static IContext INSTANCE = new Context();
	}

	public static IFileImporter fileImporter() {
		IFileImporter fileImporter = Holder.INSTANCE.fileImporter();
		Assert.assertNotNull(fileImporter);
		return fileImporter;
	}

	public static IContext getContext() {
		Assert.assertNotNull(Holder.INSTANCE);
		return Holder.INSTANCE;
	}

	public static IContentReader contentReader() {
		IContentReader contentReader = Holder.INSTANCE.contentReader();
		Assert.assertNotNull(contentReader);
		return contentReader;
	}

	public static IAnalyzer<ILogFile> fileProcessor(IFileDescriptor fileDescriptor)
			throws FileProcessingException {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (IConfigurationElement element : elements) {
			if (ELEMENT_NAME.equals(element.getName())) {
				try {
					@SuppressWarnings("unchecked")
					IAnalyzer<ILogFile> processor = (IAnalyzer<ILogFile>) element
							.createExecutableExtension("class");
					if (processor.canHandleLogFile(fileDescriptor))
						return processor;
				} catch (CoreException e) {
					throw new FileProcessingException(e);
				}
			}
		}

		return null;
	}

}
