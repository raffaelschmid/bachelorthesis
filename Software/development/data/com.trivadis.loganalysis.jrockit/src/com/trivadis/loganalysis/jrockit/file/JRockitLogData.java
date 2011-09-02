package com.trivadis.loganalysis.jrockit.file;

import java.util.HashMap;
import java.util.Map;


public class JRockitLogData {
	
	private final Map<ValueType, Value> map;

	public JRockitLogData(){
		this.map = new HashMap<ValueType, Value>();
	}
	
	public Value get(ValueType key){
		return map.get(key);
	}
	
	public void put(ValueType key, Value value){
		map.put(key, value);
	}
}
