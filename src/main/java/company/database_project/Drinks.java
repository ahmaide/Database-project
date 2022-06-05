package company.database_project;


import java.util.ArrayList;
import java.util.Map;

public class Drinks {
    public static ArrayList <String> notActive;
    private String drink_id;
    private String drink_name;
    private String company;
    public static Drinks current;
    public static int d = 0;
    private double price;
    public static Map<String, Drinks> list;

    public Drinks(String drink_id, String drink_name, String company, double price){
        this.drink_id = drink_id;
        this.drink_name = drink_name;
        this.company = company;
        this.price = price;
    }

    public String getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(String drink_id) {
        this.drink_id = drink_id;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }

    public String getCompany(String company) {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

