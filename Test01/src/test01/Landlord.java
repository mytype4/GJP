package test01;

public class Landlord {
    private String name;

    public Landlord(String name) {
        this.name = name;
    }

    public void collectRent(Owner owner, double amount) {
        System.out.println("Landlord " + name + " collected $" + amount + " in rent from " + owner.getName());
    }

    public void performMaintenance(Restaurant tenant) {
        System.out.println("Landlord " + name + " is performing maintenance for " + tenant.getName());
    }

	public String getName() {
		return name;
	}
}