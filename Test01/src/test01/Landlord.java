package test01;

public class Landlord {
    private String name;

    public Landlord(String name) {
        this.name = name;
    }

    public void collectRent(Restaurant tenant, double amount) {
        System.out.println("Landlord " + name + " collected $" + amount + " in rent from " + tenant.getName());
        // 임대료 수집 로직 구현
    }

    public void performMaintenance(Restaurant tenant) {
        System.out.println("Landlord " + name + " is performing maintenance for " + tenant.getName());
        // 유지 보수 로직 구현
    }

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}