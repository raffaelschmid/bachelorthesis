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

import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static java.util.Arrays.asList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.core.common.Predicate;

public class Serie {
	public static final String MEMENTO_ELEMENT_NAME = "serie";
	public static final String ATTRIBUTE_LABEL = "label";
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final List<IAxis> axes;
	private String label;

	public Serie(final String label, final IAxis... axes) {
		this(label, asList(axes));
	}

	public Serie(final String label, final List<IAxis> axes) {
		this.axes = axes;
		this.label = label;
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public List<IAxis> getAxes() {
		return axes;
	}

	public List<IAxis> getAxes(final AxisType type) {
		return findAll(axes, new Predicate<IAxis>() {
			public boolean matches(final IAxis item) {
				return type.equals(item.getAxisType());
			}
		});
	}

	public void save(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		foreach(axes, new ClosureI<IAxis>() {
			public void call(final IAxis serie) {
				serie.save(memento);
			}
		});
	}

	@Override
	public String toString() {
		return "Serie [axes=" + axes + "]";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		propertyChangeSupport.firePropertyChange("label", this.label, this.label = label);
	}

}
