package com.trivadis.loganalysis.jrockit.domain.gc;

import com.trivadis.loganalysis.jrockit.domain.State;

public interface GarbageCollection extends Transition {

	String getName();

	double getDuration();

	State getStartState();

	State getEndState();

}
