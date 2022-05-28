package company.database_project;

import java.util.Map;

public abstract class Employee {
    private int worker_id;
    private String worker_name;
    private int worker_actualID;
    private int worker_phone;
    private boolean activity;
    private String start_date;
    private String leave_date;

    public Employee(int worker_id, String worker_name, int worker_actualID, int worker_phone, boolean activity,
                    String start_date, String leave_date) {
        this.worker_id = worker_id;
        this.worker_name = worker_name;
        this.worker_actualID = worker_actualID;
        this.worker_phone = worker_phone;
        this.activity = activity;
        this.start_date = start_date;
        this.leave_date = leave_date;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public int getWorker_actualID() {
        return worker_actualID;
    }

    public void setWorker_actualID(int worker_actualID) {
        this.worker_actualID = worker_actualID;
    }

    public int getWorker_phone() {
        return worker_phone;
    }

    public void setWorker_phone(int worker_phone) {
        this.worker_phone = worker_phone;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }
}
