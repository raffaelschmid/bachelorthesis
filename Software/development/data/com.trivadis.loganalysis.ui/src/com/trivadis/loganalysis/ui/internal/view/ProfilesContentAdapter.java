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
package com.trivadis.loganalysis.ui.internal.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.trivadis.loganalysis.ui.UiLoganalysis;
import com.trivadis.loganalysis.ui.domain.profile.IExtension;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;

public class ProfilesContentAdapter implements ITreeContentProvider {

	private final boolean showProfiles;
	private static final Object[] EMPTY_ARRAY = new Object[] {};

	public ProfilesContentAdapter(final boolean showProfiles) {
		this.showProfiles = showProfiles;
	}

	public void dispose() {
	}

	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

	public Object[] getElements(final Object inputElement) {
		return UiLoganalysis.getUiContext().getProfiles().toArray();
	}

	public Object[] getChildren(final Object parentElement) {
		Object[] retVal = EMPTY_ARRAY;
		if (parentElement instanceof IExtension) {
			final IExtension box = (IExtension) parentElement;
			retVal = box.getProfiles().toArray();
		}
		return retVal;
	}

	public Object getParent(final Object element) {
		return (element instanceof IProfile) ? ((IProfile) element).getConfiguration() : null;
	}

	public boolean hasChildren(final Object element) {
		return showProfiles
				&& (element != null && element instanceof IExtension && ((IExtension) element).getProfiles()
						.size() > 0);
	}
}