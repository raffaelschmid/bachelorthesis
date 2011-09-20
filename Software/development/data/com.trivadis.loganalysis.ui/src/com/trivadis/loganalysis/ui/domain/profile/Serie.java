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
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.IMemento;

public class Serie {

	public static final String MEMENTO_ELEMENT_NAME = "serie";
	public static final String ATTRIBUTE_LABEL = "label";
	public static final String ATTRIBUTE_DOTTED = "dotted";

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private String label;
	private int index = -1;
	private final IAxis yaxis, xaxis;
	private boolean dotted = false;

	public Serie(final String label, final IAxis xAxis, final IAxis yAxis) {
		this.label = label;
		this.xaxis = xAxis;
		this.yaxis = yAxis;
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

	public void save(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		memento.putBoolean(ATTRIBUTE_DOTTED, dotted);
		xaxis.save(memento);
		yaxis.save(memento);
	}

	@Override
	public String toString() {
		return "Serie [label=" + label + ", yAxis=" + yaxis + ", xAxis=" + xaxis + "]";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		propertyChangeSupport.firePropertyChange("label", this.label, this.label = label);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public IAxis getYaxis() {
		return yaxis;
	}

	public IAxis getXaxis() {
		return xaxis;
	}

	public boolean isDotted() {
		return dotted;
	}

	public void setDotted(final boolean dotted) {
		this.dotted = dotted;
	}

}
