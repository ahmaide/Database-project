package company.database_project;

import java.util.Map;

public class Customer {

    private int customer_id;
    private String customer_name;
    private String customer_address;
    private String buisness_type;
    private int customer_phone;
    public static Map<Integer, Customer> list;


    public Customer(int customer_id, String customer_name, String customer_address, String buisness_type, int customer_phone){
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.buisness_type = buisness_type;
        this.customer_phone = customer_phone;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getBuisness_type() {
        return buisness_type;
    }

    public void setBuisness_type(String buisness_type) {
        this.buisness_type = buisness_type;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(int customer_phone) {
        this.customer_phone = customer_phone;
    }
}
