package homework;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private TreeMap<Customer, String> customers = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        final Map.Entry<Customer, String> e = customers.firstEntry();
        return copyCustomer(e);
        //return null; // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyCustomer(customers.higherEntry(customer));
        //return null; // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Map.Entry<Customer, String> copyCustomer(Map.Entry<Customer, String> e) {
        if(e !=null) {
            Customer customer = e.getKey();
            return Map.entry(new Customer(customer.getId(), customer.getName(), customer.getScores()), e.getValue());
        }
        return e;
    }

}
