package test01;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    private static SimulationManager instance = null;

    private List<Customer> customers = new ArrayList<>();
    private List<Owner> owners = new ArrayList<>();
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<Menu> menuList = new ArrayList<>();
    private OrderDB orderDB;

    private SimulationManager() {
        orderDB = new OrderDB();
    }

    public static SimulationManager getInstance() {
        if (instance == null) {
            instance = new SimulationManager();
        }
        return instance;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public Owner owner(String name) {
        for (Owner owner : owners) {
            if (owner.getName().equals(name)) {
                return owner;
            }
        }
        System.out.println("Error: No owner found with the name " + name);
        return null;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void addMenu(Menu menu) {
        menuList.add(menu);
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public OrderDB getOrderDB() {
        return orderDB;
    }
}