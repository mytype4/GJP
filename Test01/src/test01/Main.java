package test01;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Owner> owners = new ArrayList<>();
        owners.add(new Owner("Alice"));
        owners.add(new Owner("Bob"));
        owners.add(new Owner("Charlie"));
        owners.add(new Owner("David"));
        owners.add(new Owner("Eva"));
        owners.add(new Owner("Frank"));
        owners.add(new Owner("George"));
        owners.add(new Owner("Hannah"));

        // 각 사장이 음식점을 생성
        String[] locations = {"Downtown", "Uptown", "Midtown", "Eastside", "Westside", "North Hills", "South Valley", "Central Park"};
        for (int i = 0; i < owners.size(); i++) {
            String restaurantName = "Restaurant " + (i + 1);
            String location = locations[i % locations.length];
            owners.get(i).createRestaurant(restaurantName, location);
        }
    }
}
