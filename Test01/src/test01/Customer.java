package test01;

public class Customer {
    private String name;
    private String contactInfo;

    public Customer(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public void placeOrder(Restaurant restaurant, Menu menu) {
        System.out.println(name + " has ordered " + menu.getDishName() + " from " + restaurant.getName());
        // 주문 로직 구현
    }

    public void leaveFeedback(Restaurant restaurant, String feedback) {
        System.out.println("Feedback from " + name + " for " + restaurant.getName() + ": " + feedback);
        // 피드백 저장 로직 구현
    }

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}