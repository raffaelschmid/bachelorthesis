package com.trivadis.loganalysis.jrockit.domain;

public class Value {
	private final String value;
	public Value(String value) {
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
	public Double toDouble(){
		return Double.valueOf(value);
	}

}
