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
package com.trivadis.loganalysis.ui.common;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.trivadis.loganalysis.core.common.Cache;

public class ImageCache extends Cache<ImageDescriptor, Image>{

	@Override
	public Image create(ImageDescriptor k) {
		return k.createImage();
	}

	@Override
	public void dispose(Image v) {
		v.dispose();
	}

}
