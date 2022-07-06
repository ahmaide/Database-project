package company.database_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class Arranged_OrderController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button x;

    @FXML
    private Label error_text;

    @FXML
    private TableView<Arranged_Order> table;

    @FXML
    private TableColumn<Arranged_Order, Integer> order_id;

    @FXML
    private TableColumn<Arranged_Order, String> order_date;

    @FXML
    private TableColumn<Arranged_Order, String> order_machineType;

    @FXML
    private TableColumn<Arranged_Order, String> order_pay;

    @FXML
    private TableColumn<Arranged_Order, Double> order_discount;

    @FXML
    private TableColumn<Arranged_Order, Integer> order_customer;

    @FXML
    private TableColumn<Arranged_Order, String> order_seller;

    @FXML
    private TableColumn<Arranged_Order, String> order_delivery;

    @FXML
    private ChoiceBox<String> warehouse_selector;

    @FXML
    private Button back;

    @FXML
    private Label warehouse_label;

    @FXML
    private Button warehouse_ok;

    @FXML
    private ListView<String> info_list;

    @FXML
    private Stage stage;

    private ObservableList<Arranged_Order> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hide();
        order_id.setCellValueFactory(new PropertyValueFactory<Arranged_Order, Integer>("order_id"));
        order_date.setCellValueFactory(new PropertyValueFactory<Arranged_Order, String>("order_date"));
        order_machineType.setCellValueFactory(new PropertyValueFactory<Arranged_Order, String>("machine_type"));
        order_pay.setCellValueFactory(new PropertyValueFactory<Arranged_Order, String>("pay_method"));
        order_discount.setCellValueFactory(new PropertyValueFactory<Arranged_Order, Double>("discount"));
        order_customer.setCellValueFactory(new PropertyValueFactory<Arranged_Order, Integer>("customer_id"));
        order_seller.setCellValueFactory(new PropertyValueFactory<Arranged_Order, String>("worker_name"));
        order_delivery.setCellValueFactory(new PropertyValueFactory<Arranged_Order, String>("delivery_date"));
        for(Map.Entry m : Arranged_Order.not_Passed.entrySet()){
            Arranged_Order o = (Arranged_Order) m.getValue();
                observableList.add(o);
            }
        table.setItems(observableList);
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("orders.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent e) throws IOException {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void deleteOrder(ActionEvent e) throws SQLException, ClassNotFoundException {
        hide();
        Arranged_Order.currentA = table.getSelectionModel().getSelectedItem();
        if(Arranged_Order.currentA==null)
            error_text.setText("Please select an order to delete");
        else{
            SQL_connection.deleteDeliveredOrder(false);
            observableList = FXCollections.observableArrayList();
            for(Map.Entry m : Arranged_Order.not_Passed.entrySet()){
                Arranged_Order o = (Arranged_Order) m.getValue();
                    observableList.add(o);
            }
            table.setItems(observableList);
        }
    }

    public void retreaveDisplay(ActionEvent e){
        hide();
        warehouse_label.setVisible(true);
        warehouse_selector.setVisible(true);
        warehouse_ok.setVisible(true);
        warehouse_selector.getItems().clear();
        for(Map.Entry m : Warehouse.list.entrySet()){
            Warehouse w = (Warehouse) m.getValue();
            warehouse_selector.getItems().add(w.getName());
        }
    }

    public void restoreMachine(ActionEvent e) throws SQLException, ClassNotFoundException {
        hide();
        Arranged_Order.currentA = table.getSelectionModel().getSelectedItem();
        if(Arranged_Order.currentA==null)
            error_text.setText("Please select an order");
        else{
            if(warehouse_selector.getValue()==null)
                error_text.setText("Please select a warehouse");
            else{
                SQL_connection.retreaveMachineForOrder(warehouse_selector.getValue(), false);
                observableList = FXCollections.observableArrayList();
                for(Map.Entry m : Arranged_Order.not_Passed.entrySet()){
                    Arranged_Order o = (Arranged_Order) m.getValue();
                        observableList.add(o);
                }
                table.setItems(observableList);
                hide();
            }
        }
    }

    public void customerInfo(ActionEvent e){
        hide();
        Arranged_Order.currentA = table.getSelectionModel().getSelectedItem();
        info_list.getItems().clear();
        if(Arranged_Order.currentA==null)
            error_text.setText("Please select an order");
        else {
            info_list.setVisible(true);
            Customer c = Customer.list.get(Arranged_Order.currentA.getCustomer_id());
            info_list.getItems().add("Name: " + c.getCustomer_name());
            info_list.getItems().add("Phone number: " + c.getCustomer_phone());
            info_list.getItems().add("Address: " + c.getCustomer_address());
            info_list.getItems().add("Business type: " + c.getBuisness_type());
        }
    }

    public void hide(){
        error_text.setText("");
        warehouse_label.setVisible(false);
        warehouse_selector.setVisible(false);
        warehouse_ok.setVisible(false);
        info_list.setVisible(false);
        info_list.getItems().clear();
    }
}