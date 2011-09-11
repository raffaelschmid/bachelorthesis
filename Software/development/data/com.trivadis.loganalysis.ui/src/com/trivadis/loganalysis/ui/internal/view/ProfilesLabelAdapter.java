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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.trivadis.loganalysis.ui.domain.profile.IExtension;
import com.trivadis.loganalysis.ui.domain.profile.IProfile;
import com.trivadis.loganalysis.ui.internal.Activator;

public class ProfilesLabelAdapter extends LabelProvider {

	@Override
	public Image getImage(final Object obj) {
		return Activator.getDefault().getImageDescriptor("icons/profile.gif").createImage();
	}

	@Override
	public String getText(final Object obj) {
		String retVal;
		if (obj instanceof IExtension) {
			retVal = ((IExtension) obj).getLabel();
		} else if (obj instanceof IProfile) {
			retVal = ((IProfile) obj).getLabel();
		} else {
			retVal = getText(obj);
		}
		return retVal;
	}
}