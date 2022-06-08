package company.database_project;

import java.util.ArrayList;
import java.util.Map;

public class Order {
    private int order_id;
    private String order_date;
    private String machine_type;
    private String pay_method;
    private double discount;
    private int customer_id;
    private int worker_id;
    private boolean arranged;
    public static Map<Integer, Order> notSet;
    public static Map<Integer, Order> all;
    public static int last = 0;
    public static ArrayList<Integer> months;

    public Order(int order_id, String order_date, String machine_type, String pay_method, double discount, int customer_id, int worker_id,
                 boolean arranged) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.machine_type = machine_type;
        this.pay_method = pay_method;
        this.discount = discount;
        this.customer_id = customer_id;
        this.worker_id = worker_id;
        this.arranged = arranged;
        if(order_id>last)
            last=order_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getMachine_type() {
        return machine_type;
    }

    public void setMachine_type(String machine_type) {
        this.machine_type = machine_type;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public boolean isArranged() {
        return arranged;
    }

    public void setArranged(boolean arranged) {
        this.arranged = arranged;
    }
}
