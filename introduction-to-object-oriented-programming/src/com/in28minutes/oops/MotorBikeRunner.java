package com.in28minutes.oops;

public class MotorBikeRunner {
	public static void main(String[] args) {
		Motorbike honda = new Motorbike(1000);
		Motorbike yamaha = new Motorbike(2000);
		Motorbike daelim = new Motorbike(3000);
		
		System.out.println(honda.getSpeed());
		System.out.println(yamaha.getSpeed());
		
		honda.start();
		yamaha.start();
		
		honda.setSpeed(100);
		honda.increaseSpeed(100);
		System.out.println(honda.getSpeed());
		
		yamaha.setSpeed(300);
		yamaha.increaseSpeed(700);
		System.out.println(yamaha.getSpeed());
		
		int hondaSpeed = honda.getSpeed();
		hondaSpeed += 100;
		honda.setSpeed(hondaSpeed);
		
		
		int yamahaSpeed = yamaha.getSpeed();
		yamahaSpeed += 100;
		yamaha.setSpeed(yamahaSpeed);
		
		
//		System.out.println(honda.getSpeed());
		
//		yamaha.setSpeed(120);
//		System.out.println(yamaha.getSpeed());

		
	}
}
