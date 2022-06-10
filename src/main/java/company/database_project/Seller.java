package company.database_project;

import java.util.ArrayList;
import java.util.Map;

public class Seller extends Employee {

    public static Map<Integer, Seller> active;
    public static Map<Integer, Seller> list;
    public static Seller current;
    public static ArrayList<String> notActive;
    public static int m = 0;

    public Seller(int worker_id, String worker_name, int worker_actualID, int worker_phone, boolean activity, String start_date, String leave_date) {
        super(worker_id, worker_name, worker_actualID, worker_phone, activity, start_date, leave_date);
    }
}
