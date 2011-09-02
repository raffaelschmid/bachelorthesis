package com.trivadis.loganalysis.jrockit.domain.gc;

public class YoungCollection extends AbstractGarbageCollection {

	public YoungCollection(double duration) {
		super("Young Collection", duration);
	}

}
