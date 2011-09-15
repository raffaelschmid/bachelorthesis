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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.core.common.Predicate;

public class Chart implements IChart {

	public static final String ATTRIBUTE_LABEL = "label";
	public static final String MEMENTO_ELEMENT_NAME = "chart";
	public static final String ATTRIBUTE_DESCRIPTION = "description";
	private final String label;
	private final List<IAxis> axes = new ArrayList<IAxis>();
	private final String description;

	public Chart(final String label, final String description, final IAxis... axes) {
		this(label, description, asList(axes));
	}

	public Chart(final String label, final String description, final List<IAxis> axes) {
		this.label = label;
		this.description = description;
		this.axes.addAll(axes);
	}

	public void addAxis(final IAxis axis) {
		axes.add(axis);
	}

	public String getLabel() {
		return label;
	}

	public List<IAxis> getAxes() {
		return axes;
	}

	public List<IAxis> getXAxes() {
		return findAll(axes, new Predicate<IAxis>() {
			public boolean matches(final IAxis item) {
				return AxisType.X.equals(item.getAxisType());
			}
		});
	}

	public List<IAxis> getYAxes() {
		return findAll(axes, new Predicate<IAxis>() {
			public boolean matches(final IAxis item) {
				return AxisType.Y.equals(item.getAxisType());
			}
		});
	}

	public void saveMemento(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
		memento.putString(ATTRIBUTE_LABEL, label);
		memento.putString(ATTRIBUTE_DESCRIPTION, description);
		foreach(axes, new ClosureI<IAxis>() {
			public void call(final IAxis axis) {
				axis.saveMemento(memento);
			}
		});
	}

	@Override
	public String toString() {
		return "Chart [label=" + label + ", axes=" + axes + "]";
	}

	public String getYLabel() {
		final List<IAxis> yAxes = getYAxes();
		return yAxes.size() > 0 ? yAxes.get(0).getLabel() : null;
	}

	public String getXLabel() {
		final List<IAxis> xAxes = getXAxes();
		return xAxes.size() > 0 ? xAxes.get(0).getLabel() : null;
	}

	public String getDescription() {
		return description;
	}

}
