package orientedProgramming;

public class RecipeOne extends AbstractRecipe {

	// 추상 클래스를 상속하려면 메소드들을 오버라이딩 해야 함
	@Override
	void getReady() {
		System.out.println("GET READY!");
	}

	@Override
	void doTheDish() {
		System.out.println("DO THE DISH!");		
	}

	@Override
	void cleanUp() {
		System.out.println("CLEAN UP!");		
	}

}
