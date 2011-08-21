package com.trivadis.loganalysis.jrockit.ui.internal;

public enum Axis {
	X("X-Axis:"), Y("Y-Axis:");
	private final String msg;

	Axis(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
