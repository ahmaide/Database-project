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

public class unArranged_OrdersController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button x;

    @FXML
    private Label error_text;

    @FXML
    private TableView<Order> table;

    @FXML
    private TableColumn<Order, Integer> order_id;

    @FXML
    private TableColumn<Order, String> order_date;

    @FXML
    private TableColumn<Order, String> order_machineType;

    @FXML
    private TableColumn<Order, String> order_pay;

    @FXML
    private TableColumn<Order, Double> order_discount;

    @FXML
    private TableColumn<Order, Integer> order_customer;

    @FXML
    private TableColumn<Order, String> order_seller;

    @FXML
    private Button back;

    @FXML
    private Label type_label;

    @FXML
    private Button ok;

    @FXML
    private ListView<String> info_list;

    @FXML
    private ChoiceBox<String> type_selector;

    @FXML
    private ChoiceBox<String> delivery_selector;

    @FXML
    private Button ok2;

    @FXML
    private Stage stage;

    private ObservableList<Order> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hide();
        order_id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("order_id"));
        order_date.setCellValueFactory(new PropertyValueFactory<Order, String>("order_date"));
        order_machineType.setCellValueFactory(new PropertyValueFactory<Order, String>("machine_type"));
        order_pay.setCellValueFactory(new PropertyValueFactory<Order, String>("pay_method"));
        order_discount.setCellValueFactory(new PropertyValueFactory<Order, Double>("discount"));
        order_customer.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customer_id"));
        order_seller.setCellValueFactory(new PropertyValueFactory<Order, String>("worker_name"));
        for(Map.Entry m : Order.notSet.entrySet()){
            observableList.add((Order) m.getValue());
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

    public void customerInfo(ActionEvent e){
        hide();
        Order.current = table.getSelectionModel().getSelectedItem();
        info_list.getItems().clear();
        if(Order.current==null)
            error_text.setText("Please select an order");
        else {
            info_list.setVisible(true);
            Customer c = Customer.list.get(Order.current.getCustomer_id());
            info_list.getItems().add("Name: " + c.getCustomer_name());
            info_list.getItems().add("Phone number: " + c.getCustomer_phone());
            info_list.getItems().add("Address: " + c.getCustomer_address());
            info_list.getItems().add("Business type: " + c.getBuisness_type());
        }
    }

    public void cancelOrder(ActionEvent e) throws SQLException, ClassNotFoundException {
        hide();
        Order.current = table.getSelectionModel().getSelectedItem();
        if(Order.current==null)
            error_text.setText("You didn't select an order");
        else{
            SQL_connection.cancelOrder();
            observableList = FXCollections.observableArrayList();
            for(Map.Entry m : Order.notSet.entrySet()){
                Order o = (Order) m.getValue();
                    observableList.add(o);
            }
            table.setItems(observableList);
            hide();
        }
    }

    public void changeMachineDisplay(ActionEvent e){
        hide();
        Order.current = table.getSelectionModel().getSelectedItem();
        if(Order.current==null)
            error_text.setText("You didn't select an order");
        else{
            type_selector.getItems().clear();
            type_label.setVisible(true);
            type_selector.setVisible(true);
            ok.setVisible(true);
            for(Map.Entry m : Machine_type.list.entrySet()){
                Machine_type M = (Machine_type) m.getValue();
                if(!M.getType_id().equals(Order.current.getMachine_type())){
                    type_selector.getItems().add(M.getType_id());
                }
            }
        }
    }

    public void changeMachine(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(type_selector.getValue()==null)
            error_text.setText("Please select a machine type to replace");
        else{
            SQL_connection.changeMachineTypeForOrder(type_selector.getValue());
            observableList.clear();
            observableList = FXCollections.observableArrayList();
            for(Map.Entry m : Order.notSet.entrySet()){
                Order o = (Order) m.getValue();
                observableList.add(o);
            }
            table.setItems(observableList);
            hide();
        }
    }

    public void setDeliveryDisplay(ActionEvent e){
        hide();
        Order.current = table.getSelectionModel().getSelectedItem();
        if(Order.current==null)
            error_text.setText("You didn't select an order");
        else {
            delivery_selector.getItems().clear();
            delivery_selector.setVisible(true);
            ok2.setVisible(true);
            for(Map.Entry m : Delivery.list.entrySet()){
                Delivery d = (Delivery) m.getValue();
                if(Dates.dateInt(d.getDate()) > Dates.dateInt(Dates.dateToday())){
                    delivery_selector.getItems().add(d.getDate());
                }
            }
        }
    }

    public void setDelivery(ActionEvent e) throws SQLException, ClassNotFoundException {
        String mID = "";
        boolean found = false;
        for (Map.Entry m : Stored_machine.list.entrySet()) {
            Stored_machine SM = (Stored_machine) m.getValue();
            if (SM.getType_id().equals(Order.current.getMachine_type())) {
                found = true;
                mID = SM.getMachine_id();
            }
        }
        if (found) {
            if (delivery_selector.getValue() == null)
                error_text.setText("Please select a delivery date from the available deliveries");
            else {
                SQL_connection.setDeliveryForOrder(delivery_selector.getValue(), mID);
                observableList.clear();
                observableList = FXCollections.observableArrayList();
                for (Map.Entry m : Order.notSet.entrySet()) {
                    Order o = (Order) m.getValue();
                    observableList.add(o);
                }
                table.setItems(observableList);
                hide();
            }
        }
        else
            error_text.setText("There is no available machines right now");
    }


    public void hide(){
        type_label.setVisible(false);
        delivery_selector.setVisible(false);
        type_selector.setVisible(false);
        ok.setVisible(false);
        ok2.setVisible(false);
        info_list.setVisible(false);
        error_text.setText("");
    }
}
