package test01;

import java.util.List;
import java.util.Random;

class Customer extends Person {
    private Restaurant currentRestaurant;
    private Owner currentOwner;
    
    public Customer(String name) {
        super(name);
    }

    @Override
    public void introduce() {
        System.out.println("Hello, I am a Customer named " + getName());
    }

    // 레스토랑 방문
    public void visitRestaurant(Restaurant restaurant, Owner owner) {
        if (currentRestaurant != null && currentOwner != null) {
            leaveRestaurant();
        }
        currentRestaurant = restaurant;
        restaurant.addCustomer(this);
        
        currentOwner = owner;
    }

    // 레스토랑에서 나가기
    public void leaveRestaurant() {
        if (currentRestaurant != null) {
            currentRestaurant.removeCustomer(this);
            System.out.println(getName() + " has left " + currentRestaurant.getName());
            currentRestaurant = null;
            currentOwner = null;
        }
    }

    // 레스토랑에서 메뉴 주문
    public void orderFood(String restaurantName, String menuName) {
        if (currentRestaurant == null || !currentRestaurant.getName().equals(restaurantName)) {
            System.out.println("Customer is not currently visiting " + restaurantName);
            return;
        }

        List<Menu> menus = currentRestaurant.getMenus();
        for (Menu menu : menus) {
            if (menu.getName().equals(menuName)) {
                System.out.println(getName() + " has ordered " + menuName + " at " + restaurantName + ".");
                this.pay(currentOwner, menu.getPrice());
                return;
            }
        }
        System.out.println("Menu item " + menuName + " not found in " + restaurantName);
    }

    // 메뉴를 임의로 선택하고 행동하는 메서드
    public void autoSelectAndOrder(List<Menu> menuList, List<Owner> owners) {
        Random random = new Random();
        Menu selectedMenu = menuList.get(random.nextInt(menuList.size()));

        for (Owner owner : owners) {
            for (Restaurant restaurant : owner.getRestaurants()) {
                if (restaurant.getMenus().contains(selectedMenu)) {
                    visitRestaurant(restaurant, owner);
                    orderFood(restaurant.getName(), selectedMenu.getName());
                    leaveRestaurant();
                    return;
                }
            }
        }
        System.out.println("No restaurant found for the selected menu item.");
    }
}
