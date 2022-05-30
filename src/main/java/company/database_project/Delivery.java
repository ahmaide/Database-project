package company.database_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Delivery {
    private int date;
    private int worker_id;
    private double expences;
    private Map<Integer, Order> orders;
    public static Map<String, Delivery> list;
    public static ArrayList<Integer> months;

    public Delivery(int date, int worker_id, double expences) {
        this.date = date;
        this.worker_id = worker_id;
        this.expences = expences;
        this.orders = new HashMap<Integer, Order>();
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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

    public void addToOrders(Map<Integer, Order> orders, Order o) {
        this.orders.put(o.getOrder_id(), o);
    }
}
