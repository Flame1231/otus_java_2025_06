import proxy.Ioc;
import services.TestLoggingInterface;

@SuppressWarnings("java:S106")
public class Main {
    public static void main(String[] args) {
        TestLoggingInterface testClass = Ioc.createMyClass();
        testClass.calculation(1);
        testClass.calculation(1, 2);
        testClass.calculation(1, 2, 3);
    }
}
