package com.arrays;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Student {

	private String name;
	private ArrayList<Integer> marks = new ArrayList<Integer>();

	public Student(String name, int... marks) {
		this.name = name;
		for(int mark:marks) 
		{
			this.marks.add(mark);
		}
	}

	public int getNumberOfMarks() {

		return marks.size();
	}

	public int getTotalSumOfMarks() {
		int sum = 0;

		for (int i = 0; i < marks.size(); i++) {
//			sum += marks[i];
//			=> array -> arrayList로 변경
			sum += marks.get(i);
		}

		return sum;
	}

	public int getMaximumMark() {
/*
 * 기존 방법
		int maximum = Integer.MIN_VALUE;
		for (int mark : marks) {
			if (mark > maximum) {
				maximum = mark;
			}
		}

		return maximum;
*/
		return Collections.max(marks);
		
	}

	public int getMinimumMark() {
/*
 * 	기존 방법
		int minimum = Integer.MAX_VALUE;
		
		for (int mark : marks) 
		{
			if (mark < minimum) 
			{
				minimum = mark;
			}
		}
		return minimum;
*/
		return Collections.min(marks);
	}

	public BigDecimal getAverageMarks() {
		int sum = getTotalSumOfMarks();
		int number = getNumberOfMarks();
		
		return new BigDecimal(sum).divide(new BigDecimal(number), 3, RoundingMode.UP);
	}
	
	public String toString() 
	{
		return name + marks;
	}

	public void addNewMark(int mark) {
		marks.add(mark);
	}

	public void removeMarkAtIndex(int mark) {
		marks.remove(mark);
	}

}
