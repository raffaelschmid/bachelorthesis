package com.trivadis.loganalysis.core;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.trivadis.loganalysis.core.domain.ILogFile;
import com.trivadis.loganalysis.core.domain.ILogFileDescriptor;
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
		Assert.isNotNull(fileImporter);
		return fileImporter;
	}

	public static IContext getContext() {
		Assert.isNotNull(Holder.INSTANCE);
		return Holder.INSTANCE;
	}

	public static IContentReader contentReader() {
		IContentReader contentReader = Holder.INSTANCE.contentReader();
		Assert.isNotNull(contentReader);
		return contentReader;
	}

	public static IAnalyzer<ILogFile> fileProcessor() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		ILogFileDescriptor fileDescriptor = null;
		for (IConfigurationElement element : elements) {
			if (ELEMENT_NAME.equals(element.getName())) {
				try {
					@SuppressWarnings("unchecked")
					IAnalyzer<ILogFile> processor = (IAnalyzer<ILogFile>) element
							.createExecutableExtension("class");
					if (processor.isResponsible(fileDescriptor))
						return processor;
				} catch (CoreException e) {
					// TODO refactor exception handling
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}

}
