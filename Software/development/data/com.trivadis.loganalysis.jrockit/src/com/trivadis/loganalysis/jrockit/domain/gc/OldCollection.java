package com.trivadis.loganalysis.jrockit.domain.gc;

public class OldCollection extends AbstractGarbageCollection {

	public OldCollection(double duration) {
		super("Old Collection",duration);
	}

}
