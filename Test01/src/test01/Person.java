package test01;

public class Person {
    private static int nextId = 1000000; // 시작 번호 설정
    private String id;
    private String name;
    private int wealth;  // 사람의 재산

    public Person(String name) {
        this.id = "Person" + nextId++; // 고유 ID 할당
        this.name = name;
        this.wealth = 1000000;  // 초기 재산을 100만원으로 설정
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // 재산 가져오기
    public int getWealth() {
        return wealth;
    }

    // 재산 설정
    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    // 음식점 가기
    public void goToRestaurant() {
        System.out.println(name + " is going to a restaurant.");
    }

    // 음식 주문하기
    public void orderFood(int price) {
        if (price <= wealth) {
            System.out.println(name + " has ordered food costing " + price + " won.");
        } else {
            System.out.println(name + " cannot afford to order food costing " + price + " won.");
        }
    }

    // 먹기
    public void eat() {
        System.out.println(name + " is eating.");
    }

    // 계산하기
    public void pay(int amount) {
        if (amount <= wealth) {
            wealth -= amount; // 금액만큼 재산에서 차감
            System.out.println(name + " has paid " + amount + " won. Remaining wealth: " + wealth + " won.");
        } else {
            System.out.println(name + " does not have enough money to pay " + amount + " won.");
        }
    }

    // 객체 정보를 쉽게 확인하기 위한 toString 메서드 오버라이드
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Wealth: " + wealth;
    }

    // Person 객체를 자동으로 생성하고 반환하는 팩토리 메소드
    public static Person createPerson(String name) {
        return new Person(name);
    }
}
