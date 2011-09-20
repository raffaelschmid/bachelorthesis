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

import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static java.util.Arrays.asList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureI;

public class Chart implements IChart {
	public static final String PROPERTY_SHOW_YC = "showYoungCollections";
	public static final String PROPERTY_SHOW_OC = "showOldCollections";
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_TAB_NAME = "tabName";
	public static final String PROPERTY_LABEL = "label";
	public static final String PROPERTY_SERIES = "series";

	public static final String ATTRIBUTE_SHOW_OLD_COLLECTIONS = PROPERTY_SHOW_OC;
	public static final String ATTRIBUTE_SHOW_YOUNG_COLLECTIONS = PROPERTY_SHOW_YC;
	public static final String ATTRIBUTE_LABEL = "label";
	public static final String MEMENTO_ELEMENT_NAME = "chart";
	public static final String ATTRIBUTE_DESCRIPTION = PROPERTY_DESCRIPTION;
	public static final String ATTRIBUTE_TAB_NAME = PROPERTY_TAB_NAME;
	public static final String MEMENTO_ELEMENT_META = "meta";

	private boolean showYoungCollections = false;
	private boolean showOldCollections = false;

	private String label, description, tabName;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final List<Serie> series = new ArrayList<Serie>();
	private final ChartType type;

	private final Map<String, String> meta;

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final String property, final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(property, listener);
	}

	public Chart(final ChartType type, final String tabName, final String label, final String description,
			final List<Serie> series) {
		this(type, tabName, label, description, new HashMap<String, String>(), series);
	}

	public Chart(final ChartType type, final String tabName, final String label, final String description,
			final Serie... axes) {
		this(type, tabName, label, description, new HashMap<String, String>(), asList(axes));
	}

	public Chart(final ChartType type, final String tabName, final String label, final String description,
			final Map<String, String> meta, final List<Serie> series) {
		this.label = label;
		this.tabName = tabName;
		this.description = description;
		this.type = type;
		this.series.addAll(series);
		this.meta = meta;
	}

	public void saveMemento(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, getLabel());
		memento.putString(ATTRIBUTE_DESCRIPTION, description);
		memento.putString(ATTRIBUTE_TAB_NAME, tabName);
		memento.putBoolean(ATTRIBUTE_SHOW_OLD_COLLECTIONS, showOldCollections);
		memento.putBoolean(ATTRIBUTE_SHOW_YOUNG_COLLECTIONS, showYoungCollections);
		foreach(series, new ClosureI<Serie>() {
			public void call(final Serie serie) {
				serie.save(memento);
			}
		});
		final IMemento metaMemento = memento.createChild(MEMENTO_ELEMENT_META);
		foreach(meta.keySet(), new ClosureI<String>() {
			public void call(final String key) {
				metaMemento.putString(key, meta.get(key));
			}
		});
	}

	public void addSerie(final Serie axis) {
		series.add(axis);
		propertyChangeSupport.firePropertyChange("series", null, null);
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "Chart [label=" + getLabel() + ", axes=" + series + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		propertyChangeSupport
				.firePropertyChange(PROPERTY_DESCRIPTION, this.description, this.description = description);
	}

	public void setTabName(final String tabName) {
		propertyChangeSupport.firePropertyChange(PROPERTY_TAB_NAME, this.tabName, this.tabName = tabName);
	}

	public String getTabName() {
		return tabName;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public ChartType getType() {
		return type;
	}

	public void removed(final Serie serie) {
		propertyChangeSupport.firePropertyChange(PROPERTY_SERIES, serie, null);
	}

	public void added(final Serie serie) {
		propertyChangeSupport.firePropertyChange(PROPERTY_SERIES, null, serie);
	}

	public void setLabel(final String label) {
		propertyChangeSupport.firePropertyChange(PROPERTY_LABEL, this.label, this.label = label);
	}

	public void setMeta(final String key, final String value) {
		meta.put(key, value);
	}

	public String getMeta(final String key, final String defaultValue) {
		String value = meta.get(key);
		if (value == null) {
			meta.put(key, defaultValue);
			value = defaultValue;
		}
		return value;
	}

	public boolean isShowOldCollections() {
		return showOldCollections;
	}

	public void setShowOldCollections(final boolean showOldCollections) {
		propertyChangeSupport.firePropertyChange(PROPERTY_SHOW_OC, this.showOldCollections,
				this.showOldCollections = showOldCollections);
	}

	public boolean isShowYoungCollections() {
		return showYoungCollections;
	}

	public void setShowYoungCollections(final boolean showYoungCollections) {
		propertyChangeSupport.firePropertyChange(PROPERTY_SHOW_YC, this.showYoungCollections,
				this.showYoungCollections = showYoungCollections);
	}
}
