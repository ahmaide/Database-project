package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label error_text;

    @FXML
    private ChoiceBox<String> date_selector;

    @FXML
    private Label date_label;

    @FXML
    private TextField discount_box;

    @FXML
    private ChoiceBox<String> type_selector;

    @FXML
    private Label type_label;

    @FXML
    private ChoiceBox<String> pay_selector;

    @FXML
    private Label payMethod_label;

    @FXML
    private Label discount_label;

    @FXML
    private TextField customerId_box;

    @FXML
    private Label customerId_label;

    @FXML
    private ChoiceBox<String> seller_selector;

    @FXML
    private Label seller_label;

    @FXML
    private TextField customerName_box;

    @FXML
    private Label customerName_label;

    @FXML
    private Label address_label;

    @FXML
    private TextField address_box;

    @FXML
    private Label buisness_label;

    @FXML
    private TextField buisness_box;

    @FXML
    private Label phone_label;

    @FXML
    private TextField phone_box;

    @FXML
    private RadioButton delivery_radioButton;

    @FXML
    private ChoiceBox<String> deliveries_selector;

    @FXML
    private Label available_label;

    @FXML
    private Button addOrder;

    @FXML
    private Stage stage;

    @FXML
    private Label check_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideAll();
        check_text.setVisible(false);
    }

    public void newOrder(ActionEvent e){
        check_text.setVisible(false);
        hideAll();
        date_label.setVisible(true);
        date_selector.setVisible(true);
        date_selector.getItems().clear();
        String [] d = Dates.pastWeekList();
        for(int i=0; i<d.length ; i++){
            date_selector.getItems().add(d[i]);
        }
        type_label.setVisible(true);
        type_selector.setVisible(true);
        type_selector.getItems().clear();
        for(Map.Entry m : Machine_type.list.entrySet()){
            Machine_type mt = Machine_type.list.get(m.getKey());
            type_selector.getItems().add(mt.getType_id());
        }
        pay_selector.setVisible(true);
        pay_selector.getItems().clear();
        pay_selector.getItems().add("Cash");
        pay_selector.getItems().add("Check");
        pay_selector.getItems().add("Card");
        pay_selector.getItems().add("Deposit");
        payMethod_label.setVisible(true);
        discount_box.setVisible(true);
        discount_box.setText("");
        discount_label.setVisible(true);
        customerId_box.setVisible(true);
        customerId_box.setText("");
        customerId_label.setVisible(true);
        seller_label.setVisible(true);
        seller_selector.setVisible(true);
        seller_selector.getItems().clear();
        for(Map.Entry m : Seller.active.entrySet()){
            Seller s = Seller.active.get(m.getKey());
            seller_selector.getItems().add(s.getWorker_name());
        }
        delivery_radioButton.setVisible(true);
        addOrder.setVisible(true);
        error_text.setText("");
        customerName_box.setVisible(true);
        customerName_box.setText("");
        customerName_label.setVisible(true);
        address_box.setVisible(true);
        address_box.setText("");
        address_label.setVisible(true);
        buisness_label.setVisible(true);
        buisness_box.setVisible(true);
        buisness_box.setText("");
        phone_box.setVisible(true);
        phone_label.setVisible(true);
        phone_box.setText("");
    }

    public void deliveries(ActionEvent e){
        if (delivery_radioButton.isSelected()){
            available_label.setVisible(true);
            deliveries_selector.setVisible(true);
            deliveries_selector.getItems().clear();
            for(Map.Entry m : Delivery.list.entrySet()){
                Delivery d = (Delivery) m.getValue();
                if(Dates.dateInt(d.getDate()) > Dates.dateInt(Dates.dateToday()))
                    deliveries_selector.getItems().add(d.getDate());
            }
        }
        if(!delivery_radioButton.isSelected()){
            available_label.setVisible(false);
            deliveries_selector.setVisible(false);
        }
    }

    public void exit(ActionEvent e) throws IOException {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void deliveredOrders(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("deliveredOrder.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void UnarrangedOrders(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("unArrangedOrders.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void arrangedOrders(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("arrangedOrders.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void addNewOrder(ActionEvent e) throws SQLException, ClassNotFoundException {
        boolean customer = false;
        boolean delivery = false;
        if(date_selector.getValue()==null || type_selector.getValue()==null || pay_selector.getValue()==null ||
                seller_selector.getValue()==null || discount_box.getText().equals("") || !isNumeric(discount_box.getText()) ||
        customerId_box.getText().equals("") || !isNumeric(customerId_box.getText()))
            error_text.setText("Please make sure that all the order data is chosen and all data is right");
        else{
            if(!Customer.list.containsKey(Integer.parseInt(customerId_box.getText())) && ( customerName_box.getText().equals("") ||
            customerName_box.getText().length() >= 30 || address_box.getText().equals("") || address_box.getText().length()>=30 ||
            buisness_box.getText().equals("") || buisness_box.getText().length() >= 30 || phone_box.getText().equals("") ||
            !isNumeric(phone_box.getText()) || phone_box.getText().length()<7))
                error_text.setText("Please make sure that all the customer data is chosen and all data is right");
            else{
                if(Customer.list.containsKey(Integer.parseInt(customerId_box.getText())))
                    customer = true;
                if(delivery_radioButton.isSelected() && deliveries_selector.getValue()==null)
                    error_text.setText("Please make sure that you selected a delivery");
                else{
                    if(delivery_radioButton.isSelected())
                        delivery = true;
                    boolean machineFound=false;
                    for(Map.Entry m : Stored_machine.list.entrySet()){
                        Stored_machine M = (Stored_machine) m.getValue();
                        if(M.getType_id().equals(type_selector.getValue()))
                            machineFound=true;
                    }
                    if(!machineFound && delivery)
                        error_text.setText("This type of machine is out");
                    else{
                        int seller_id = 0;
                        for(Map.Entry m : Seller.list.entrySet()){
                            Seller s = (Seller) m.getValue();
                            if(s.getWorker_name().equals(seller_selector.getValue())){
                                seller_id = s.getWorker_id();
                                break;
                            }
                        }
                        int p;
                        if(phone_box.getText().equals("") || !isNumeric(phone_box.getText()))
                            p=0;
                        else
                            p=Integer.parseInt(phone_box.getText());

                        SQL_connection.addOrder(date_selector.getValue(), type_selector.getValue(), pay_selector.getValue(),
                                Double.parseDouble(discount_box.getText()), Integer.parseInt(customerId_box.getText()),
                                customerName_box.getText(), address_box.getText(), buisness_box.getText(),
                                p, seller_id, deliveries_selector.getValue(), customer, delivery);
                        hideAll();
                        check_text.setVisible(true);
                    }
                }
            }
        }
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void hideAll(){
        date_label.setVisible(false);
        date_selector.setVisible(false);
        type_label.setVisible(false);
        type_selector.setVisible(false);
        pay_selector.setVisible(false);
        payMethod_label.setVisible(false);
        discount_box.setVisible(false);
        discount_label.setVisible(false);
        customerId_box.setVisible(false);
        customerId_label.setVisible(false);
        customerName_box.setVisible(false);
        customerName_label.setVisible(false);
        address_box.setVisible(false);
        address_label.setVisible(false);
        buisness_label.setVisible(false);
        buisness_box.setVisible(false);
        phone_box.setVisible(false);
        phone_label.setVisible(false);
        seller_label.setVisible(false);
        seller_selector.setVisible(false);
        delivery_radioButton.setVisible(false);
        available_label.setVisible(false);
        deliveries_selector.setVisible(false);
        addOrder.setVisible(false);
        error_text.setText("");
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
