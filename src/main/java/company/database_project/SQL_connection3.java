package company.database_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

public class SQL_connection3 {
    private static String dbURL;
    private static String dbUsername = "root"; // database username
    private static String dbPassword = "root1234"; // database password
    private static String URL = "127.0.0.1"; // server location
    private static String port = "3306"; // port that mysql uses
    private static String dbName = "final_project"; //database on mysql to connect to
    private static Connection con;
    public static int m =0;
    public static int n = 0;

    public static void ExecuteStatement(String SQL) throws SQLException {

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        }
        catch(SQLException s) {
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
        con = DriverManager.getConnection (dbURL, p);
    }


    public static void deleteDelivery() throws ClassNotFoundException, SQLException{
        connectDB();
        for(Map.Entry m : Delivery.current.getOrders().entrySet()){
            Arranged_Order o = (Arranged_Order) m.getValue();
            if(o.isPassed())
                Arranged_Order.passed_list.remove(o.getOrder_id());
            else
                Arranged_Order.not_Passed.remove(o.getOrder_id());
            Order.all.remove(o.getOrder_id());
            ExecuteStatement("delete from arranged_orders where order_id = " + o.getOrder_id() + ";");
            Order O = new Order(o.getOrder_id(), o.getOrder_date(), o.getMachine_type(), o.getPay_method(), o.getDiscount(),
                    o.getCustomer_id(), o.getWorker_id(), false);
            Order.all.put(O.getOrder_id(),O);
            Order.notSet.put(O.getOrder_id(), O);
            ExecuteStatement("delete from sold_machine where machine_id = '" + o.getMachine_id() + "';");
            Sold_machine sm = Sold_machine.list.get(o.getMachine_id());
            Sold_machine.list.remove(sm.getMachine_id());
            Stored_machine s = new Stored_machine(sm.getMachine_id(), sm.getType_id(), sm.getShipment_id(), "Main warehouse");
            ExecuteStatement("insert into stored_machine values('" + s.getMachine_id() + "', 'Main warehouse');");
            Warehouse.list.get("Main warehouse").addToMachines_list(s);
        }
        Delivery.list.remove(Delivery.current);
        ExecuteStatement("delete from delivery where delivery_date = " + Delivery.current.getDate() + ";");
        con.close();
    }
}