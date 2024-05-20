package test01;

import java.time.LocalDateTime;

public class Order {
    private String customerName;
    private String restaurantName;
    private String menuName;
    private int price;
    private LocalDateTime orderTime;

    public Order(String customerName, String restaurantName, String menuName, int price, LocalDateTime orderTime) {
        this.customerName = customerName;
        this.restaurantName = restaurantName;
        this.menuName = menuName;
        this.price = price;
        this.orderTime = orderTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }
}