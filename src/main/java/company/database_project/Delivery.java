package company.database_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Delivery {
    private String date;
    private int worker_id;
    private double expences;
    private Map<Integer, Order> orders;
    private String city;
    public static Delivery current;
    public static Map<String, Delivery> list;
    public static ArrayList<Integer> months;
    private Map<Integer, Order> orders_list;
    public static ArrayList<String> notActive;
    public static int m = 0;

    public Delivery(String date, int worker_id, double expences, String city) {
        this.date = date;
        this.worker_id = worker_id;
        this.expences = expences;
        this.orders = new HashMap<Integer, Order>();
        this.city = city;
    }

    public void removeFromOrders_list(int o) {
        this.orders_list.remove(o);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public double getExpences() {
        return expences;
    }

    public void setExpences(double expences) {
        this.expences = expences;
    }

    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public void addToOrders(Order o) {
        this.orders.put(o.getOrder_id(), o);
    }

    public void deleteFromOrders(int order){
        this.orders.remove(order);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<Integer, Order> getOrders_list() {
        return orders_list;
    }

    public void addToOrders_list(Order o) {
        this.orders_list.put(o.getOrder_id(), o);
    }
}

