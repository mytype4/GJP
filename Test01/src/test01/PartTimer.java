package test01;

public class PartTimer extends Person{
    private int hoursWorked;
    
    public PartTimer(String name) {
    	super(name);
    }

    public PartTimer(String name, String position, int hoursWorked) {
        super(name);
    	this.hoursWorked = hoursWorked;
    }

    public void serveCustomer(Customer customer, Menu menu) {
        System.out.println(getName() + " is serving " + customer.getName() + " with " + menu.getDishName());
        // 고객 서빙 로직 구현
    }

    public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void prepareFood() {
        System.out.println(getName() + " is preparing food in the kitchen.");
        // 음식 준비 로직 구현
    }
}