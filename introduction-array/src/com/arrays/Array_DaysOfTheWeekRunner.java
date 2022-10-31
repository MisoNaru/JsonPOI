package com.arrays;

public class Array_DaysOfTheWeekRunner {
	public static void main(String[] args) {

		String[] daysOfWeek = { "Sunday", "Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday" };

		Array_DaysOfTheWeek array_DaysOfTheWeek = new Array_DaysOfTheWeek("Sunday", "Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday" );

		String dayWithMostCharacters = "";
		for(String day:daysOfWeek) 
		{
			if ( day.length() > dayWithMostCharacters.length() ) 
			{
				dayWithMostCharacters = day;
			}
		}
		
		System.out.println("Most numbered day is:" + dayWithMostCharacters);
		
		// 배열 역순 출력
//		for (int i = 0 ; i <daysOfWeek.length; i++) 
		for (int i = daysOfWeek.length -1 ; i >= 0; i--)
		{
			System.out.print("역순 출력:"+daysOfWeek[i]+" / ");
		}
		
	}
}
