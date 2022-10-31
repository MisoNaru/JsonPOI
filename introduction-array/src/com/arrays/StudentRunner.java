package com.arrays;

import java.math.BigDecimal;

public class StudentRunner {
	public static void main(String[] args) {
		
		
		Student student = new Student("BAEK", 100, 0, 100);
		
		Student student2 = new Student("JEONG", 100, 100, 100, 100, 100);
		
		int number = student.getNumberOfMarks();
		
		int sum = student.getTotalSumOfMarks();
		System.out.println("sum:" + sum);
		
		int maximumMark = student.getMaximumMark();
		System.out.println("maximumMark:" + maximumMark);
		
		int minimumMark = student.getMinimumMark();
		System.out.println("minimumMark:" + minimumMark);
		
		BigDecimal average = student.getAverageMarks();
		System.out.println("average:" + average);
		
		System.out.println(student);
		
		student.addNewMark(35);
		
		System.out.println(student);
		
		student.removeMarkAtIndex(0);
	
		System.out.println(student);
	}
}
