package company.database_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeliveryController implements Initializable {

    @FXML
    Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label Workerid_label;

    @FXML
    private TextField Workerid_text;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Delivery, String> city;

    @FXML
    private Label city_label;

    @FXML
    private TextField city_text;

    @FXML
    private TextField deelivery_text;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Delivery, String> delivery_date;

    @FXML
    private Label delivery_label;

    @FXML
    private Button edit;

    @FXML
    private Label error_text;

    @FXML
    private TableColumn<Delivery, Double> expences;

    @FXML
    private Label expences_label;

    @FXML
    private TextField expences_text;

    @FXML
    private Button insert;

    @FXML
    private Button ok;

    @FXML
    private ListView<String> ordersList;

    @FXML
    private TableView<Delivery> table;

    @FXML
    private Button view;

    @FXML
    private Label visible_label;

    @FXML
    private TableColumn<Delivery, Integer> worker_id;

    @FXML
    private Button x;



    private ObservableList<Delivery> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Map.Entry n : Delivery.list.entrySet()) {
            observableList.add((Delivery) n.getValue());
        }
        delivery_date.setCellValueFactory(new PropertyValueFactory<Delivery, String>("date"));
        worker_id.setCellValueFactory(new PropertyValueFactory<Delivery, Integer>("worker_id"));
        expences.setCellValueFactory(new PropertyValueFactory<Delivery, Double>("expences"));
        city.setCellValueFactory(new PropertyValueFactory<Delivery, String>("city"));
        table.setItems(observableList);
        hide();
        ordersList.setVisible(false);
        ordersList.getItems().clear();
    }

    private void hide() {
        visible_label.setText("");
        delivery_label.setText("");
        Workerid_label.setText("");
        expences_label.setText("");
        city_label.setText("");
        error_text.setText("");
        deelivery_text.setVisible(false);
        Workerid_text.setVisible(false);
        expences_text.setVisible(false);
        city_text.setVisible(false);
        ok.setVisible(false);
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent e) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void addshow(ActionEvent actionEvent) {
        ordersList.setVisible(false);
        ok.setVisible(true);
        ordersList.getItems().clear();
        Delivery.m = 1;
        show("New Delivery:");
        error_text.setText("");
        ok.setText("Add");
    }

    public void showOrders(ActionEvent e){
        error_text.setText("");
        hide();
        Delivery.current = table.getSelectionModel().getSelectedItem();
        if(Delivery.current == null)
            error_text.setText("Please select a Delivery from the table");
        else{
            ordersList.setVisible(true);
            ordersList.getItems().clear();
            for (Map.Entry d : Delivery.current.getOrders_list().entrySet()) {
                Order o = Order.all.get(d.getKey());
                String text = ("Machine type: " + o.getMachine_type() +
                        ", Customer: "+ Customer.list.get(o.getCustomer_id()).getCustomer_name()+
                        ", phone: "+ Customer.list.get(o.getCustomer_id()).getCustomer_phone());
                ordersList.getItems().add(text);
            }
        }
    }

    public void edit() {
        ordersList.setVisible(false);
        ok.setVisible(true);
        ordersList.getItems().clear();
        Delivery.m = 3;
        Delivery.current = table.getSelectionModel().getSelectedItem();
        if (Delivery.current == null)
            error_text.setText("Please select a Delivery from the table");
        else {
            show2("Edit " + Delivery.current.getDate() + ":");
            System.out.println(Delivery.current.getDate());
            error_text.setText("");
            ok.setText("Edit");
        }
    }

    public void showOrder(ActionEvent e) {
        error_text.setText("");
        hide();
        Delivery.current = table.getSelectionModel().getSelectedItem();
        if (Delivery.current == null)
            error_text.setText("Please select a Delivery from the table");
        else {
            ordersList.setVisible(true);
            ordersList.getItems().clear();
            for (Map.Entry n : Delivery.current.getOrders().entrySet()) {
                Order o = Order.all.get(n.getKey());
                String text = ("Machine type: " + o.getMachine_type() +
                        ", Sold by: " + Seller.list.get(o.getWorker_id()).getWorker_name() + ", on: " + o.getOrder_date());
                ordersList.getItems().add(text);
            }
        }
    }

    public void execute(ActionEvent e) throws SQLException, ClassNotFoundException {
        if (Delivery.m == 1) {
            boolean num = false;
            if (Workerid_text.getText().equals(""))
                num = true;
            else {
                num = isNumeric(Workerid_text.getText());
            }

            if (num) {
                if (Workerid_text.getText().equals("") || Integer.parseInt(Workerid_text.getText()) > 0) {
                    if (Driver.list.containsKey(Integer.parseInt(Workerid_text.getText()))) {
                        SQL_connection3.addDelivery(deelivery_text.getText(), Workerid_text.getText(), expences_text.getText(), city_text.getText());
                        Delivery.m = 0;
                        hide();
                        observableList = FXCollections.observableArrayList();
                        for (Map.Entry m : Delivery.list.entrySet()) {
                            observableList.add((Delivery) m.getValue());
                        }
                        table.refresh();
                    }
                  else {
                    error_text.setText("This worker doesn't in exist ");
                }}
             else {
                    error_text.setText("Please enter a valid Worker ID");
                }
            }
            else{
                error_text.setText("Please enter a valid Worker ID");
            }
        }

        else if(Delivery.m == 3){
            if(!expences_text.getText().equals("") &&
                    !expences_text.getText().equals("") && !Workerid_text.getText().equals("")){
                if(!Delivery.list.containsKey(expences_text.getText())){
                    if(isNumeric(Workerid_text.getText())){
                        if(Integer.parseInt(expences_text.getText()) > 0){
                            SQL_connection3.editDelivery(Workerid_text.getText(), city_text.getText(), expences_text.getText());
                            Delivery.m=0;
                            hide();
                            observableList = FXCollections.observableArrayList();
                            for(Map.Entry m : Delivery.list.entrySet()){
                                observableList.add((Delivery) m.getValue());
                            }
                            table.setItems(observableList);
                        }
                        else
                            error_text.setText("Please enter a valid Worker ID: ");
                    }
                    else{
                        error_text.setText("Please enter a valid Worker ID:");
                    }
                }
                else{
                    error_text.setText("This ID Order already exists, try another");
                }
            }
            else{
                error_text.setText("Please fill all fields");
            }
        }
        else if(Delivery.m == 3){
            if(!deelivery_text.getText().equals("") && !Workerid_text.getText().equals("") &&
                    !city_text.getText().equals("") && !expences_text.getText().equals("")){
                if(!Delivery.list.containsKey(deelivery_text.getText())){
                    if(isNumeric(Workerid_text.getText())){
                        if(Integer.parseInt(Workerid_text.getText()) > 0){
                            SQL_connection3.addDelivery(deelivery_text.getText(), Workerid_text.getText(),
                                    expences_text.getText(), city_text.getText());
                            Delivery.m=0;
                            hide();
                            observableList = FXCollections.observableArrayList();
                            for(Map.Entry n : Delivery.list.entrySet()){
                                observableList.add((Delivery) n.getValue());
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

       public void delete() throws SQLException, ClassNotFoundException {
        hide();
        Delivery.current = table.getSelectionModel().getSelectedItem();
        if(Delivery.current == null)
            error_text.setText("Please select a Delivery from the table to Delete");
        else{
            if(Delivery.current.getOrders().size()==0){
                SQL_connection3.deleteDelivery();
                observableList = FXCollections.observableArrayList();
                for(Map.Entry m : Delivery.list.entrySet()){
                    observableList.add((Delivery) m.getValue());
                }
                table.setItems(observableList);
            }

        }
    }

    public void show(String title) {
        System.out.println(title);
        visible_label.setText(title);
        delivery_label.setText("Delivery_Date: ");
        Workerid_label.setText("Worker ID: ");
        expences_label.setText("Expences: ");
        city_label.setText("City:");
        deelivery_text.setVisible(true);
        deelivery_text.setText("");
        Workerid_text.setVisible(true);
        Workerid_text.setText("");
        expences_text.setVisible(true);
        expences_text.setText("");
        city_text.setVisible(true);
        city_text.setText("");
        ok.setVisible(true);
    }

    public void show2(String title) {
        System.out.println(title);
        visible_label.setText(title);
        Workerid_label.setText("Worker ID:");
        expences_label.setText("Expences:");
        delivery_label.setText("");
        city_label.setText("City:");
        deelivery_text.setVisible(false);
        Workerid_text.setVisible(true);
        Workerid_text.setText("");
        expences_text.setVisible(true);
        expences_text.setText("");
        city_text.setVisible(true);
        city_text.setText("");
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