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

public class customerController implements Initializable {
    @FXML
    Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label address_label;

    @FXML
    private TextField address_text;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Customer, String> buisness_type;

    @FXML
    private TableColumn<Customer, String> customer_address;

    @FXML
    private TableColumn<Customer, Integer> customerId;

    @FXML
    private TableColumn<Customer, String> customer_name;

    @FXML
    private TableColumn<Customer, Integer> customer_phone;

    @FXML
    private Label delete_label;

    @FXML
    private Button delete_ok;

    @FXML
    private ChoiceBox<?> delete_option;

    @FXML
    private Button edit;

    @FXML
    private Label error_text;

    @FXML
    private Label phone_label;

    @FXML
    private TextField phone_text;

    @FXML
    private Button insert;

    @FXML
    private Label name_label;

    @FXML
    private TextField name_text;

    @FXML
    private Button ok;

    @FXML
    private TableView<Customer> table;

    @FXML
    private Label title;

    @FXML
    private Label type_label;

    @FXML
    private TextField type_text;

    @FXML
    private Label visible_label;

    @FXML
    private Button x;

    @FXML
    private ListView<String> ordersList;

    private ObservableList<Customer> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Map.Entry n : Customer.list.entrySet()) {
            observableList.add((Customer) n.getValue());
        }
            customerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_id"));
            customer_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_name"));
            customer_address.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_address"));
            buisness_type.setCellValueFactory(new PropertyValueFactory<Customer, String>("buisness_type"));
            customer_phone.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_phone"));
            table.setItems(observableList);
            hide();
        ordersList.setVisible(false);
        ordersList.getItems().clear();
    }
    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void hide() {
        visible_label.setText("");
        name_label.setText("");
        address_label.setText("");
        type_label.setText("");
        phone_label.setText("");
        error_text.setText("");
        name_text.setVisible(false);
        address_text.setVisible(false);
        type_text.setVisible(false);
        phone_text.setVisible(false);
        ok.setVisible(false);
    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void addshow(ActionEvent actionEvent) {
        ordersList.setVisible(false);
        ordersList.getItems().clear();
        Customer.n = 3;
        show("New Customer:");
        error_text.setText("");
        ok.setText("Add");
    }

    public void edit() {
        ordersList.setVisible(false);
        ordersList.getItems().clear();
        Customer.n = 1;
        Customer.current1 = table.getSelectionModel().getSelectedItem();
        if (Customer.current1 == null)
            error_text.setText("Please select a customer from the table");
        else {
            show2("Edit " + Customer.current1.getCustomer_id() + ":");
            System.out.println(Customer.current1.getCustomer_id());
            error_text.setText("");
            ok.setText("Edit");
        }
    }

    public void showOrders(ActionEvent e){
        error_text.setText("");
        hide();
        Customer.current1 = table.getSelectionModel().getSelectedItem();
        if(Customer.current1 == null)
            error_text.setText("Please select a customer from the table");
        else{
            ordersList.setVisible(true);
            for (Map.Entry n : Customer.current1.getOrders_list().entrySet()) {
                Order o = Order.all.get(n.getKey());
                String text = ("Machine type: " + o.getMachine_type() +
                        ", Sold by: " + Seller.list.get(o.getWorker_id()).getWorker_name() + ", on: " + o.getOrder_date());
                ordersList.getItems().add(text);
            }
        }
    }

    public void execute(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(Customer.n==1){
            boolean num = false;
            if(phone_text.getText().equals(""))
                num = true;

            else {
                num = isNumeric(phone_text.getText());
            }


            if(num){
                if( phone_text.getText().equals("") || Integer.parseInt(phone_text.getText()) > 0){
                    SQL_connection.editCustomer(name_text.getText(),address_text.getText(), type_text.getText(), phone_text.getText());
                    Customer.n=0;
                    hide();
                    observableList = FXCollections.observableArrayList();
                    for(Map.Entry m : Customer.list.entrySet()){
                        observableList.add((Customer) m.getValue());
                    }
                    table.refresh();
                }
                else{
                    error_text.setText("Please enter a valid phone number");
                }
            }
            else{
                error_text.setText("Please enter a valid phone number");
            }
        }

        else if(Customer.n == 3){
            if(!name_text.getText().equals("") && !address_text.getText().equals("") &&
                    !type_text.getText().equals("") && !phone_text.getText().equals("")){
                if(!Warehouse.list.containsKey(name_text.getText())){
                    if(isNumeric(phone_text.getText())){
                        if(Integer.parseInt(phone_text.getText()) > 0){
                            SQL_connection.addWarehouse(name_text.getText(), address_text.getText(),
                                    type_text.getText(), phone_text.getText());
                            Customer.n=0;
                            hide();
                            observableList = FXCollections.observableArrayList();
                            for(Map.Entry n : Customer.list.entrySet()){
                                observableList.add((Customer) n.getValue());
                            }
                            table.setItems(observableList);
                        }
                        else
                            error_text.setText("Please enter a valid phone number");
                    }
                    else{
                        error_text.setText("Please enter a valid phone number");
                    }
                }
                else{
                    error_text.setText("This warehouse name already exists, try another");
                }
            }
            else{
                error_text.setText("Please fill all fields");
            }
        }
    }

        public void show(String title){
            System.out.println(title);
            visible_label.setText(title);
            name_label.setText("Name:");
            address_label.setText("Address:");
            type_label.setText("Type:");
            phone_label.setText("Floors:");
            name_text.setVisible(true);
            name_text.setText("");
            address_text.setVisible(true);
            address_text.setText("");
            type_text.setVisible(true);
            type_text.setText("");
            phone_text.setVisible(true);
            phone_text.setText("");
            ok.setVisible(true);
        }

        public void show2(String title){
            System.out.println(title);
            visible_label.setText(title);
            name_label.setText("Name:");
            address_label.setText("Address:");
            name_label.setText("");
            type_label.setText("Type:");
            phone_label.setText("Phone:");
            name_text.setVisible(false);
            address_text.setVisible(true);
            address_text.setText("");
            type_text.setVisible(true);
            type_text.setText("");
            phone_text.setVisible(true);
            phone_text.setText("");
            ok.setVisible(true);
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
