package com.trivadis.loganalysis.jrockit.domain;

import java.util.Map;

public class DataLine {
	
	private final Map<DataExtractor, Value> map;

	public DataLine(Map<DataExtractor, Value> map){
		this.map = map;
	}
	
	public Value getValue(DataExtractor key){
		return map.get(key);
	}
}
