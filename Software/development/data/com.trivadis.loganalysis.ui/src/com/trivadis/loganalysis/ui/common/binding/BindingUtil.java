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
package com.trivadis.loganalysis.ui.common.binding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.widgets.Widget;

public class BindingUtil {
	public static void bindCheckbox(final Widget widget, final Class<?> clazz, final Object model, final String propertyName) {
		new DataBindingContext().bindValue(WidgetProperties.selection().observe(widget),
				BeanProperties.value(clazz, propertyName).observe(model));
	}
}
