package company.database_project;

public class Sold_machine extends Machine {

    private int customer_id;

    public Sold_machine(int order_id, String order_date, String machine_type, String pay_method, double discount, int customer_id, int worker_id, int customer_id1) {
        super(order_id, order_date, machine_type, pay_method, discount, customer_id, worker_id);
        this.customer_id = customer_id1;
    }

    @Override
    public int getCustomer_id() {
        return customer_id;
    }

    @Override
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
