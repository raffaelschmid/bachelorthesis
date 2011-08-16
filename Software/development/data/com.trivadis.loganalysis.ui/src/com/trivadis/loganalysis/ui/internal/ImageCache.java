package com.trivadis.loganalysis.ui.internal;

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
