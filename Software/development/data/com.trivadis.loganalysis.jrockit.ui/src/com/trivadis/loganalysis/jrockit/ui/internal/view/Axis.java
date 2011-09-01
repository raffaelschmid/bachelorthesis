package com.trivadis.loganalysis.jrockit.ui.internal.view;

import com.trivadis.loganalysis.jrockit.ui.Messages;

public enum Axis {
	X(Messages.Axis_X), Y(Messages.Axis_Y);
	private final String msg;

	Axis(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

}
