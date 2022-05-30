package company.database_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Type_of_machine {
    private String id;
    private double price;
    private String cups;
    private String color;
    private int n_types;
    private Map<String, Drinks> drink_list;
    public static Map<String, Type_of_machine> list;

    public Type_of_machine(String id, double price, String cups,String color,int n_types) {
        this.id = id;
        this.price = price;
        this.cups = cups;
        this.color = color;
        this.n_types = n_types;
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

    public int getN_types() {
        return n_types;
    }

    public void setN_types(int n_types) {
        this.n_types = n_types;
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

