package com.trivadis.loganalysis.jrockit.ui.internal.view.summary;

public class Tuple {

	private final String first, second, third, fourth, fifth, sixth;

	public Tuple(String first, String second, String third, String fourth, String fifth,
			String sixth) {
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
		return new String[] { first, second, third, fourth, fifth, sixth };
	}

}