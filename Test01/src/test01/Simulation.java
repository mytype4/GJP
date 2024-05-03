package test01;

public class Simulation {
    public static void main(String[] args) {
        Restaurant myRestaurant = new Restaurant("Happy Meals", "1234 Food St.");
        Menu myMenu = new Menu("Cheeseburger", 2.99, "Delicious cheeseburger");
        myRestaurant.addMenu(myMenu);

        Owner owner = new Owner("Alice");
        Customer customer = new Customer("John Doe", "123-456-7890");
        Landlord landlord = new Landlord("Mr. Smith");

        PartTimer partTimer = new PartTimer("Bob", "Waiter", 20);

        owner.hireStaff(myRestaurant, "Waiter", partTimer.getName());
        owner.interactWithLandlord(landlord, 1200);

        partTimer.serveCustomer(customer, myMenu);
        partTimer.prepareFood();

        customer.placeOrder(myRestaurant, myMenu);
        customer.leaveFeedback(myRestaurant, "Great food and service!");
    }
}
