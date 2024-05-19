package test01;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Customer extends Person {
    private Restaurant currentRestaurant;
    private Owner currentOwner;
    private ScheduledExecutorService scheduler;

    public Customer(String name) {
        super(name);
        SimulationManager.getInstance().addCustomer(this);
        this.scheduler = Executors.newScheduledThreadPool(1); // 각 Customer마다 스케줄러 생성
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
    public void autoSelectAndOrder() {
        Random random = new Random();
        List<Menu> menuList = SimulationManager.getInstance().getMenuList();

        if (menuList.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        // 랜덤으로 메뉴를 선택
        Menu selectedMenu = menuList.get(random.nextInt(menuList.size()));

        // 선택된 메뉴를 소유한 레스토랑을 찾고 주문
        for (Owner owner : SimulationManager.getInstance().getOwners()) {
            for (Restaurant restaurant : owner.getRestaurants()) {
                if (restaurant.getMenus().contains(selectedMenu)) {
                    visitRestaurant(restaurant, owner);
                    sleepRandomTime(1000, 2000); // 1~2초 대기
                    orderFood(restaurant.getName(), selectedMenu.getName());
                    sleepRandomTime(1000, 2000); // 1~2초 대기
                    leaveRestaurant();
                    return;
                }
            }
        }
        System.out.println("No restaurant found for the selected menu item.");
    }

    // 스케줄러 시작
    public void startScheduler() {
        scheduler.scheduleWithFixedDelay(() -> autoSelectAndOrder(), 0, 10, TimeUnit.SECONDS);
    }

    // 스케줄러 종료
    public void shutdownScheduler() {
        scheduler.shutdownNow(); // 즉시 종료
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) { // 10초 동안 종료를 기다림
                System.err.println("Scheduler did not terminate");
            }
        } catch (InterruptedException ie) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // 랜덤한 시간 동안 대기하는 메서드
    private void sleepRandomTime(int minMillis, int maxMillis) {
        Random random = new Random();
        int sleepTime = minMillis + random.nextInt(maxMillis - minMillis + 1);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
