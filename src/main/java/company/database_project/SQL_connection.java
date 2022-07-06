package company.database_project;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public static void checkOrderDates() throws SQLException, ClassNotFoundException {
        String date = Dates.dateToday();
        connectDB();
        ExecuteStatement("update arranged_orders set passed = true where delivery_date < '" + date + "' and passed = false;");
        con.close();
    }

    public static void storeUsers() throws SQLException, ClassNotFoundException {
        Users.list = new HashMap<String, Users>();
        connectDB();
        String sql = "select * from users";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Users.list.put(rs.getString(1), new Users(rs.getString(1), rs.getString(2)));
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeWarehouse() throws SQLException, ClassNotFoundException {
        Warehouse.list = new HashMap<String, Warehouse>();
        Warehouse.notActive = new ArrayList<String>();
        connectDB();
        String sql = "select * from warehouse";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            if(rs.getString(5).equals("1")) {
                Warehouse.list.put(rs.getString(1), new Warehouse(rs.getString(1), rs.getString(2),
                        rs.getString(3), Integer.parseInt(rs.getString(4))));

            }
            else{
                Warehouse.notActive.add(rs.getString(1));
            }
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeShipment() throws SQLException, ClassNotFoundException {
        Shipment.list = new HashMap<Integer, Shipment>();
        Shipment.dates = new HashMap<String, Integer>();
        connectDB();
        String sql = "select * from shipment";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Shipment.list.put(Integer.parseInt(rs.getString(1)), new Shipment(Integer.parseInt(rs.getString(1)),
                    Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4),
                    Double.parseDouble(rs.getString(5))));
            if(!Shipment.dates.containsKey(Dates.stringMonth(rs.getString(3)))){
                Shipment.dates.put(Dates.stringMonth(rs.getString(3)), Dates.stripDay(rs.getString(3)));
            }
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
            if(rs.getString(6).equals("1"))
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
        }
        rs.close();
        stmt.close();
        con.close();
    }

    public static void storeMachines() throws SQLException, ClassNotFoundException {
        Stored_machine.list = new HashMap<String, Stored_machine>();
        Sold_machine.list = new HashMap<String, Sold_machine>();
        connectDB();
        String sql = "select m.machine_id, m.type_id, m.shipment_id, sm.warehouse_name " +
                "from machine m, stored_machine sm where m.machine_id = sm.machine_id;";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Stored_machine.list.put(rs.getString(1), new Stored_machine(rs.getString(1),
                    rs.getString(2), Integer.parseInt(rs.getString(3)), rs.getString(4)));
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
        Order.all = new HashMap<Integer, Order>();
        Arranged_Order.not_Passed = new HashMap<Integer, Order>();
        Arranged_Order.passed_list = new HashMap<Integer, Order>();
        Arranged_Order.dates = new HashMap<String, Integer>();
        connectDB();
        String sql = "select * from orders where arranged = false";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            Order o = new Order(Integer.parseInt(rs.getString(1)),
                    rs.getString(2), rs.getString(3), rs.getString(4), Double.parseDouble(rs.getString(5)),
                    Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7)), false);
            Order.notSet.put(Integer.parseInt(rs.getString(1)), o);
            Order.all.put(Integer.parseInt(rs.getString(1)), o);
            Customer.list.get(Integer.parseInt(rs.getString(6))).addToOrders_list(o);
        }

        sql = "select o.order_id, o.order_date, o.type_id, o.pay_method, o.discount, o.customer_id, o.worker_id, " +
                " ao.delivery_date, ao.machine_id, ao.passed from orders o, arranged_orders ao  " +
                "    where o.order_id = ao.order_id";

        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            boolean passed;
            if(rs.getString(10).equals("0")){
                System.out.println(rs.getString(1) + "No");
                passed = false;
            }
            else{
                System.out.println(rs.getString(1) + "Yes");
                passed = true;
            }
            Arranged_Order o = new Arranged_Order(Integer.parseInt(rs.getString(1)),
                    rs.getString(2), rs.getString(3), rs.getString(4), Double.parseDouble(rs.getString(5)),
                    Integer.parseInt(rs.getString(6)), Integer.parseInt(rs.getString(7)), rs.getString(8),
                    rs.getString(9), passed);
            Delivery.list.get(o.getDelivery_date()).addToOrders(o);
            if (passed) {
                System.out.println(rs.getString(1) + "Yes");
                Arranged_Order.passed_list.put(o.getOrder_id(), o);
                if(!Arranged_Order.dates.containsKey(Dates.stringMonth(rs.getString(2)))){
                    Arranged_Order.dates.put(Dates.stringMonth(rs.getString(2)), Dates.stripDay(rs.getString(2)));
                }
            }
            else {
                System.out.println(rs.getString(1) + "No");
                Arranged_Order.not_Passed.put(o.getOrder_id(), o);
            }
            System.out.println(" ");
            Order.all.put(Integer.parseInt(rs.getString(1)), o);
            Customer.list.get(Integer.parseInt(rs.getString(6))).addToOrders_list(o);
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

    public static void editWarehouse(String address, String type, String floors) throws SQLException, ClassNotFoundException {
        if (address!=""){
            connectDB();
            ExecuteStatement("update warehouse set warehouse_address = '" + address + "' where warehouse_name = '"
                    + Warehouse.current.getName() + "';");
            Warehouse.current.setAddress(address);
            con.close();
        }
        if (type!=""){
            connectDB();
            ExecuteStatement("update warehouse set building_type = '" + type + "' where warehouse_name = '"
                    + Warehouse.current.getName() + "';");
            Warehouse.current.setType_building(type);
            con.close();
        }
        if (floors!=""){
            int floor;
            floor= Integer.parseInt(floors);
            connectDB();
            ExecuteStatement("update warehouse set floors = " + floor + " where warehouse_name = '"
                    + Warehouse.current.getName() + "';");
            Warehouse.current.setFloors(floor);
            con.close();
        }

    }

    public static void addWarehouse(String name, String address, String type, String floors) throws SQLException, ClassNotFoundException {
        int floor;
        floor= Integer.parseInt(floors);
        if (Warehouse.notActive.contains(name)){
            connectDB();
            ExecuteStatement("update warehouse set activity = true where warehouse_name = '"
                    + name + "';");
            ExecuteStatement("update warehouse set warehouse_address = '" + address + "' where warehouse_name = '"
                    + name + "';");
            ExecuteStatement("update warehouse set building_type = '" + type + "' where warehouse_name = '"
                    + name + "';");
            ExecuteStatement("update warehouse set floors = " + floor + " where warehouse_name = '"
                    + name + "';");
            con.close();
            Warehouse.list.put(name, new Warehouse(name, address, type, floor));
            Warehouse.notActive.remove(name);
        }
        else {
            connectDB();
            ExecuteStatement("insert into warehouse values('" + name + "', '" + address + "', '" + type + "', " + floors + ", 1);");
            con.close();
            Warehouse.list.put(name, new Warehouse(name, address, type, floor));
        }
    }

    public static void deleteWarehouse(Warehouse w, int num, Warehouse replacment) throws SQLException, ClassNotFoundException {
        if(num==1){
            Warehouse.list.remove(w.getName());
            connectDB();
            ExecuteStatement("update warehouse set activity = false where warehouse_name = '"
                    + w.getName() + "';");
            con.close();
        }
        else{
            for(Map.Entry m : w.getMachines_list().entrySet()){
                Stored_machine.list.get(m.getKey()).setWarehouse_name(replacment.getName());
                Warehouse.list.get(replacment.getName()).addToMachines_list(Stored_machine.list.get(m.getKey()));
            }
            Warehouse.list.remove(w.getName());
            connectDB();
            ExecuteStatement("update warehouse set activity = false where warehouse_name = '"
                    + w.getName() + "';");
            ExecuteStatement("update stored_machine set warehouse_name = '" + replacment.getName() +
                    "' where warehouse_name = '" + w.getName() + "';");
            con.close();

        }
    }

    public static void editdrink(String name, String country, String p) throws SQLException, ClassNotFoundException {
        if (name!=""){
            connectDB();
            ExecuteStatement("update drink set drink_name = '" + name + "' where drink_id = '"
                    + Drinks.current.getDrink_id() + "';");
            Drinks.current.setDrink_name(name);
            con.close();
        }
        if (country!=""){
            connectDB();
            ExecuteStatement("update drink set country = '" + country + "' where drink_id = '"
                    + Drinks.current.getDrink_id() + "';");
            Drinks.current.setDrink_name(name);
            con.close();
        }
        if (p!=""){
            double price;
            price= Double.parseDouble(p);
            connectDB();
            ExecuteStatement("update warehouse set price = " + price + " where warehouse_name = '"
                    + Warehouse.current.getName() + "';");
            Drinks.current.setPrice(price);
            con.close();
        }
    }


    public static void addCustomer(String customer_id, String customer_name, String customer_address, String buisness_type, String customer_phone) throws SQLException, ClassNotFoundException {
        int customerid;
        customerid= Integer.parseInt(customer_id);
        int customrphone;
        customrphone= Integer.parseInt(customer_phone);

        connectDB();
        ExecuteStatement("insert into customer values(" + customerid + ", '" + customer_name + "', '" + customer_address + "', '" + buisness_type + "' , " + customrphone + ", 1);");
        con.close();
        Customer.list.put(customerid, new Customer(customerid, customer_name, customer_address, buisness_type, customrphone));
    }

    public static void editCustomer(String customer_name, String customer_address, String buisness_type, String customer_phone) throws SQLException, ClassNotFoundException {
        if (customer_name != "") {
            connectDB();
            ExecuteStatement("update customer set customer_name = '" + customer_name + "' where customer_id = "
                    + Customer.current1.getCustomer_id() + ";");
            Customer.current1.setCustomer_name(customer_name);
            con.close();
        }
        if (customer_address != "") {
            connectDB();
            ExecuteStatement("update customer set customer_address = '" + customer_address + "' where customer_id = "
                    + Customer.current1.getCustomer_id() + ";");
            Customer.current1.setCustomer_address(customer_address);
            con.close();
        }
        if (buisness_type != "") {
            connectDB();
            ExecuteStatement("update customer set buisness_type = '" + buisness_type + "' where customer_id = "
                    + Customer.current1.getCustomer_id() + ";");
            Customer.current1.setBuisness_type(buisness_type);
            con.close();
        }
        if (customer_phone != "") {
            int customerphone;
            customerphone = Integer.parseInt(customer_phone);
            connectDB();
            ExecuteStatement("update customer set customer_phone = " + customerphone + " where customer_id = "
                    + Customer.current1.getCustomer_id() + ";");
            Customer.current1.setCustomer_phone(customerphone);
            con.close();
        }

    }

    public static void deleteShipment() throws SQLException, ClassNotFoundException {
        Shipment.list.remove(Shipment.current.getShipment_id());
        connectDB();
        for(Map.Entry m : Shipment.current.getMachines_list().entrySet()){
            Machine M = (Machine) m.getValue();
            ExecuteStatement("delete from stored_machine where machine_id = '" + M.getMachine_id() + "';");
        }
        ExecuteStatement("delete from machine where shipment_id = " + Shipment.current.getShipment_id() + ";");
        ExecuteStatement("delete from shipment where shipment_id = " + Shipment.current.getShipment_id() + ";");
        con.close();
    }

    public static void createShipment() throws SQLException, ClassNotFoundException {
        connectDB();
        Shipment s = Shipment.current;
        Shipment.list.put(s.getShipment_id(), s);
        ExecuteStatement("insert into shipment values(" + s.getShipment_id() + ", " + s.getDriver_id() +
                ", '" + s.getShipment_date() + "', '" + s.getWarehouse_name() + "', " + s.getCosts() + ");");
        if(!Shipment.dates.containsKey(s.getShipment_date())){
            Shipment.dates.put(Dates.stringMonth(s.getShipment_date()), Dates.stripDay(s.getShipment_date()));
        }
        con.close();
    }

    public static void addMachines(String type_id, int num) throws SQLException, ClassNotFoundException {
        connectDB();
        for(int i=0 ; i<num ; i++) {
            Machine m = new Stored_machine(type_id, Shipment.current.getShipment_id(), Shipment.current.getWarehouse_name());
            Stored_machine.list.put(m.getMachine_id(), (Stored_machine) m);
            Warehouse.list.get(Shipment.current.getWarehouse_name()).addToMachines_list(m);
            Shipment.current.addToMachines_list(m);
            ExecuteStatement("insert into machine values('" + m.getMachine_id() + "', " + m.getShipment_id() +
                    ", '" + m.getType_id() + "');");
            ExecuteStatement("insert into stored_machine values('" + m.getMachine_id() + "', '" +
                    ((Stored_machine) m).getWarehouse_name() + "');");
        }
        con.close();
    }

    public static void addOrder(String date, String type, String pay, Double discount, int customer_id, String customer_name,
                                String address, String buisness, int phone, int seller, String delivery, boolean isCust,
                                boolean isDel) throws SQLException, ClassNotFoundException {
        connectDB();
        if(!isCust){
            Customer.list.put(customer_id, new Customer(customer_id, customer_name,address, buisness, phone));
            ExecuteStatement("insert into customer values (" + customer_id + ", '" + customer_name + "', '" + address +
                    "', '" + buisness + "', " + phone + ");");
        }

        if(!isDel) {
            Order o = new Order(date, type, pay, discount, customer_id, seller, isDel);
            Order.all.put(o.getOrder_id(),o);
            Order.notSet.put(o.getOrder_id(),o);
            ExecuteStatement("insert into orders values(" + o.getOrder_id() + ", '" + date + "', '" + type + "', '" +
                    pay +"', " + discount + ", " + customer_id + ", " + seller + ", false);");
            Customer.list.get(customer_id).addToOrders_list(o);
        }
        else{
            Stored_machine sm = null;
            for(Map.Entry m : Stored_machine.list.entrySet()){
                Stored_machine M = (Stored_machine) m.getValue();
                if(M.getType_id().equals(type))
                    sm = M;
            }
            Sold_machine Sm = new Sold_machine(sm.getMachine_id(), sm.getType_id(), sm.getShipment_id(), customer_id);
            Warehouse.list.get(sm.getWarehouse_name()).deleteFromMachine_list(sm.getMachine_id());
            Stored_machine.list.remove(sm.getMachine_id());
            Sold_machine.list.put(Sm.getMachine_id(), Sm);
            ExecuteStatement("delete from stored_machine where machine_id = '" + sm.getMachine_id() + "';");
            ExecuteStatement("insert into sold_machine values('" + Sm.getMachine_id() + "', " + Sm.getCustomer_id() + ");");
            Arranged_Order o = new Arranged_Order(date, type, pay, discount, customer_id, seller, delivery,
                    sm.getMachine_id(), false);
            Arranged_Order.not_Passed.put(o.getOrder_id(), o);
            Order.all.put(o.getOrder_id(),o);
            ExecuteStatement("insert into orders values(" + o.getOrder_id() + ", '" + date + "', '" + type + "', '" +
                    pay +"', " + discount + ", " + customer_id + ", " + seller + ", true);");
            ExecuteStatement("insert into arranged_orders values(" + o.getOrder_id() + ", '" + delivery + "', '" +
                    sm.getMachine_id() + "', false);");
            Customer.list.get(customer_id).addToOrders_list(o);
        }
        con.close();
    }

    public static void deleteDeliveredOrder(boolean passed) throws SQLException, ClassNotFoundException {
        if (passed)
            Arranged_Order.passed_list.remove(Arranged_Order.currentA.getOrder_id());
        else
            Arranged_Order.not_Passed.remove(Arranged_Order.currentA.getOrder_id());
        Order.all.remove(Arranged_Order.currentA.getOrder_id());
        Delivery.list.get(Arranged_Order.currentA.getDelivery_date()).deleteFromOrders(Arranged_Order.currentA.getOrder_id());
        Sold_machine.list.remove(Arranged_Order.currentA.getMachine_id());
        Customer.list.get(Arranged_Order.currentA.getCustomer_id()).removeFromOrders_list(Arranged_Order.currentA.getOrder_id());
        connectDB();
        ExecuteStatement("delete from arranged_orders where order_id = " + Arranged_Order.currentA.getOrder_id() + " ;");
        ExecuteStatement("delete from orders where order_id = " + Arranged_Order.currentA.getOrder_id() + " ;");
        ExecuteStatement("delete from sold_machine where machine_id = '" + Arranged_Order.currentA.getMachine_id() + "' ;");
        ExecuteStatement("delete from machine where machine_id = '" + Arranged_Order.currentA.getMachine_id() + "' ;");
        if(Customer.list.get(Arranged_Order.currentA.getCustomer_id()).getOrders_list().size() ==0){
            Customer.list.remove(Arranged_Order.currentA.getCustomer_id());
            ExecuteStatement("delete from customer where customer_id = " + Arranged_Order.currentA.getCustomer_id() +" ;");
        }
        con.close();
    }

    public static void retreaveMachineForOrder(String warehouse_name, boolean passed) throws SQLException, ClassNotFoundException {
        if(passed)
            Arranged_Order.passed_list.remove(Arranged_Order.currentA.getOrder_id());
        else
            Arranged_Order.not_Passed.remove(Arranged_Order.currentA.getOrder_id());
        Sold_machine s = Sold_machine.list.get(Arranged_Order.currentA.getMachine_id());
        Order.all.remove(Arranged_Order.currentA.getOrder_id());
        Delivery.list.get(Arranged_Order.currentA.getDelivery_date()).deleteFromOrders(Arranged_Order.currentA.getOrder_id());
        Sold_machine.list.remove(s.getMachine_id());
        Stored_machine sm = new Stored_machine(s.getMachine_id(), s.getType_id(), s.getShipment_id(), warehouse_name);
        Stored_machine.list.put(sm.getMachine_id(), sm);
        Warehouse.list.get(warehouse_name).addToMachines_list(sm);
        Customer.list.get(Arranged_Order.currentA.getCustomer_id()).removeFromOrders_list(Arranged_Order.currentA.getOrder_id());
        connectDB();
        ExecuteStatement("delete from arranged_orders where order_id = " + Arranged_Order.currentA.getOrder_id() + " ;");
        ExecuteStatement("delete from orders where order_id = " + Arranged_Order.currentA.getOrder_id() + " ;");
        ExecuteStatement("delete from sold_machine where machine_id = '" + Arranged_Order.currentA.getMachine_id() + "' ;");
        ExecuteStatement("insert into stored_machine values('" + s.getMachine_id() + "', '" + warehouse_name + "');");
        if(Customer.list.get(Arranged_Order.currentA.getCustomer_id()).getOrders_list().size() ==0){
            Customer.list.remove(Arranged_Order.currentA.getCustomer_id());
            ExecuteStatement("delete from customer where customer_id = " + Arranged_Order.currentA.getCustomer_id() +" ;");
        }
        con.close();
    }


    public static void cancelOrder() throws SQLException, ClassNotFoundException {
        connectDB();
        Order.all.remove(Order.current.getOrder_id());
        Order.notSet.remove(Order.current.getOrder_id());
        Customer.list.get(Order.current.getCustomer_id()).removeFromOrders_list(Order.current.getOrder_id());
        ExecuteStatement("delete from orders where order_id = " + Order.current.getOrder_id() + ";");
        con.close();
    }

    public static void changeMachineTypeForOrder(String machine_type) throws SQLException, ClassNotFoundException {
        Order o = Order.current;
        connectDB();
        Order.notSet.get(o.getOrder_id()).setMachine_type(machine_type);
        ExecuteStatement("update orders set type_id = '" + machine_type + "' where order_id = " +
                Order.current.getOrder_id() + " ;");
        con.close();
    }

    public static void setDeliveryForOrder(String delivery_date, String machine_id) throws SQLException, ClassNotFoundException {
        Order o = Order.current;
        connectDB();
        Stored_machine s = Stored_machine.list.get(machine_id);
        Sold_machine SM = new Sold_machine(s.getMachine_id(), s.getType_id(), s.getShipment_id(), o.getCustomer_id());
        Stored_machine.list.remove(s);
        Warehouse.list.get(s.getWarehouse_name()).deleteFromMachine_list(s.getMachine_id());
        Shipment.list.get(s.getShipment_id()).getMachines_list().replace(s.getMachine_id(), SM);
        ExecuteStatement("delete from stored_machine where machine_id = '" + s.getMachine_id() + "' ;");
        ExecuteStatement("insert into sold_machine values('" + SM.getMachine_id() + "', " + SM.getCustomer_id() + ");");
        Arranged_Order O = new Arranged_Order(o.getOrder_id(), o.getOrder_date(), o.getMachine_type(), o.getPay_method(),
                o.getDiscount(), o.getCustomer_id(), o.getWorker_id(), delivery_date, machine_id, false);
        Order.all.remove(o.getOrder_id());
        Order.all.put(O.getOrder_id(), O);
        Order.notSet.remove(o.getOrder_id());
        Arranged_Order.not_Passed.put(O.getOrder_id(), O);
        ExecuteStatement("insert into arranged_orders values(" + O.getOrder_id() + ", '"  + delivery_date +
                "', '" + machine_id + "', false);");
        ExecuteStatement("update orders set arranged = true where order_id = " + O.getOrder_id() + ";");
        Delivery.list.get(delivery_date).addToOrders(O);
    }

}
