package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public class Nursery extends AbstractSpace {

	public Nursery(JRockitJvmRun jvm) {
		super("Nursery", jvm);
	}

	public String getName() {
		return "Nursery";
	}

}
