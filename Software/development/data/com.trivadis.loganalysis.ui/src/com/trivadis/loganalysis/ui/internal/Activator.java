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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.trivadis.loganalysis.core.common.Cache;
import com.trivadis.loganalysis.ui.common.ImageCache;

public class Activator extends AbstractUIPlugin {

	private static final String PLUGIN_ID = "com.trivadis.loganalysis.ui";

	public String getPluginId() {
		return PLUGIN_ID;
	}
	private static Activator plugin;
	private final Cache<ImageDescriptor, Image> cache = new ImageCache();

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		cache.dispose();
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	public Cache<ImageDescriptor, Image> getCache() {
		return cache;
	}

	public Image getImage(final ImageDescriptor imageDescriptor) {
		return getDefault().cache.get(imageDescriptor);
	}

	public Image getImage(final String path) {
		return getDefault().cache.get(getImageDescriptor(path));
	}

	public ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(getPluginId(), path);
	}
}