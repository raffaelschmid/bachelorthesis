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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class DeleteProfileHandler extends AbstractHandler {

	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selected = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (!selected.isEmpty() && selected instanceof IStructuredSelection) {
			final Object selectedObject = ((IStructuredSelection) selected)
					.getFirstElement();
			if (selectedObject instanceof IProfile) {
				final IProfile profile = (IProfile) selectedObject;
				profile.getConfiguration().removeProfile(profile);
			}
		}
		return null;
	}


}
