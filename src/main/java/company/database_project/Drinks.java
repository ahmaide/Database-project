package company.database_project;



import java.util.ArrayList;
import java.util.Map;

public class Drinks {
    
    private String drink_id;
    private String drink_name;
    private String country;
    public static Drinks current;
    public static int d = 0;
    private double price;
    public static Map<String, Drinks> list;
    public static ArrayList <String> notActive;

    public Drinks(String drink_id, String drink_name, String country, double price){
        this.drink_id = drink_id;
        this.drink_name = drink_name;
        this.country = country;
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

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

