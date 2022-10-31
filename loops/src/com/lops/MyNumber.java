package com.lops;

public class MyNumber {

	private int number;

	public MyNumber(int number) {
		this.number = number;
	}

	// 소수 구하기 //
	public boolean isPrime() {

		if (number < 2) {
			return false;
		}

		for (int i = 2; i <= number - 1; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;

	}


	public int sumUpToN() {
		
		int sum = 0;
		
		for (int i = 0; i <= number; i++) 
		{
			sum = sum + i;
		}
		
		return sum;
	}

	// 약수 합 //
	public int sumOfDivisors() {
		int sumOfDivisors = 0;
		
		for (int i = 2; i < number; i++) 
		{
			if (number % i == 0)
			{
				sumOfDivisors = sumOfDivisors + i;
			}
			
		}
		
		
		return sumOfDivisors;
	}

	public void printANumberTriangle() {
		
		for (int i = 1; i <= number; i++) 
		{
			for (int j = 1; j <= i; j++) 
			{
				System.out.print(j + " ");
				
			}
			System.out.println();
			
		}
		
		
	}

}
