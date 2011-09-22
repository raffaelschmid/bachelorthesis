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
package com.trivadis.loganalysis.core.common;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static java.util.Arrays.asList;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class ExtensionUtil {
	public static <T> List<T> findExtensionInstances(final String extensionPointId, final String name) {
		final IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				extensionPointId);
		return collect(findAll(asList(elements), new Predicate<IConfigurationElement>() {
			public boolean matches(final IConfigurationElement element) {
				return name.equals(element.getName());
			}
		}), new ClosureIO<IConfigurationElement, T>() {
			@SuppressWarnings("unchecked")
			public T call(final IConfigurationElement element) {
				try {
					return (T) element.createExecutableExtension("class");
				} catch (final CoreException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
