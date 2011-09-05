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
package com.trivadis.loganalysis.ui.internal.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.trivadis.loganalysis.ui.internal.Perspective;

public class OpenGcLoganalysisPerspective extends AbstractHandler {

	public static final String PERSPECTIVE_ID = "com.trivadis.loganalysis.ui.inernal.perspective.GarbageCollectionAnalysisPerspective";

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Perspective.update(PERSPECTIVE_ID);
		return null;
	}
}
