package test01;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Customer extends Person {
    private Restaurant currentRestaurant;
    private Owner currentOwner;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
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
    public void autoSelectAndOrder(List<Menu> menuList) {
        Random random = new Random();
        Menu selectedMenu = menuList.get(random.nextInt(menuList.size()));
        Restaurant restaurant = selectedMenu.getRestaurant();

        if (restaurant != null) {
            // 방문, 주문, 떠나는 과정을 비동기적으로 처리
            scheduler.schedule(() -> {
                visitRestaurant(restaurant, restaurant.getOwner());
                scheduler.schedule(() -> {
                    orderFood(restaurant.getName(), selectedMenu.getName());
                    scheduler.schedule(this::leaveRestaurant, random.nextInt(10) + 5, TimeUnit.SECONDS);
                }, random.nextInt(10) + 5, TimeUnit.SECONDS);
            }, random.nextInt(10) + 5, TimeUnit.SECONDS);
        } else {
            System.out.println("No restaurant found for the selected menu item.");
        }
    }

    // 스케줄러 종료 메서드
    public void shutdownScheduler() {
        scheduler.shutdown();
    }
}
