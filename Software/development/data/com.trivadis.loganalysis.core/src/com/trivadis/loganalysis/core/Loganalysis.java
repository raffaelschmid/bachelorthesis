package com.trivadis.loganalysis.core;

import org.eclipse.core.runtime.Assert;

import com.trivadis.loganalysis.core.internal.Context;
import com.trivadis.loganalysis.core.process.IFileProcessor;

public class Loganalysis {

	private static class Holder {
		private static IContext INSTANCE = new Context();
	}

	public static IFileProcessor importProcessor() {
		IFileProcessor importProcessor = Holder.INSTANCE.getImportProcessor();
		Assert.isNotNull(importProcessor);
		return importProcessor;
	}

	public static IContext getContext() {
		Assert.isNotNull(Holder.INSTANCE);
		return Holder.INSTANCE;
	}
}
