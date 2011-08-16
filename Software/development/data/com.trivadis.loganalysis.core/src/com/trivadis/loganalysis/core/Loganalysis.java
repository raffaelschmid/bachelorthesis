package com.trivadis.loganalysis.core;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.trivadis.loganalysis.core.domain.ILogFile;
import com.trivadis.loganalysis.core.internal.Context;

public class Loganalysis {

	private static final String ELEMENT_NAME = "analyzer";

	private static class Holder {
		private static IContext INSTANCE = new Context();
	}

	public static IFileImporter fileImporter() {
		IFileImporter fileImporter = Holder.INSTANCE.fileImporter();
		Assert.isNotNull(fileImporter);
		return fileImporter;
	}

	private static final String EXTENSION_POINT_ID = "com.trivadis.loganalysis.analyzer";

	public static IFileProcessor<? extends ILogFile> fileProcessor() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (IConfigurationElement element : elements) {
			if (ELEMENT_NAME.equals(element.getName())) {
				try {
					@SuppressWarnings("unchecked")
					IFileProcessor<? extends ILogFile> processor = (IFileProcessor<? extends ILogFile>) element
							.createExecutableExtension("class");
					if (processor.isResponsible("JRockit Log File"))
						return processor;
				} catch (CoreException e) {
					// TODO refactor exception handling
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}

	public static IContext getContext() {
		Assert.isNotNull(Holder.INSTANCE);
		return Holder.INSTANCE;
	}
}
