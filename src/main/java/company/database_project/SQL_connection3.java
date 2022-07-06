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

    public static void editDelivery(String worker_id, String expences, String city) throws SQLException, ClassNotFoundException {
        if (worker_id!=""){
            int workerid;
            workerid= Integer.parseInt(worker_id);
            connectDB();
            ExecuteStatement("update delivery set worker_id = " + worker_id + " where worker_id = "
                    + Delivery.current.getDate() + ";");
            Delivery.current.setWorker_id(workerid);
            con.close();
        }
        if (expences!=""){
            double expence;
            expence= Double.parseDouble(expences);
            connectDB();
            ExecuteStatement("update delivery set expences = " + expence + " where worker_id = "
                    + Delivery.current.getWorker_id() + ";");
            Delivery.current.setExpences(expence);
            con.close();
        }
        if (city!=""){
            connectDB();
            ExecuteStatement("update delivery set city = '" + city + "' where worker_id = "
                    + Delivery.current.getWorker_id() + ";");
            Delivery.current.setCity(city);
            con.close();
        }

    }
    public static void addDelivery(String date, String worker_id, String expences, String city) throws SQLException, ClassNotFoundException {
        int workerid;
        workerid=Integer.parseInt(worker_id);
        double expence;
        expence=Double.parseDouble(expences);
        connectDB();
        ExecuteStatement("insert into delivery values('" + date + "', " + worker_id + ", " + expences + ", '" + city +  "' );");
        con.close();
        Delivery.list.put(date, new Delivery(date, workerid, expence, city));
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
        Delivery.list.remove(Delivery.current.getDate());
        System.out.println("'delete from delivery where delivery_date = '" + Delivery.current.getDate() + "';'");
        ExecuteStatement("delete from delivery where delivery_date = '" + Delivery.current.getDate() + "';");
        con.close();

    }
    public static void addDrink(String  drink_id, String drink_name, String country, String price) throws SQLException, ClassNotFoundException {
        double prices;
        prices= Double.parseDouble(price);
        connectDB();
        ExecuteStatement("insert into drink values('" + drink_id + "', '" + drink_name + "', '" + country + "', " + prices + ");");
        con.close();
        Drinks.list.put(drink_id, new Drinks(drink_id, drink_name, country, prices));
    }

    public static void addType(String type_id, String price, String cups, String color)throws SQLException, ClassNotFoundException{
        double prices;
        prices= Double.parseDouble(price);
        connectDB();
        ExecuteStatement("insert into machine_type values('" + type_id + "', " + price + ", '" + cups + "', '" + color + "');");
        con.close();
        Machine_type.list.put(type_id, new Machine_type(type_id, prices, cups, color));
    }
}
