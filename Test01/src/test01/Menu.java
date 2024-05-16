package test01;

public class Menu {
    private String name;
    private int price;
    private Restaurant restaurant; // 메뉴가 속한 레스토랑

    public Menu(String name, int price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
