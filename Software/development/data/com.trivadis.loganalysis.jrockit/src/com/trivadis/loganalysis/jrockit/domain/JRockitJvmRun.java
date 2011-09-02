package com.trivadis.loganalysis.jrockit.domain;

import com.trivadis.loganalysis.core.domain.IFileDescriptor;
import com.trivadis.loganalysis.core.domain.IJvmRun;
import com.trivadis.loganalysis.jrockit.domain.space.Heap;
import com.trivadis.loganalysis.jrockit.domain.space.KeepArea;
import com.trivadis.loganalysis.jrockit.domain.space.OldSpace;
import com.trivadis.loganalysis.jrockit.domain.space.YoungSpace;

public class JRockitJvmRun implements IJvmRun{

	private final Heap heap = new Heap(new KeepArea(), new OldSpace(), new YoungSpace());
	private final IFileDescriptor descriptor;

	public JRockitJvmRun(IFileDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public Heap getHeap() {
		return heap;
	}

	public IFileDescriptor getDescriptor() {
		return descriptor;
	}

}
