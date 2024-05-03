package test01;

public class Owner {
    private String name;

    public Owner(String name) {
        this.name = name;
    }

    public void hireStaff(Restaurant restaurant, String position, String candidateName) {
        System.out.println(name + " has hired " + candidateName + " as a " + position + " at " + restaurant.getName());
        // 추가 고용 로직 구현
    }

    public void interactWithLandlord(Landlord landlord, double rent) {
        System.out.println(name + " is negotiating the rent of " + rent + " with " + landlord.getName());
        // 임대료 협상 로직 구현
    }
}