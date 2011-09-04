package com.trivadis.loganalysis.jrockit.domain.space;

import com.trivadis.loganalysis.jrockit.domain.JRockitJvmRun;

public class Tenured extends AbstractSpace {

	public Tenured(JRockitJvmRun jvm) {
		super("Tenured", jvm);
	}

	public String getName() {
		return "Tenured space";
	}

}
