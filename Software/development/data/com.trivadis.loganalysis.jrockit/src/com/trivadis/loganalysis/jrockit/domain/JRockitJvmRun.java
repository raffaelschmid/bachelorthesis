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
package com.trivadis.loganalysis.jrockit.domain;

import static com.trivadis.loganalysis.core.common.CollectionUtil.collect;
import static com.trivadis.loganalysis.core.common.CollectionUtil.findAll;
import static com.trivadis.loganalysis.core.common.CollectionUtil.sum;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.common.ClosureIO;
import com.trivadis.loganalysis.core.common.Predicate;
import com.trivadis.loganalysis.core.domain.AbstractJvmRun;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.unit.Time;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
import com.trivadis.loganalysis.jrockit.domain.gc.OldCollection;
import com.trivadis.loganalysis.jrockit.domain.space.Heap;

public class JRockitJvmRun extends AbstractJvmRun {

	private final Heap heap;
	private final List<GarbageCollection> garbageCollections = new ArrayList<GarbageCollection>();

	public JRockitJvmRun(IFileDescriptor descriptor) {
		super(descriptor);
		heap = new Heap(this);
	}

	public Heap getHeap() {
		return heap;
	}

	public void addTransition(GarbageCollection transition) {
		garbageCollections.add(transition);
	}

	public List<GarbageCollection> getGarbageCollections() {
		return garbageCollections;
	}

	public Time getDurationOfMeasurment() {
		return heap.getStates().get(heap.getStates().size() - 1).getTimestamp();
	}

	public long getNumberOfGcEvents() {
		return getGarbageCollections().size();
	}

	public Time getTimeSpentInGc() {
		return new Time(sum(collect(getGarbageCollections(), new ClosureIO<GarbageCollection, Double>() {
			public Double call(GarbageCollection in) {
				return in.getDuration();
			}
		})));
	}

	public Double getPercentageOfTimeInGc() {
		return getTimeSpentInGc().getSeconds() / getDurationOfMeasurment().getSeconds();
	}

	public Time getTimeSpentInFullGc() {
		return new Time(getTimeSpent(getOldCollections()));
	}

	private Double getTimeSpent(List<GarbageCollection> list) {
		return sum(collect(list, new ClosureIO<GarbageCollection, Double>(){
			public Double call(GarbageCollection in) {
				return in.getDuration();
			}}));
	}

	private List<GarbageCollection> getOldCollections() {
		return findAll(getGarbageCollections(), new Predicate<GarbageCollection>() {
			public boolean matches(GarbageCollection item) {
				return item instanceof OldCollection;
			}
		});
	}

	public Double getPercentageOfTimeInFullGc() {
		return getTimeSpentInFullGc().getSeconds() / getTimeSpentInGc().getSeconds();
	}
}
