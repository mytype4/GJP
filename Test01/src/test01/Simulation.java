package test01;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
	public static void main(String[] args) {
//        Restaurant myRestaurant = new Restaurant("Happy Meals", "1234 Food St.");
//        Menu myMenu = new Menu("Cheeseburger", 2.99, "Delicious cheeseburger");
//        myRestaurant.addMenu(myMenu);
//
//        Owner owner = new Owner("Alice");
//        Customer customer = new Customer("John Doe", "123-456-7890");
//        Landlord landlord = new Landlord("Mr. Smith");
//
//        PartTimer partTimer = new PartTimer("Bob", "Waiter", 20);
//
//        owner.hireStaff(myRestaurant, "Waiter", partTimer.getName());
//        owner.interactWithLandlord(landlord, 1200);
//
//        partTimer.serveCustomer(customer, myMenu);
//        partTimer.prepareFood();
//
//        customer.placeOrder(myRestaurant, myMenu);
//        customer.leaveFeedback(myRestaurant, "Great food and service!");

		Person p1 = Person.createPerson("test");
		System.out.println(p1.getId());
		System.out.println(p1.getName());

//		List<Person> people = new ArrayList<>();
//		List<Customer> customers = new ArrayList<>();
//
//		// 100명의 Person 객체 생성
//		for (int i = 0; i < 100; i++) {
//			people.add(new Person("Person " + (i + 1)));
//		}
//		
//		// 생성된 모든 Person 객체의 ID와 이름 출력
//		for (Person person : people) {
//			System.out.println(person.toString());
//		}
//
//		// 20%의 Person을 Customer로 변환
//		int numberOfCustomers = (int) (people.size() * 0.20); // 20% 계산
//		for (int i = 0; i < numberOfCustomers; i++) {
//			Person person = people.get(i);
//			Customer customer = new Customer(person.getName());
//			customers.add(customer);
//		}
//
//		// 결과 확인
//		for (Customer customer : customers) {
//			System.out.println("Customer: " + customer.getName());
//		}
		List<Person> people = new ArrayList<>();
		List<Person> customers = new ArrayList<>();

		// 100명의 Person 객체 생성
		for (int i = 0; i < 100; i++) {
			people.add(new Person("Person " + (i + 1)));
		}

		// 20%의 Person을 Customer로 변환하고 원본 리스트에서 제거
		int numberOfCustomers = (int) (people.size() * 0.80); // 20% 계산
		for (int i = 0; i < numberOfCustomers; i++) {
			Person person = people.remove(0); // 리스트의 첫 번째 원소를 제거하면서 반환
			Person customer = new Customer(person.getName());
			customers.add(customer);
		}

		// 결과 확인
		System.out.println("Remaining people in the list: " + people.size());
		for (Person person : people) {
			System.out.println("People: " + person.getName());
		}
		for (Person customer : customers) {
			System.out.println("Customer: " + customer.getName());
		}
	}
}
