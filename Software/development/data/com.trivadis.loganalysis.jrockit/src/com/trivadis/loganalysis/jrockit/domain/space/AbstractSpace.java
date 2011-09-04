package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public abstract class AbstractSpace extends AbstractArea implements Space {

	public AbstractSpace(String name, JRockitJvmRun jvm) {
		super(name, jvm);
	}
}
