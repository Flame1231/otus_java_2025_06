import counter.Counter;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Thread firstThread = new Thread(counter::print, "first");
        Thread secondThread = new Thread(counter::answer, "second");

        firstThread.start();
        secondThread.start();
    }
}
