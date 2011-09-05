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
package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import java.util.ArrayList;
import java.util.List;

public class Tuple {

	private final Object first, second, third, fourth, fifth, sixth;

	public Tuple(Object first, Object second) {
		this(first, second, null, null, null, null);
	}

	public Tuple(Object first, Object second, Object third, Object fourth, Object fifth) {
		this(first, second,third, fourth, fifth,null);
	}

	public Tuple(Object first, Object second, Object third, Object fourth, Object fifth, Object sixth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.sixth = sixth;
	}

	public Object getFirst() {
		return first;
	}

	public Object getSecond() {
		return second;
	}

	public Object getThird() {
		return third;
	}

	public Object getFourth() {
		return fourth;
	}

	public Object getFifth() {
		return fifth;
	}

	public Object getSixth() {
		return sixth;
	}

	public String[] toArray() {
		List<String> retVal = new ArrayList<String>();
		if (first != null)
			retVal.add(first.toString());
		if (second != null)
			retVal.add(second.toString());
		if (third != null)
			retVal.add(third.toString());
		if (fourth != null)
			retVal.add(fourth.toString());
		if (fifth != null)
			retVal.add(fifth.toString());
		if (sixth != null)
			retVal.add(sixth.toString());
		return retVal.toArray(new String[retVal.size()]);
	}

}