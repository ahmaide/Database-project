package company.database_project;

import java.util.Map;

public class Sold_machine extends Machine {

    private int customer_id;
    public static Map<String, Sold_machine> list;

    public Sold_machine(String machine_id, String type_id ,int shipment_id, int customer_id) {
        super(machine_id, type_id, shipment_id);
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
