package test01;

public class Customer extends Person {
    public Customer(String name) {
        super(name);  // Person 클래스의 생성자를 호출하여 이름을 설정합니다.
    }

    public void placeOrder(Restaurant restaurant, Menu menu) {
        System.out.println(getName() + " has ordered " + menu.getDishName() + " from " + restaurant.getName());
    }

    public void leaveFeedback(Restaurant restaurant, String feedback) {
        System.out.println("Feedback from " + getName() + " for " + restaurant.getName() + ": " + feedback);
    }
}
