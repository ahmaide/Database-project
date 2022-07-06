package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    AnchorPane pane;

    @FXML
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void settingsPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void employeePage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("employee.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void warehousePage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("warehouse.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deliveryPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Delivery.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Type_of_Machine_page(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Type_of_machine.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void drinkssPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DrinksController.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void customerPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("customer.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void shipmentPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("shipment.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ordersPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("orders.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void exit(ActionEvent e) throws IOException {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }


}
