package test01;

// 추상 클래스 Person
public abstract class Person {
    private static int nextId = 1000000;
    private final int id;
    private final String name;

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

    public abstract void introduce();
}