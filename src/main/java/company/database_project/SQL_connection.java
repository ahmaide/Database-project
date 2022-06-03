package company.database_project;

import java.sql.*;
import java.util.HashMap;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class SQL_connection {
    private static String dbURL;
    private static String dbUsername = "root"; // database username
    private static String dbPassword = "root1234"; // database password
    private static String URL = "127.0.0.1"; // server location
    private static String port = "3306"; // port that mysql uses
    private static String dbName = "final_project"; //database on mysql to connect to
    private static Connection con;
    public static int m =0;

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

    public static void storeUsers() throws SQLException, ClassNotFoundException {
        Users.list = new HashMap<String, Users>();
        connectDB();
        String sql = "select * from users";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Users.list.put(rs.getString(1), new Users(rs.getString(1), rs.getString(2)));
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeWarehouse() throws SQLException, ClassNotFoundException {
        Warehouse.list = new HashMap<String, Warehouse>();
        connectDB();
        String sql = "select * from warehouse";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Warehouse.list.put(rs.getString(1), new Warehouse(rs.getString(1), rs.getString(2),
                    rs.getString(3), Integer.parseInt(rs.getString(4))));
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeShipment() throws SQLException, ClassNotFoundException {
        Shipment.list = new HashMap<Integer, Shipment>();
        connectDB();
        String sql = "select * from shipment";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Shipment.list.put(Integer.parseInt(rs.getString(1)), new Shipment(Integer.parseInt(rs.getString(1)),
                    Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4),
                    Double.parseDouble(rs.getString(5))));
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeCustomers() throws SQLException, ClassNotFoundException {
        Customer.list = new HashMap<Integer, Customer>();
        connectDB();
        String sql = "select * from customer";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Customer.list.put(Integer.parseInt(rs.getString(1)), new Customer(Integer.parseInt(rs.getString(1)), rs.getString(2),
                    rs.getString(3), rs.getString(4), Integer.parseInt(rs.getString(5))));
            System.out.println(rs.getString(1) + " " + rs.getString(2));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeDrink() throws SQLException, ClassNotFoundException {
        Drinks.list = new HashMap<String, Drinks>();
        connectDB();
        String sql = "select * from drink";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Drinks.list.put(rs.getString(1), new Drinks(rs.getString(1), rs.getString(2),
                    rs.getString(3), Double.parseDouble(rs.getString(4))));
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeEmployee() throws SQLException, ClassNotFoundException {
        Driver.active = new HashMap<Integer, Driver>();
        Driver.list = new HashMap<Integer, Driver>();
        Seller.active = new HashMap<Integer, Seller>();
        Seller.list = new HashMap<Integer, Seller>();
        connectDB();
        String sql = "select * from worker";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            boolean activity = false;
            if(rs.getString(6)=="1")
                activity = true;
            if(rs.getString(5).equals("driver")){
                Driver d = new Driver(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
                        Integer.parseInt(rs.getString(4)), activity, rs.getString(7), rs.getString(8));
                Driver.list.put(d.getWorker_id(), d);
                if (activity)
                    Driver.active.put(d.getWorker_id(), d);
            }
            else if(rs.getString(5).equals("sales")){
                Seller s = new Seller(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
                        Integer.parseInt(rs.getString(4)), activity, rs.getString(7), rs.getString(8));
                Seller.list.put(s.getWorker_id(), s);
                if (activity)
                    Seller.active.put(s.getWorker_id(), s);
            }
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(5));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeMachines() throws SQLException, ClassNotFoundException {
        Stored_machine.list = new HashMap<String, Machine>();
        Sold_machine.list = new HashMap<String, Machine>();
        connectDB();
        String sql = "select m.machine_id, m.type_id, m.shipment_id, sm.warehouse_name " +
                "from machine m, stored_machine sm where m.machine_id = sm.machine_id;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Stored_machine.list.put(rs.getString(1), new Stored_machine(rs.getString(1),
                    rs.getString(2), Integer.parseInt(rs.getString(3)), rs.getString(4)));
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(4));
            Warehouse.list.get(rs.getString(4)).addToMachines_list(Stored_machine.list.get(rs.getString(1)));
            Shipment.list.get(Integer.parseInt(rs.getString(3))).addToMachines_list(Stored_machine.list.get(rs.getString(1)));
        }

        sql = "select m.machine_id, m.type_id, m.shipment_id, sm.customer_id " +
                "from machine m, sold_machine sm where m.machine_id = sm.machine_id;";
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()){
            Sold_machine.list.put(rs.getString(1), new Sold_machine(rs.getString(1),
                    rs.getString(2), Integer.parseInt(rs.getString(3)), Integer.parseInt(rs.getString(4))));
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(4));
            Shipment.list.get(Integer.parseInt(rs.getString(3))).addToMachines_list(Sold_machine.list.get(rs.getString(1)));
        }

        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeDelivery() throws SQLException, ClassNotFoundException {
        Delivery.list = new HashMap<String, Delivery>();
        connectDB();
        String sql = "select * from delivery";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Delivery.list.put(rs.getString(1), new Delivery(rs.getString(1), Integer.parseInt(rs.getString(2)),
                    Double.parseDouble(rs.getString(3)), rs.getString(4)));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeOrders() throws SQLException, ClassNotFoundException {
        Order.notSet = new HashMap<Integer, Order>();
        Arranged_Order.not_Passed = new HashMap<Integer, Order>();
        Arranged_Order.passed_list = new HashMap<Integer, Order>();

        connectDB();
        String sql = "select * from orders where arranged = false";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Order.notSet.put(Integer.parseInt(rs.getString(1)), new Order(Integer.parseInt(rs.getString(1)),
                    rs.getString(2), rs.getString(3), rs.getString(4), Double.parseDouble(rs.getString(5)),
                    Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7)), false));

            System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)
            + " " + rs.getString(4) + " " + rs.getString(5) + rs.getString(6));
        }

        sql = "select o.order_id, o.order_date, o.type_id, o.pay_method, o.discount, o.customer_id, o.worker_id, " +
                " ao.delivery_date, ao.machine_id, ao.passed from orders o, arranged_orders ao  " +
                "    where o.order_id = ao.order_id";

        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            boolean passed;
            if(rs.getString(10) == "0"){
                passed = false;
            }
            else{
                passed = true;
            }
            Arranged_Order o = new Arranged_Order(Integer.parseInt(rs.getString(1)),
                    rs.getString(2), rs.getString(3), rs.getString(4), Double.parseDouble(rs.getString(5)),
                    Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7)), rs.getString(8),
                    rs.getString(9), passed);
            Delivery.list.get(o.getDelivery_date()).addToOrders(o);
            if (passed)
                Arranged_Order.passed_list.put(o.getOrder_id(), o);
            else
                Arranged_Order.not_Passed.put(o.getOrder_id(), o);
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeType_of_machine() throws SQLException, ClassNotFoundException {
        Machine_type.list = new HashMap<String, Machine_type>();
        connectDB();
        String sql = "select * from machine_type";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Machine_type.list.put(rs.getString(1), new Machine_type(rs.getString(1), Double.parseDouble(rs.getString(2)),
                    rs.getString(3), rs.getString(4)));
        }

        sql = "select * from drink_machine";
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()){
            Machine_type.list.get(rs.getString(1)).addToDrink_list(Drinks.list.get(rs.getString(2)));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static String dateToday(){
        Date d = new Date();
        String s = Integer.toString(d.getYear() + 1900) + "=" + Integer.toString(d.getMonth() + 1) + "=" + Integer.toString(d.getDate());
        return s;
    }

    public static void addNewUser(String username, String password) throws SQLException, ClassNotFoundException {
        Users.list.put(username, new Users(username, password));
        connectDB();
        ExecuteStatement("insert into users(username, password0) " +
                "values('" + username + "', '" + password +"');");
        con.close();

    }

    public static void removeUser(String username) throws SQLException, ClassNotFoundException {
        Users u = Users.list.get(username);
        Users.list.remove(u);
        connectDB();
        ExecuteStatement("delete from users where username ='" + username +"'; ");
        con.close();
    }

    public static void changePassword(String password) throws SQLException, ClassNotFoundException {
        Users.currentUser.setPassword(password);
        connectDB();
        ExecuteStatement("update users set password0 = '" + password + "' where username = '"
                + Users.currentUser.getUsername() + "';");
        con.close();
    }

}
