/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.core;

import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;

import com.trivadis.loganalysis.core.common.ExtensionUtil;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.core.internal.Context;

public class Loganalysis implements ILoganalysis {

	private static final String ELEMENT_NAME = "analyzer";

	private static class Holder {
		private static ILoganalysis INSTANCE = new Loganalysis(new Context());
	}

	public static ILoganalysis getDefault() {
		return Holder.INSTANCE;
	}

	private final Context context;

	public Loganalysis(final Context context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.trivadis.loganalysis.core.ILoganalysis#getContext()
	 */
	public IContext getContext() {
		return context;
	}

	/* (non-Javadoc)
	 * @see com.trivadis.loganalysis.core.ILoganalysis#contentReader()
	 */
	public IContentReader contentReader() {
		return context.getContentReader();
	}

	/* (non-Javadoc)
	 * @see com.trivadis.loganalysis.core.ILoganalysis#fileProcessor(com.trivadis.loganalysis.core.domain.IFileDescriptor)
	 */
	public IAnalyzer<IJvmRun> fileProcessor(final IFileDescriptor fileDescriptor) {
		return findFirst(
				ExtensionUtil.<IAnalyzer<IJvmRun>> findExtensionInstances(ExtensionPoint.ANALYZER, ELEMENT_NAME),
				new Predicate<IAnalyzer<IJvmRun>>() {
					public boolean matches(final IAnalyzer<IJvmRun> item) {
						return item.canHandleLogFile(fileDescriptor);
					}
				});
	}

}
