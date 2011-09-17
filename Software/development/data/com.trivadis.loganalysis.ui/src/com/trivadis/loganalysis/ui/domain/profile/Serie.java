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

import java.util.List;

import org.eclipse.ui.IMemento;

import com.trivadis.loganalysis.core.common.ClosureI;
import com.trivadis.loganalysis.core.common.Predicate;
public class Serie {
	public static final String MEMENTO_ELEMENT_NAME = "serie";
	private final List<IAxis> axes;

	public Serie(final IAxis... axes) {
		this(asList(axes));
	}

	public Serie(final List<IAxis> axes) {
		this.axes = axes;
	}

	public List<IAxis> getAxes() {
		return axes;
	}
	
	public List<IAxis> getAxes(final AxisType type){
		return findAll(axes, new Predicate<IAxis>(){
			public boolean matches(final IAxis item) {
				return type.equals(item.getAxisType());
			}});
	}

	public void save(final IMemento parent) {
		final IMemento memento = parent.createChild(MEMENTO_ELEMENT_NAME);
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

	
}
