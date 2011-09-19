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
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureI;

public class Chart implements IChart {

	public static final String ATTRIBUTE_LABEL = "label";
	public static final String MEMENTO_ELEMENT_NAME = "chart";
	public static final String ATTRIBUTE_DESCRIPTION = "description";
	public static final String ATTRIBUTE_TAB_NAME = "tabName";

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final List<Serie> series = new ArrayList<Serie>();
	private final String label;
	private String description, tabName;
	private final ChartType type;
	private boolean editable = false;

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public Chart(final ChartType type, final String tabName, final String label, final String description,
			final Serie... axes) {
		this(type, tabName, label, description, asList(axes));
	}

	public Chart(final ChartType type, final String tabName, final String label, final String description,
			final List<Serie> series) {
		this.label = label;
		this.tabName = tabName;
		this.description = description;
		this.type = type;
		this.series.addAll(series);
	}

	public void addSerie(final Serie axis) {
		series.add(axis);
		propertyChangeSupport.firePropertyChange("series", null, null);
	}

	public String getLabel() {
		return label;
	}

	public void saveMemento(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		memento.putString(ATTRIBUTE_DESCRIPTION, description);
		memento.putString(ATTRIBUTE_TAB_NAME, tabName);
		foreach(series, new ClosureI<Serie>() {
			public void call(final Serie serie) {
				serie.save(memento);
			}
		});
	}

	@Override
	public String toString() {
		return "Chart [label=" + label + ", axes=" + series + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		propertyChangeSupport.firePropertyChange("description", this.description, this.description = description);
	}

	public void setTabName(final String tabName) {
		propertyChangeSupport.firePropertyChange("tabName", this.tabName, this.tabName = tabName);
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
		propertyChangeSupport.firePropertyChange("series", serie,null);
	}
	public void added(final Serie serie) {
		propertyChangeSupport.firePropertyChange("series", null,serie);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(final boolean editable) {
		propertyChangeSupport.firePropertyChange("editable", this.editable, this.editable = editable);
	}

}
