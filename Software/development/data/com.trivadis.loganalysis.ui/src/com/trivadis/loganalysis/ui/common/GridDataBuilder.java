/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
package com.trivadis.loganalysis.ui.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

public class GridDataBuilder {

	private int horizontalAlignment = GridData.BEGINNING;
	private boolean grabExcessHorizontalSpace = false;
	private int verticalAlignment = GridData.CENTER;
	private boolean grabExcessVerticalSpace = false;
	private int horizontalSpan = 1;
	private int verticalSpan = 1;
	private int horizontalIndent = 0;
	private int heightHint = SWT.DEFAULT;
	private int widthHint = SWT.DEFAULT;

	public GridDataBuilder fill() {
		horizontalAlignment = SWT.FILL;
		grabExcessHorizontalSpace = true;
		verticalAlignment = SWT.FILL;
		grabExcessVerticalSpace = true;
		return this;
	}

	public GridDataBuilder fillVertical() {
		verticalAlignment = SWT.FILL;
		grabExcessVerticalSpace = true;
		return this;
	}

	public GridDataBuilder horizontalAlignment(final int horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
		return this;
	}

	public GridDataBuilder grabExcessHorizontalSpace(final boolean grabExcessHorizontalSpace) {
		this.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
		return this;
	}

	public GridDataBuilder verticalAlignment(final int verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
		return this;
	}

	public GridDataBuilder grabExcessVerticalSpace(final boolean grabExcessVerticalSpace) {
		this.grabExcessVerticalSpace = grabExcessVerticalSpace;
		return this;
	}

	public GridData build() {
		final GridData gridData = new GridData();
		gridData.horizontalAlignment = horizontalAlignment;
		gridData.verticalAlignment = verticalAlignment;
		gridData.grabExcessHorizontalSpace = grabExcessHorizontalSpace;
		gridData.grabExcessVerticalSpace = grabExcessVerticalSpace;
		gridData.horizontalSpan = horizontalSpan;
		gridData.verticalSpan = verticalSpan;
		gridData.horizontalIndent = horizontalIndent ;
		gridData.widthHint = widthHint;
		gridData.heightHint = heightHint;
		return gridData;
	}

	public GridDataBuilder horizontalSpan(final int horizontalSpan) {
		this.horizontalSpan = horizontalSpan;
		return this;
	}

	public GridDataBuilder verticalSpan(final int verticalSpan) {
		this.verticalSpan = verticalSpan;
		return this;
	}

	public GridDataBuilder horizontalIndent(final int horizontalIndent) {
		this.horizontalIndent = horizontalIndent;
		return this;
	}

	public int getWidthHint() {
		return widthHint;
	}

	public GridDataBuilder widthHint(final int widthHint) {
		this.widthHint = widthHint;
		return this;
	}

	public GridDataBuilder heightHint(final int heightHint) {
		this.heightHint = heightHint;
		return this;
	}

}
