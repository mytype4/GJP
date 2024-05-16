package test01;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static void main(String[] args) {
    	List<Customer> customers = new ArrayList<>();
    	List<Owner> owners = new ArrayList<>();
    	
//    	Me me = new Me("광운이");
    	// Customer 객체 리스트 생성
        customers.add(new Customer("Customer1"));
        customers.add(new Customer("Customer2"));
        customers.add(new Customer("Customer3"));

        // Owner 객체 리스트 생성
        owners.add(new Owner("Owner1"));
        owners.add(new Owner("Owner2"));
        owners.add(new Owner("Owner3"));
        
        customers.get(0).introduce();
        
        owners.get(0).createRestaurant("광운이네");
        owners.get(0).createRestaurant("광운광운이");
        owners.get(0).addMenuToRestaurant("광운이네", "맛있는 음식1", 10000);
        owners.get(0).addMenuToRestaurant("광운광운이", "떡볶이", 110000);
        
        owners.get(1).createRestaurant("광운이네");
        owners.get(1).createRestaurant("광운광운이");
    }
}
