package test01;

public class Customer extends Person {
    public Customer(String name) {
        super(name);  // Person 클래스의 생성자를 호출하여 이름을 설정합니다.
    }

    public void orderFood(Restaurant restaurant, Menu menu) {
        System.out.println(getName() + " has ordered " + menu.getDishName() + " from " + restaurant.getName());
    }

    public void leaveFeedback(Restaurant restaurant, String feedback) {
        System.out.println("Feedback from " + getName() + " for " + restaurant.getName() + ": " + feedback);
    }

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public int getWealth() {
		// TODO Auto-generated method stub
		return super.getWealth();
	}

	@Override
	public void setWealth(int wealth) {
		// TODO Auto-generated method stub
		super.setWealth(wealth);
	}

	@Override
	public void goToRestaurant() {
		// TODO Auto-generated method stub
		super.goToRestaurant();
	}

	@Override
	public void orderFood(int price) {
		// TODO Auto-generated method stub
		super.orderFood(price);
	}

	@Override
	public void eat() {
		// TODO Auto-generated method stub
		super.eat();
	}

	@Override
	public void pay(int amount) {
		// TODO Auto-generated method stub
		super.pay(amount);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
