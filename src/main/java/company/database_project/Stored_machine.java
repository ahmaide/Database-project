package company.database_project;

import java.util.Map;

public class Stored_machine extends Machine {

    private String warehouse_name;
    public static Map<String, Stored_machine> list;

    public Stored_machine(String machine_id, String type_id, int shipment_id,  String warehouse_name) {
        super(machine_id, type_id, shipment_id);
        this.warehouse_name = warehouse_name;
    }

    public Stored_machine(String type_id, int shipment_id,  String warehouse_name) {
        super(type_id, shipment_id);
        this.warehouse_name = warehouse_name;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }
}
