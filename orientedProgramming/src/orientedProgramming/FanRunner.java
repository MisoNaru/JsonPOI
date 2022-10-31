package orientedProgramming;

public class FanRunner {

	public static void main(String[] args) {
		Fan fan= new Fan("SAMSUNG", 1, "WHITE");
		fan.switchOn();
		System.out.println(fan.toString());
		fan.switchOff();
		System.out.println(fan.toString());
	}

}
