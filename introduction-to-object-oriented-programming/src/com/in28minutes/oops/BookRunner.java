package com.in28minutes.oops;

public class BookRunner {
	public static void main(String[] args) {
		Book artOfComputerProgramming = new Book(1000);
		Book effectiveJava = new Book(2000);
		Book cleanCode = new Book(3000);
		
		System.out.println("artOfComputerProgramming:" + artOfComputerProgramming.getNoOfCopies());
		System.out.println("effectiveJava:" + effectiveJava.getNoOfCopies());
		System.out.println("cleanCode:" + cleanCode.getNoOfCopies());
		System.out.println("--------------------------------------------------------------------------");
		
		
		artOfComputerProgramming.setNoOfCopies(10);
		effectiveJava.setNoOfCopies(20);
		cleanCode.setNoOfCopies(5);
		
		artOfComputerProgramming.increaseNoOfCopies(100);
		effectiveJava.decreaseNoOfCopies(20);
		cleanCode.increaseNoOfCopies(5);
		
		System.out.println("artOfComputerProgramming:" + artOfComputerProgramming.getNoOfCopies());
		System.out.println("effectiveJava:" + effectiveJava.getNoOfCopies());
		System.out.println("cleanCode:" + cleanCode.getNoOfCopies());
		
	}
}
