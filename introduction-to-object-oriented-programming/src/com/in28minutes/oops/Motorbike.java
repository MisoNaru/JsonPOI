package com.in28minutes.oops;

public class Motorbike {
	// state
	private int speed;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (speed > 0) {
			this.speed = speed;
		}
	}

	public void start() {
		System.out.println("부릉부릉!");
	}

	// 캡슐화를 통해 increaseSpeed와 decraseSpeed를 setSpeed를 가져와 구현
	
	public void increaseSpeed(int increaseSpeed) {
//		this.speed = this.speed + increaseSpeed;
		setSpeed(this.speed + increaseSpeed);

	}

	public void decreaseSpeed(int decreaseSpeed) {
		setSpeed(this.speed - decreaseSpeed);
		
//		if (this.speed - decreaseSpeed > 0) {
//			this.speed = this.speed - decreaseSpeed;
//		}

	}
}
