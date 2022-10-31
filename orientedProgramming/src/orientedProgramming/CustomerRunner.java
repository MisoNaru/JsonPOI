package orientedProgramming;

public class CustomerRunner {

	public static void main(String[] args) {
		
		Address homeAddress = new Address("진접", "남양주", "7777");
		Customer customer = new Customer("백종헌", homeAddress);
		Address workAddress = new Address("반곡동", "원주", "1111");
		customer.setHomeAddress(homeAddress);
		customer.setWorkAddress(workAddress);
		
		System.out.println(customer);
		
		
		
		
		
	}

}
