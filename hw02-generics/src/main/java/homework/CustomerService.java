package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {
    private final Comparator<Customer> comparator = Comparator.comparingLong(Customer::getScores);

    private final NavigableMap<Customer, String> customerMap = new TreeMap<>(comparator);

    public Map.Entry<Customer, String> getSmallest() {
        return getCopyOfEntry(customerMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        return getCopyOfEntry(customerMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerMap.put(customer, data);
    }

    private Map.Entry<Customer, String> getCopyOfEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        TreeMap<Customer, String> tmpMap = new TreeMap<>(comparator);
        tmpMap.put(new Customer(entry.getKey()), entry.getValue());
        return tmpMap.firstEntry();
    }
}
