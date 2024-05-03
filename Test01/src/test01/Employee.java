package test01;

public class Employee {
    private String name;
    private String position;

    // Constructor to initialize the Employee class
    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    // Method to get the name of the employee
    public String getName() {
        return name;
    }

    // Method to get the position of the employee
    public String getPosition() {
        return position;
    }

    // You may add other methods related to all employees here, such as methods for handling work hours, reporting, etc.
}
