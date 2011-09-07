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
package com.trivadis.loganalysis.jrockit.ui.domain;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.foreach;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.Closure;
import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.ui.domain.profile.AxisType;
import com.trivadis.loganalysis.ui.domain.profile.IAxis;
import com.trivadis.loganalysis.ui.domain.profile.IChart;

public class Chart implements IChart {

	private static final String ATTRIBUTE_LABEL = "label";
	public static final String MEMENTO_ELEMENT_NAME = "chart";
	private final String label;
	private final List<IAxis> axes = new ArrayList<IAxis>();

	public Chart(final String label, final IAxis... axes) {
		this(label, asList(axes));
	}

	public Chart(final String label, final List<IAxis> axes) {
		this.label = label;
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
		foreach(axes, new Closure<IAxis>() {
			public void call(final IAxis axis) {
				axis.saveMemento(memento);
			}
		});
	}

	public static IChart loadMemento(final IMemento in) {
		return new Chart(in.getString(ATTRIBUTE_LABEL), collect(asList(in.getChildren(Axis.MEMENTO_ELEMENT_NAME)),
				new ClosureIO<IMemento, IAxis>() {
					public IAxis call(final IMemento in) {
						return Axis.loadMemento(in);
					}
				}));
	}

	@Override
	public String toString() {
		return "Chart [label=" + label + ", axes=" + axes + "]";
	}

}
