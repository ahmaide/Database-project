package company.database_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    private String name;
    private String address;
    private String type_building;
    private int floors;
    private Map<String, Machine> machines_list;
    public static Map<String, Warehouse> list;

    public Warehouse(String name, String address, String type_building, int floors) {
        this.name = name;
        this.address = address;
        this.type_building = type_building;
        this.floors = floors;
        this.machines_list = new HashMap<String, Machine>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_building() {
        return type_building;
    }

    public void setType_building(String type_building) {
        this.type_building = type_building;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public Map<String, Machine> getMachines_list() {
        return machines_list;
    }

    public void addToMachines_list(Machine machine) {
        this.machines_list.put(machine.machine_id, machine);
    }

    public void deleteFromMachine_list(String machine){
        this.machines_list.remove(machine);
    }
}

