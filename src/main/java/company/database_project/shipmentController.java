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
import java.util.Optional;
import java.util.ResourceBundle;


public class shipmentController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button insert;

    @FXML
    private Button view;

    @FXML
    private Button x;

    @FXML
    private Label error_text;

    @FXML
    private TableView<Shipment> table;

    @FXML
    private TableColumn<Shipment, Integer> shipment_id;

    @FXML
    private TableColumn<Shipment, String> shipment_employee;

    @FXML
    private TableColumn<Shipment, String> shipment_date;

    @FXML
    private TableColumn<Shipment, String> shipment_warehouse;

    @FXML
    private TableColumn<Shipment, Double> shipment_costs;

    @FXML
    private ComboBox month_selector;

    @FXML
    private ChoiceBox<String> checker_selector;

    @FXML
    private ChoiceBox<String> date_selector;

    @FXML
    private ChoiceBox<String> warehouse_selector;

    @FXML
    private Label costs_label;

    @FXML
    private TextField costs_box;

    @FXML
    private Label checker_label;

    @FXML
    private Label date_label;

    @FXML
    private Label warehouse_label;

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private Button add_machines;

    @FXML
    private Stage stage;

    @FXML
    private Map<MenuItem, String> menu;

    @FXML
    private Label machineNum_label;

    private ObservableList<Shipment> observableList = FXCollections.observableArrayList();
    private ObservableList<String> monthsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        machineNum_label.setText("");
        shipment_id.setCellValueFactory(new PropertyValueFactory<Shipment, Integer>("shipment_id"));
        shipment_employee.setCellValueFactory(new PropertyValueFactory<Shipment, String>("Driver_name"));
        shipment_date.setCellValueFactory(new PropertyValueFactory<Shipment, String>("shipment_date"));
        shipment_warehouse.setCellValueFactory(new PropertyValueFactory<Shipment, String>("warehouse_name"));
        shipment_costs.setCellValueFactory(new PropertyValueFactory<Shipment, Double>("costs"));
        month_selector.getItems().clear();
        hide();
        for(Map.Entry m : Shipment.dates.entrySet()){
            monthsList.add((String) m.getKey());
        }
        month_selector.setItems(monthsList);
    }

    public void display(ActionEvent e){
        hide();
        observableList.clear();
        String s = month_selector.getSelectionModel().getSelectedItem().toString();
        for(Map.Entry m : Shipment.list.entrySet()){
            Shipment w = (Shipment) m.getValue();
            if(Dates.stringMonth(w.getShipment_date()).equals(s)){
                observableList.add(w);
            }
        }
        table.setItems(observableList);
    }

    public void getNumberOfMachines(ActionEvent e){
        hide();
        Shipment.current = table.getSelectionModel().getSelectedItem();
        if(Shipment.current==null)
            error_text.setText("Please select a shipment to delete");
        else
            machineNum_label.setText(String.valueOf(Shipment.current.getMachines_list().size()));
    }

    public void delete(ActionEvent e) throws SQLException, ClassNotFoundException {
        hide();
        Shipment.current = table.getSelectionModel().getSelectedItem();
        if(Shipment.current==null)
            error_text.setText("Please select a shipment to delete");
        else {
            boolean f = true;
            for (Map.Entry m : Shipment.current.getMachines_list().entrySet()) {
                Machine M = (Machine) m.getValue();
                if (M instanceof Sold_machine) {
                    f = false;
                    break;
                }
            }
            if (f) {
                error_text.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting a shipment");
                alert.setContentText("Deleting this shipment will delete its machines too");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    SQL_connection.deleteShipment();
                    observableList = FXCollections.observableArrayList();
                    String s = month_selector.getSelectionModel().getSelectedItem().toString();
                    for(Map.Entry m : Shipment.list.entrySet()){
                        Shipment w = (Shipment) m.getValue();
                        if(Dates.stringMonth(w.getShipment_date()).equals(s)){
                            observableList.add(w);
                        }
                    }
                    table.setItems(observableList);
                }
            }
            else
                error_text.setText("This shipment contains machines that have already been sold");
        }
    }

    public void showMachines(ActionEvent e) throws IOException {
        hide();
        Shipment.current = table.getSelectionModel().getSelectedItem();
        if(Shipment.current==null)
            error_text.setText("No shipment is selected");
        else{
            Parent root = FXMLLoader.load(getClass().getResource("machineForShipment.fxml"));
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void newShipment(ActionEvent e){
        show();
        checker_selector.getItems().clear();
        date_selector.getItems().clear();
        warehouse_selector.getItems().clear();
        costs_box.setText("");
        for(Map.Entry m : Driver.active.entrySet()){
            Driver d = (Driver) m.getValue();
            checker_selector.getItems().add(d.getWorker_name());
        }
        String [] dates = Dates.pastWeekList();
        for(int m=0; m<8 ; m++){
            date_selector.getItems().add(dates[m]);
        }
        for(Map.Entry m : Warehouse.list.entrySet()){
            Warehouse w = (Warehouse) m.getValue();
            warehouse_selector.getItems().add(w.getName());
        }
    }

    public void MachinesForNewShipment(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        if(checker_selector.getValue()==null || date_selector.getValue()==null || warehouse_selector.getValue()==null ||
                costs_box.getText().equals("") || (!isNumeric(costs_box.getText()) && !costs_box.getText().equals("0")))
            error_text.setText("Please choose the data right (Numeric cost and nun empty fields)");
        else{
            Driver d = null;
            for(Map.Entry m : Driver.active.entrySet()){
                Driver de = (Driver) m.getValue();
                if(checker_selector.getValue().equals(de.getWorker_name())) {
                    d = de;
                    break;
                }
            }
            Shipment.current = new Shipment(d.getWorker_id(), date_selector.getValue(), warehouse_selector.getValue(),
                    Double.parseDouble(costs_box.getText()));
            SQL_connection.createShipment();
            Parent root = FXMLLoader.load(getClass().getResource("addMachinesToShipment.fxml"));
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void hide(){
        checker_label.setVisible(false);
        date_label.setVisible(false);
        warehouse_label.setVisible(false);
        costs_label.setVisible(false);
        checker_selector.setVisible(false);
        date_selector.setVisible(false);
        warehouse_selector.setVisible(false);
        costs_box.setVisible(false);
        add_machines.setVisible(false);
        error_text.setText("");
    }

    public void show(){
        checker_label.setVisible(true);
        date_label.setVisible(true);
        warehouse_label.setVisible(true);
        costs_label.setVisible(true);
        checker_selector.setVisible(true);
        date_selector.setVisible(true);
        warehouse_selector.setVisible(true);
        costs_box.setVisible(true);
        add_machines.setVisible(true);
        error_text.setText("");
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
            if (d <= 0)
                return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
