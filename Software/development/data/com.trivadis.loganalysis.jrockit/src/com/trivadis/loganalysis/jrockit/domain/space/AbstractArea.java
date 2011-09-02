package com.trivadis.loganalysis.jrockit.domain.space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trivadis.loganalysis.jrockit.domain.State;

public class AbstractArea implements Area {

	private final List<State> states = new ArrayList<State>();

	public void addStates(State... states) {
		this.states.addAll(Arrays.asList(states));
	}
	
	public List<State> getStates(){
		return states;
	}
}
