package com.trivadis.loganalysis.ui.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.trivadis.loganalysis.core.common.Cache;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "com.trivadis.loganalysis.ui";

	private static Activator plugin;
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

	public static Activator getDefault() {
		return plugin;
	}
	
	public Cache<ImageDescriptor, Image> getCache() {
		return cache;
	}

	public static Image getImage(ImageDescriptor imageDescriptor){
		return getDefault().cache.get(imageDescriptor);
	}
	public static Image getImage(String path){
		return getDefault().cache.get(getImageDescriptor(path));
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
