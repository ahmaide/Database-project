package company.database_project;

import java.util.Map;

public class Arranged_Order extends Order {

    String machine_id;
    String delivery_date;
    boolean passed;
    public static Map<Integer, Order> not_Passed;
    public static Map<Integer, Order> passed_list;

    public Arranged_Order(int order_id, String order_date, String machine_type, String pay_method, double discount, int customer_id,
           int worker_id, String delivery_date, String machine_id, boolean passed) {

        super(order_id, order_date, machine_type, pay_method, discount, customer_id, worker_id, true);
        this.machine_id = machine_id;
        this.delivery_date = delivery_date;
        this.passed = passed;
    }

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

}
