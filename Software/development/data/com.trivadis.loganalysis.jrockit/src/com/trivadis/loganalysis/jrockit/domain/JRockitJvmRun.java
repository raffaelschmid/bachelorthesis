package com.trivadis.loganalysis.jrockit.domain;

import java.util.ArrayList;
import java.util.List;

import com.trivadis.loganalysis.core.domain.AbstractJvmRun;
import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.jrockit.domain.gc.GarbageCollection;
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
}
