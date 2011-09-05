package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

import java.util.ArrayList;
import java.util.List;

public class Tuple {

	private final String first, second, third, fourth, fifth, sixth;

	public Tuple(String first, String second) {
		this(first, second, null, null, null, null);
	}

	public Tuple(String first, String second, String third, String fourth, String fifth) {
		this(first, second,third, fourth, fifth,null);
	}

	public Tuple(String first, String second, String third, String fourth, String fifth, String sixth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.sixth = sixth;
	}

	public String getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}

	public String getThird() {
		return third;
	}

	public String getFourth() {
		return fourth;
	}

	public String getFifth() {
		return fifth;
	}

	public String getSixth() {
		return sixth;
	}

	public String[] toArray() {
		List<String> retVal = new ArrayList<String>();
		if (first != null)
			retVal.add(first);
		if (second != null)
			retVal.add(second);
		if (third != null)
			retVal.add(third);
		if (fourth != null)
			retVal.add(fourth);
		if (fifth != null)
			retVal.add(fifth);
		if (sixth != null)
			retVal.add(sixth);
		return retVal.toArray(new String[retVal.size()]);
	}

}