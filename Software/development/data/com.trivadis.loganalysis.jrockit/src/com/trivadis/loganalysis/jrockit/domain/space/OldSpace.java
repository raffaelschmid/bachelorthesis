package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public class OldSpace extends AbstractSpace{

	public OldSpace(JRockitJvmRun jvm) {
		super(jvm);
	}

	public String getName() {
		return "Old Space";
	}

}
