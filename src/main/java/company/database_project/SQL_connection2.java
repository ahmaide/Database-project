package company.database_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQL_connection2 {
    private static String dbURL;
    private static String dbUsername = "root"; // database username
    private static String dbPassword = "root1234"; // database password
    private static String URL = "127.0.0.1"; // server location
    private static String port = "3306"; // port that mysql uses
    private static String dbName = "final_project"; //database on mysql to connect to
    private static Connection con;

    public static void ExecuteStatement(String SQL) throws SQLException {

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");

        }
    }

    public static void connectDB() throws ClassNotFoundException, SQLException {
        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(dbURL, p);
    }
    public static void addDriver(String worker_id, String worker_name, String worker_actualID, String worker_phone,String Activity, String start_date, String leave_date) throws SQLException, ClassNotFoundException {
        int workerid;
        workerid= Integer.parseInt(worker_id);
        int workeractualid;
        workeractualid= Integer.parseInt(worker_actualID);
        int workerphone;
        workerphone= Integer.parseInt(worker_phone);
        boolean activity;
        activity= Boolean.parseBoolean(Activity);
        if (Driver.notActive.contains(worker_name)) {
            connectDB();
            ExecuteStatement("update customer set activity = true where worker_id = '" + workerid + "';");
            ExecuteStatement("update customer set worker_name = '" + worker_name + " ' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set worker_actualID = '" + workeractualid + "' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set worker_phone = '" + workerphone + "' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set start_date = " + start_date + " where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set leave_date = '" + leave_date + "' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set Activity = " + activity + " where worker_id = '" + worker_id + "';");
            con.close();
            Driver.list.put(workerid, new Driver(workerid, worker_name, workeractualid, workerphone,activity, start_date, leave_date));
            Driver.notActive.remove(worker_id);
        }
        connectDB();
        ExecuteStatement("insert into customer values('" + worker_id + "', '" + worker_name + "', '" + worker_actualID + "', " + worker_phone + "' , " + start_date + "'," + leave_date + "'," + Activity + ", 1);");
        con.close();
        Driver.list.put(workerid, new Driver(workerid, worker_name, workeractualid, workerphone,activity, start_date, leave_date));
    }
    public static void editDriver(String worker_name, String worker_actualID, String worker_phone,String Activity, String start_date, String leave_date) throws SQLException, ClassNotFoundException {
        if (worker_name != "") {
            connectDB();
            ExecuteStatement("update customer set worker_name = '" + worker_name + "' where worker_name = '"
                    + Driver.current.getWorker_id() + "';");
            Driver.current.setWorker_name(worker_name);
            con.close();
        }
        if (worker_actualID != "") {
            int workeractualID;
            workeractualID = Integer.parseInt(worker_actualID);
            connectDB();
            ExecuteStatement("update customer set worker_actualID = '" + worker_actualID + "' where worker_actualID = '"
                    + Driver.current.getWorker_id() + "';");
            Driver.current.setWorker_actualID(workeractualID);
            con.close();
        }
        if (worker_phone != "") {
            int workerphone;
            workerphone = Integer.parseInt(worker_phone);
            connectDB();
            ExecuteStatement("update customer set worker_phone = '" + worker_phone + "' where worker_phone = '"
                    + Driver.current.getWorker_id() + "';");
            Driver.current.setWorker_actualID(workerphone);
            con.close();
        }
        if (Activity != "") {
            boolean activity;
            activity = Boolean.parseBoolean(Activity);
            connectDB();
            ExecuteStatement("update customer set Activity = " + Activity + " where Activity = '"
                    + Driver.current.getWorker_id() + "';");
            Driver.current.setActivity(activity);
            con.close();
        }
        if (start_date != "") {
            connectDB();
            ExecuteStatement("update customer set start_date = '" + start_date + "' where start_date = '"
                    + Driver.current.getWorker_id() + "';");
            Driver.current.setStart_date(start_date);
            con.close();
        }
        if (leave_date != "") {
            connectDB();
            ExecuteStatement("update customer set leave_date = '" + leave_date + "' where leave_date = '"
                    + Driver.current.getWorker_id() + "';");
            Driver.current.setLeave_date(leave_date);
            con.close();
        }

    }
    public static void addSeller(String worker_id, String worker_name, String worker_actualID, String worker_phone,String Activity, String start_date, String leave_date) throws SQLException, ClassNotFoundException {
        int workerid;
        workerid= Integer.parseInt(worker_id);
        int workeractualid;
        workeractualid= Integer.parseInt(worker_actualID);
        int workerphone;
        workerphone= Integer.parseInt(worker_phone);
        boolean activity;
        activity= Boolean.parseBoolean(Activity);
        if (Driver.notActive.contains(worker_name)) {
            connectDB();
            ExecuteStatement("update customer set activity = true where worker_id = '" + workerid + "';");
            ExecuteStatement("update customer set worker_name = '" + worker_name + " ' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set worker_actualID = '" + workeractualid + "' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set worker_phone = '" + workerphone + "' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set start_date = " + start_date + " where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set leave_date = '" + leave_date + "' where worker_id = '" + worker_id + "';");
            ExecuteStatement("update customer set Activity = " + activity + " where worker_id = '" + worker_id + "';");
            con.close();
            Seller.list.put(workerid, new Seller(workerid, worker_name, workeractualid, workerphone,activity, start_date, leave_date));
            Seller.notActive.remove(worker_id);
        }
        connectDB();
        ExecuteStatement("insert into customer values('" + worker_id + "', '" + worker_name + "', '" + worker_actualID + "', " + worker_phone + "' , " + start_date + "'," + leave_date + "'," + Activity + ", 1);");
        con.close();
        Seller.list.put(workerid, new Seller(workerid, worker_name, workeractualid, workerphone,activity, start_date, leave_date));
    }
    public static void editSeller(String worker_name, String worker_actualID, String worker_phone,String Activity, String start_date, String leave_date) throws SQLException, ClassNotFoundException {
        if (worker_name != "") {
            connectDB();
            ExecuteStatement("update customer set worker_name = '" + worker_name + "' where worker_name = '"
                    + Seller.current.getWorker_id() + "';");
            Seller.current.setWorker_name(worker_name);
            con.close();
        }
        if (worker_actualID != "") {
            int workeractualID;
            workeractualID = Integer.parseInt(worker_actualID);
            connectDB();
            ExecuteStatement("update customer set worker_actualID = '" + worker_actualID + "' where worker_actualID = '"
                    + Seller.current.getWorker_id() + "';");
            Seller.current.setWorker_actualID(workeractualID);
            con.close();
        }
        if (worker_phone != "") {
            int workerphone;
            workerphone = Integer.parseInt(worker_phone);
            connectDB();
            ExecuteStatement("update customer set worker_phone = '" + worker_phone + "' where worker_phone = '"
                    + Seller.current.getWorker_id() + "';");
            Seller.current.setWorker_actualID(workerphone);
            con.close();
        }
        if (Activity != "") {
            boolean activity;
            activity = Boolean.parseBoolean(Activity);
            connectDB();
            ExecuteStatement("update customer set Activity = " + Activity + " where Activity = '"
                    + Seller.current.getWorker_id() + "';");
            Seller.current.setActivity(activity);
            con.close();
        }
        if (start_date != "") {
            connectDB();
            ExecuteStatement("update customer set start_date = '" + start_date + "' where start_date = '"
                    + Seller.current.getWorker_id() + "';");
            Seller.current.setStart_date(start_date);
            con.close();
        }
        if (leave_date != "") {
            connectDB();
            ExecuteStatement("update customer set leave_date = '" + leave_date + "' where leave_date = '"
                    + Seller.current.getWorker_id() + "';");
            Seller.current.setLeave_date(leave_date);
            con.close();
        }

    }

    public static void deleteDriver(Driver d, int num, Driver replacment) throws SQLException, ClassNotFoundException {
        if(num==1){
            Seller.list.remove(d.getWorker_id());
            connectDB();
            ExecuteStatement("update seller set activity = false where worker_id = '"
                    + d.getWorker_id() + "';");
            con.close();
        }
        else{

            Seller.list.remove(d.getWorker_id());
            connectDB();
            ExecuteStatement("update seller set activity = false where worker_id = '"
                    + d.getWorker_id() + "';");
            con.close();

        }
    }
}