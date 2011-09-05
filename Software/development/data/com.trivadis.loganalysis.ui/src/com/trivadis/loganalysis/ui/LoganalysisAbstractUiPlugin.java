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
package com.trivadis.loganalysis.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.trivadis.loganalysis.core.common.Cache;
import com.trivadis.loganalysis.ui.internal.ImageCache;

public abstract class LoganalysisAbstractUiPlugin extends AbstractUIPlugin {

	private static LoganalysisAbstractUiPlugin plugin;
	private final Cache<ImageDescriptor, Image> cache = new ImageCache();

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		cache.dispose();
		plugin = null;
		super.stop(context);
	}

	public static LoganalysisAbstractUiPlugin getDefault() {
		return plugin;
	}

	public Cache<ImageDescriptor, Image> getCache() {
		return cache;
	}

	public Image getImage(ImageDescriptor imageDescriptor) {
		return getDefault().cache.get(imageDescriptor);
	}

	public Image getImage(String path) {
		return getDefault().cache.get(getImageDescriptor(path));
	}

	public ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(getPluginId(), path);
	}

	public abstract String getPluginId();
}
