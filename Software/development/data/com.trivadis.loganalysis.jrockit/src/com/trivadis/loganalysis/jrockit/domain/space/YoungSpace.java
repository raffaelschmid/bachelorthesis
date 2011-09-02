package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;


public class YoungSpace extends AbstractSpace {

	public YoungSpace(JRockitJvmRun jvm) {
		super(jvm);
	}

	public String getName() {
		return "Young Space";
	}

}
