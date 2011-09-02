package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public class KeepArea extends AbstractSpace {



	public KeepArea(JRockitJvmRun jvm) {
		super(jvm);
	}

	public String getName() {
		return "Keep Area";
	}

}
