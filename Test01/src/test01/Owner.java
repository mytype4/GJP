package test01;

public class Owner extends Person{
    private String name;
    private Restaurant restaurant;  // 사장이 관리하는 음식점

    public Owner(String name) {
        super(name);
    }

    // 음식점 생성 메소드
    public void createRestaurant(String restaurantName, String location) {
        this.restaurant = new Restaurant(restaurantName, location);
        System.out.println(getName() + " has created a new restaurant: " + restaurantName + " located at " + location);
    }

    // 직원 고용 메소드
    public void hireStaff(String position, String candidateName) {
        System.out.println(getName() + " has hired " + candidateName + " as a " + position + " at " + restaurant.getName());
    }

    // 임대료 협상 메소드
    public void interactWithLandlord(Landlord landlord, double rent) {
        System.out.println(getName() + " is negotiating the rent of " + rent + " with " + landlord.getName());
    }

//    public String getName() {
//        return name;
//    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
