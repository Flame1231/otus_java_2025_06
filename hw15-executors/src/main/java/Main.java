import counter.Counter;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        new Thread(() -> counter.process(true), "First").start();
        new Thread(() -> counter.process(false), "Second").start();
    }
}
