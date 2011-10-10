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
package com.trivadis.loganalysis.core.internal;

import static com.trivadis.loganalysis.core.common.CollectionUtil.findFirst;

import com.trivadis.loganalysis.core.ExtensionPoint;
import com.trivadis.loganalysis.core.IParser;
import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.IContext;
import com.trivadis.loganalysis.core.common.ExtensionUtil;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.IJvmRun;

public class Context implements IContext {

	private static final String ELEMENT_NAME = "analyzer";

	private final IContentReader contentReader;

	public Context() {
		this.contentReader = new ContentReader();
	}

	public IContentReader getContentReader() {
		return this.contentReader;
	}

	public IParser<IJvmRun> findAnalyzer(final IFileDescriptor fileDescriptor) {
		return findFirst(
				ExtensionUtil.<IParser<IJvmRun>> findExtensionInstances(ExtensionPoint.ANALYZER, ELEMENT_NAME),
				new Predicate<IParser<IJvmRun>>() {
					public boolean matches(final IParser<IJvmRun> item) {
						return item.canHandleLogFile(fileDescriptor);
					}
				});
	}
}
