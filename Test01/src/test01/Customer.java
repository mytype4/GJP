package test01;

import java.util.List;
import java.util.Scanner;

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

    // 레스토랑에서 메뉴 주문
    public void orderFood() {
        if (currentRestaurant == null) {
            System.out.println(getName() + " is not currently visiting any restaurant.");
            return;
        }

        List<Menu> menus = currentRestaurant.getMenus();
        if (menus.isEmpty()) {
            System.out.println("No menu items available at " + currentRestaurant.getName());
            return;
        }

        System.out.println("Menu items available at " + currentRestaurant.getName() + ":");
        for (int i = 0; i < menus.size(); i++) {
            System.out.println((i + 1) + ". " + menus.get(i).getName() + ": $" + menus.get(i).getPrice());
        }

        System.out.println("Which menu item would you like to order?");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt() - 1;

        if (choice >= 0 && choice < menus.size()) {
            System.out.println(getName() + " has ordered " + menus.get(choice).getName() + ".");
            this.pay(currentOwner, menus.get(choice).getPrice());
        } else {
            System.out.println("Invalid choice.");
        }
        
       scanner.close();
    }

    // 레스토랑에서 나가기
    public void leaveRestaurant() {
        if (currentRestaurant != null) {
            currentRestaurant.removeCustomer(this);
            System.out.println(getName() + " has left " + currentRestaurant.getName());
            currentRestaurant = null;
        }
    }
}