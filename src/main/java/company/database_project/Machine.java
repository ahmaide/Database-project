package company.database_project;

public abstract class Machine {
    String machine_id;
    int shipment_id;
    String type_id;

    public Machine(String machine_id, String type_id, int shipment_id) {
        this.machine_id = machine_id;
        this.shipment_id = shipment_id;
        this.type_id = type_id;
    }

    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public int getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(int shipment_id) {
        this.shipment_id = shipment_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
}
