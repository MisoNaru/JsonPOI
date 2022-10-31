package orientedProgramming;

public class Fan {
	
	private String make;
	private double radious;
	private String color;
	
	private boolean isOn;
	private byte speed;
	
	// 생성자
	public Fan(String make, double radious, String color) 
	{
		this.make = make;
		this.radious = radious;
		this.color = color;
	}
	
	public void isOn(boolean isOn) 
	{
		this.isOn = isOn;
	}
	
	public void switchOn() 
	{
		this.isOn = true;
		setSpeed((byte) 10);
	}
	
	public void switchOff() 
	{
		this.isOn = false;
		setSpeed((byte) 0);
	}
	
	
	
	
	public byte getSpeed() {
		return speed;
	}

	public void setSpeed(byte speed) {
		this.speed = speed;
	}

	// 메소드
	public String toString() 
	{
		return String.format("make : %s, radious : %f, color : %s, isOn : %b, speed : %d"
				, make, radious, color, isOn, speed);
	}
	
}
