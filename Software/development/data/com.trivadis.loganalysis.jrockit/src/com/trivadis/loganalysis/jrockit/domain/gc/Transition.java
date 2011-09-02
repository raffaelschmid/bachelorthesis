package com.trivadis.loganalysis.jrockit.domain.gc;

import com.trivadis.loganalysis.jrockit.domain.State;

public interface Transition {

	Transition startState(State state);
	Transition endState(State state);

}
