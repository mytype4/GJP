package test01;

public class Menu {
    private String dishName;
    private double price;
    private String description;

    public Menu(String dishName, double price, String description) {
        this.dishName = dishName;
        this.price = price;
        this.description = description;
    }

    public void displayInfo() {
        System.out.println(dishName + ": $" + price + " - " + description);
    }

	public String getDishName() {
		// TODO Auto-generated method stub
		return null;
	}

    // 추가 메소드 구현
}