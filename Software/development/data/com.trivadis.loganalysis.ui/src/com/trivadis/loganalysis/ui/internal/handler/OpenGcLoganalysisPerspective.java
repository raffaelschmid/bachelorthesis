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
import com.trivadis.loganalysis.ui.internal.perspective.GarbageCollectionAnalysisPerspective;

public class OpenGcLoganalysisPerspective extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Perspective.update(GarbageCollectionAnalysisPerspective.ID);
		return null;
	}
}
