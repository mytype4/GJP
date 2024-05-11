package test01;

// 추상 클래스 Person
public abstract class Person {
    private static int nextId = 1000000;
    private final int id;
    private final String name;
    private double bankAccount = 1000000;  //초기 잔고 설정

    public Person(String name) {
        this.id = ++nextId;
        this.name = name;
        introduce();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public Double getBankAccount() {
    	return bankAccount;
    }
    
    public void pay (Person recipent, double amount) {
    	if(amount <=0) {
    		System.out.println("Unable to send negative value.");
    		return;
    	}
    	if(this.bankAccount>=amount) {
    		this.bankAccount-=amount;
    		recipent.bankAccount+=amount;
    		System.out.println(this.name + " has paid " + amount + " to " + recipent.getName());
    	} else {
    		System.out.println("you don't have enough money.");
    	}
    }

    public abstract void introduce();
}