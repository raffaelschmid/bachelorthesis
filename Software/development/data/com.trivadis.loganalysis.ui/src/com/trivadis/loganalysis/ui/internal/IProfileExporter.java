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
package com.trivadis.loganalysis.ui.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public interface IProfileExporter {

	void export(List<IProfile> profiles, String fileName) throws IOException;

	void export(List<IProfile> profiles, Writer sw) throws IOException;

}
