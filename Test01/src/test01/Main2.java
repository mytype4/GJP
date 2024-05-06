package test01;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
	public static void main(String[] args) {
		Customer c1 = new Customer("mins"); //다형성 사용
		Restaurant r1 = new Restaurant("sdf", "dsdf");
		Menu m1 = new Menu("쌀국수",14000, "맛있는 쌀국수");
		
		c1.eat();
//		c1.placeOrder();
		
		List<Person> people = new ArrayList<>();
		people.add(new Customer("John"));
		people.add(new Owner("Alice"));
		people.add(new PartTimer("Bob"));

		for (Person person : people) {
		    person.eat(); // 모든 사람은 먹을 수 있습니다.
		    if (person instanceof Customer) {
		        ((Customer) person).orderFood(r1,m1);
		    } else if (person instanceof Owner) {
		        ((Owner) person).createRestaurant("새로운 음식점", "광운대역");
		    } else if (person instanceof PartTimer) {
		        ((PartTimer) person).serveCustomer(c1,m1);
		    }
		}
	}
}
