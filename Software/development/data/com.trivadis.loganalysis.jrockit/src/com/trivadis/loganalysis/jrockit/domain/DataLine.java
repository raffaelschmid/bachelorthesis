package com.trivadis.loganalysis.jrockit.domain;

import java.util.HashMap;
import java.util.Map;

public class DataLine {
	
	private final Map<MetaInfo, Value> map;

	public DataLine(){
		this.map = new HashMap<MetaInfo, Value>();
	}
	
	public Value get(MetaInfo key){
		return map.get(key);
	}
	
	public void put(MetaInfo key, Value value){
		map.put(key, value);
	}
}
