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
package com.trivadis.loganalysis.ui.domain.profile;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.ui.IMemento;

public interface IChart {

	void addSerie(Serie xAxis);

	String getLabel();

	List<Serie> getSeries();

	void saveMemento(IMemento parent);

	String getDescription();

	void setTabName(String valueOf);

	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	String getTabName();

	ChartType getType();

	void removed(Serie element);
	void added(Serie element);

	String getMeta(String key, String defaultValue);

	void setMeta(String key, String value);

	void removePropertyChangeListener(String property, PropertyChangeListener listener);

}
