package company.database_project;

import java.sql.*;
import java.util.HashMap;
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
            if(rs.getString(5).toLowerCase(Locale.ROOT) == "driver"){
                Driver d = new Driver(Integer.parseInt(rs.getString(1)), rs.getString(2), Integer.parseInt(rs.getString(3)),
                        Integer.parseInt(rs.getString(4)), activity, rs.getString(7), rs.getString(8));
                Driver.list.put(d.getWorker_id(), d);
                if (activity)
                    Driver.active.put(d.getWorker_id(), d);
            }
            else if(rs.getString(5).toLowerCase(Locale.ROOT) == "sales"){
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



}
