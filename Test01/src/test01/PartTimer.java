package test01;

class PartTimer extends Person {
    public PartTimer(String name) {
        super(name);
    }

    @Override
    public void introduce() {
        System.out.println("Hello, I am a PartTimer named " + getName());
    }

    public void work() {
        System.out.println(getName() + " is working part-time.");
    }

    public void requestTimeOff() {
        System.out.println(getName() + " is requesting time off.");
    }
}