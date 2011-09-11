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
package com.trivadis.loganalysis.jrockit.analyzer;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;

public class DummyDescriptor implements IFileDescriptor {

	private final List<String> logs;

	public DummyDescriptor(String... logs) {
		this.logs = Arrays.asList(logs);
	}

	public String getPath() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public String getFileName() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public String getAbsolutePath() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public File toFile() {
		throw new UnsupportedOperationException(
				"descriptors method should not be used");
	}

	public List<String> getContent(IContentReader reader) {
		return this.logs;
	}

}
