package test01;

public abstract class Person {
    private static int nextId = 1000000;
    private final int id;
    private final String name;
    private double bankAccount = 1000000;  // 초기 잔고 설정
    protected int x, y;  // 좌표 추가

    public Person(String name) {
        this.id = ++nextId;
        this.name = name;
        introduce();
    }
    
    public abstract void introduce();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public Double getBankAccount() {
        return bankAccount;
    }
    
    public void pay(Person recipient, double amount) {
        if (amount <= 0) {
            System.out.println("Unable to send negative value.");
            return;
        }
        if (this.bankAccount >= amount) {
            this.bankAccount -= amount;
            recipient.bankAccount += amount;
            System.out.println(this.name + " has paid " + amount + " to " + recipient.getName());
        } else {
            System.out.println("You don't have enough money.");
        }
    }


}
