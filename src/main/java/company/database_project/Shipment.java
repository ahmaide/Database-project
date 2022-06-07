package company.database_project;

import java.util.HashMap;
import java.util.Map;

public class Shipment {

    private int shipment_id;
    private int Driver_id;
    private String Driver_name;
    private String shipment_date;
    private String warehouse_name;
    private double costs;
    private Map<String, Machine> machines_list;
    public static Map<Integer, Shipment> list;
    public static Map<String, Integer> dates;
    public static Shipment current;
    public static int last = 0;

    public Shipment(int shipment_id, int driver_id, String shipment_date, String warehouse_name, double costs) {
        this.shipment_id = shipment_id;
        this.Driver_id = driver_id;
        this.Driver_name = Driver.list.get(this.Driver_id).getWorker_name();
        this.shipment_date = shipment_date;
        this.warehouse_name = warehouse_name;
        this.costs = costs;
        this.machines_list = new HashMap<String, Machine>();
        if(shipment_id > last)
            last = shipment_id;
    }

    public Shipment(int driver_id, String shipment_date, String warehouse_name, double costs){
        this.Driver_id = driver_id;
        this.Driver_name = Driver.list.get(this.Driver_id).getWorker_name();
        this.shipment_date = shipment_date;
        this.warehouse_name = warehouse_name;
        this.costs = costs;
        this.machines_list = new HashMap<String, Machine>();
        this.shipment_id = last+1;
        last++;
    }

    public int getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(int shipment_id) {
        this.shipment_id = shipment_id;
    }

    public int getDriver_id() {
        return Driver_id;
    }

    public String getDriver_name(){ return  Driver_name;}

    public void setDriver_id(int driver_id) {
        Driver_id = driver_id;
        Driver_name = Driver.list.get(driver_id).getWorker_name();
    }

    public String getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(String shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }

    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public Map<String, Machine> getMachines_list() {
        return machines_list;
    }

    public void addToMachines_list(Machine machine) {
        this.machines_list.put(machine.getMachine_id(), machine);
        this.costs+= Machine_type.list.get(machine.getType_id()).getPrice();
    }

    public void deleteFromMachines_list(String machine){
        this.machines_list.remove(machine);
    }
}
