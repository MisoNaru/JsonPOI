package orientedProgramming.interfaces;

public class MarioGame implements GamingConsole {

	@Override
	public void up() {
		System.out.println("JUMP");
	}

	@Override
	public void down() {
		System.out.println("DOWN");
	}

	@Override
	public void left() {
		System.out.println("MOVE LEFT");
	}

	@Override
	public void right() {
		System.out.println("MOVE RIGHT");		
	}
	
}
