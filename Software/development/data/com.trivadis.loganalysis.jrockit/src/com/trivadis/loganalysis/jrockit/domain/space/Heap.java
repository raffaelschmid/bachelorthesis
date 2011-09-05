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
package com.trivadis.loganalysis.jrockit.domain.space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public class Heap extends AbstractArea {
	private final List<Area> spaces;
	private final Tenured tenured;
	private final KeepArea keepArea;
	private final Nursery nursery;

	public Heap(JRockitJvmRun jvm) {
		super("Heap", jvm);
		nursery = new Nursery(jvm);
		keepArea = new KeepArea(jvm);
		tenured = new Tenured(jvm);
		spaces = Arrays.asList(new Area[] { nursery, keepArea, tenured });
	}

	public List<Area> getSpacesAndHeap() {
		ArrayList<Area> retVal = new ArrayList<Area>(spaces);
		retVal.add(this);
		return retVal;
	}

	public List<Area> getSpaces() {
		return spaces;
	}

	public Tenured getTenured() {
		return tenured;
	}

	public KeepArea getKeepArea() {
		return keepArea;
	}

	public Nursery getNursery() {
		return nursery;
	}

}
