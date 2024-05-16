package test01;

public class Main {
    public static void main(String[] args) {
        // 소유자 생성
    	Owner restaurantOwner = new Owner("Alice");

        // 식당 생성
        Restaurant gourmetPalace = restaurantOwner.createRestaurant("Gourmet Palace");

        // 직원 고용
        PartTimer waiter = new PartTimer("Bob");
        restaurantOwner.hirePartTimer(gourmetPalace, waiter);

        // 메뉴 추가
        Menu pizza = new Menu("Pizza", 12.99);
        Menu burger = new Menu("Burger", 8.99);
//        restaurantOwner.addMenuToRestaurant(gourmetPalace, pizza);
//        restaurantOwner.addMenuToRestaurant(gourmetPalace, burger);

        // 고객 생성 및 방문
        Customer customer1 = new Customer("Eve");
        customer1.visitRestaurant(gourmetPalace, restaurantOwner);

        // 음식 주문
        customer1.orderFood();

        // 레스토랑 떠나기
        customer1.leaveRestaurant();
        
        /* 잔고 확인
        System.out.println(restaurantOwner.getName() + "'s bank account: " + "$" + restaurantOwner.getBankAccount());
        System.out.println(customer1.getName() + "'s bank account: " + "$" + customer1.getBankAccount()); 
        */

    }
}