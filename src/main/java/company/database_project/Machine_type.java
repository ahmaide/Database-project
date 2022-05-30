package company.database_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Machine_type {
    private String id;
    private double price;
    private String cups;
    private String color;
    private Map<String, Drinks> drink_list;
    public static Map<String, Machine_type> list;

    public Machine_type(String id, double price, String cups, String color) {
        this.id = id;
        this.price = price;
        this.cups = cups;
        this.color = color;
        this.drink_list = new HashMap<String, Drinks>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCups() {
        return cups;
    }

    public void setCups(String cups) {
        this.cups = cups;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Map<String, Drinks> getDrink_list() {
        return drink_list;
    }

    public void addToDrink_list(Drinks drink) {
        this.drink_list.put(drink.getDrink_id(), drink);
    }

    public void removeFromDrink_list(String drink){
        this.drink_list.remove(drink);
    }
}

