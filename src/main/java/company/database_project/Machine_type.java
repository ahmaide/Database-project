package company.database_project;

import java.util.HashMap;
import java.util.Map;

public class Machine_type {
    private String type_id;
    private double price;
    private String cups;
    private String color;
    private int last =0;
    public static int m = 0;
    public static Machine_type current;
    private Map<String, Drinks> drink_list;
    public static Map<String, Machine_type> list;

    public Machine_type(String type_id, double price, String cups, String color) {
        this.type_id = type_id;
        this.price = price;
        this.cups = cups;
        this.color = color;
        this.drink_list = new HashMap<String, Drinks>();
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
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

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
}

