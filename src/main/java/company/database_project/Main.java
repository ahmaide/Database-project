package company.database_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        SQL_connection.storeUsers();
        SQL_connection.storeWarehouse();
        SQL_connection.storeShipment();
        SQL_connection.storeCustomers();
        SQL_connection.storeDrink();
        SQL_connection.storeEmployee();
        SQL_connection.storeMachines();
        SQL_connection.storeDelivery();
        SQL_connection.storeOrders();
        SQL_connection.storeType_of_machine();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Company Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }





}