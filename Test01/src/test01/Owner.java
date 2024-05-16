package test01;

import java.util.ArrayList;
import java.util.List;

class Owner extends Person {
	private List<Restaurant> restaurants;

	public Owner(String name) {
		super(name);
		this.restaurants = new ArrayList<>();
	}

	@Override
	public void introduce() {
		System.out.println("Hello, I am an Owner named " + getName());
	}

	// 레스토랑 생성 및 리스트에 추가
	public Restaurant createRestaurant(String restaurantName) {
		Restaurant restaurant = new Restaurant(restaurantName, this);
		restaurants.add(restaurant);
		System.out.println(getName() + " has opened a new restaurant named " + restaurantName);
		return restaurant;
	}

	// 특정 레스토랑 이름으로 메뉴 추가
	public void addMenuToRestaurant(String restaurantName, String menuName, int menuPrice) {
		Restaurant restaurant = findRestaurantByName(restaurantName);
		if (restaurant != null) {
			Menu menu = new Menu(menuName, menuPrice);
			restaurant.addMenu(menu);
			System.out.println(menu.getName() + " has been added to the menu at " + restaurant.getName());
		} else {
			System.out.println("Error: No restaurant found with the name " + restaurantName);
		}
	}

	// 레스토랑 이름으로 레스토랑 찾기
	private Restaurant findRestaurantByName(String restaurantName) {
		for (Restaurant restaurant : restaurants) {
			if (restaurant.getName().equals(restaurantName)) {
				return restaurant;
			}
		}
		return null;
	}

	// 특정 레스토랑에 PartTimer 고용
	public void hirePartTimer(Restaurant restaurant, PartTimer partTimer) {
		restaurant.addPartTimer(partTimer);
		System.out.println(partTimer.getName() + " has been hired as a part-timer at " + restaurant.getName());
	}

	public void manageRestaurant() {

	}

	// 소유한 레스토랑 목록 표시
	public void listRestaurants() {
		System.out.println("Restaurants owned by " + getName() + ":");
		for (Restaurant restaurant : restaurants) {
			System.out.println("- " + restaurant.getName());
		}
	}
}
