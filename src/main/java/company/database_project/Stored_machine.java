package company.database_project;

public class Stored_machine extends Machine {

    private String warehouse_name;

    public Stored_machine(int order_id, String order_date, String machine_type, String pay_method, double discount, int customer_id, int worker_id, String warehouse_name) {
        super(order_id, order_date, machine_type, pay_method, discount, customer_id, worker_id);
        this.warehouse_name = warehouse_name;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }
}
