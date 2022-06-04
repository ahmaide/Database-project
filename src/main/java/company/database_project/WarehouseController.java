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
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {

    @FXML
    Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button back;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @FXML
    private Button view;

    @FXML
    private Button x;

    @FXML
    private Label error_text;

    @FXML
    private Button add;

    @FXML
    private TableView<Warehouse> table;

    @FXML
    private TableColumn<Warehouse, String> warehouse_name;

    @FXML
    private TableColumn<Warehouse, String> warehouse_address;

    @FXML
    private TableColumn<Warehouse, String> warehouse_type;

    @FXML
    private TableColumn<Warehouse, Integer> warehouse_floors;

    @FXML
    private Label visible_label;

    @FXML
    private Label name_label;

    @FXML
    private TextField name_text;

    @FXML
    private Label address_label;

    @FXML
    private TextField address_text;

    @FXML
    private Label type_label;

    @FXML
    private TextField type_text;

    @FXML
    private Label floors_label;

    @FXML
    private TextField floors_text;

    @FXML
    private Button ok;

    @FXML
    private Label delete_label;

    @FXML
    private ChoiceBox delete_options;

    @FXML
    private Button delete_ok;

    private ObservableList<Warehouse> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Map.Entry m : Warehouse.list.entrySet()){
            observableList.add((Warehouse) m.getValue());
        }
        warehouse_name.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("name"));
        warehouse_address.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("Address"));
        warehouse_type.setCellValueFactory(new PropertyValueFactory<Warehouse, String>("type_building"));
        warehouse_floors.setCellValueFactory(new PropertyValueFactory<Warehouse, Integer>("Floors"));
        table.setItems(observableList);
        hide();
        hideDelete();
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


    public void editShow(){
        Warehouse.m=1;
        hideDelete();
        Warehouse.current = table.getSelectionModel().getSelectedItem();
        if(Warehouse.current == null)
            error_text.setText("Please select a warehouse from the table");
        else {
            show2("Edit " + Warehouse.current.getName() + ":");
            System.out.println(Warehouse.current.getName());
            error_text.setText("");
            ok.setText("Edit");
        }
    }

    public void addShow(){
        Warehouse.m = 3;
        hideDelete();
        show("New Warehouse:");
        error_text.setText("");
        ok.setText("Add");
    }

    public void showMachines(ActionEvent e){
        hideDelete();
        hide();
        Warehouse.current = table.getSelectionModel().getSelectedItem();
        if(Warehouse.current==null)
            error_text.setText("Please select a warehouse from the table");
        else{
            error_text.setText("");
        }
    }

    public void delete() throws SQLException, ClassNotFoundException {
        hide();
        Warehouse.current = table.getSelectionModel().getSelectedItem();
        if(Warehouse.current == null)
            error_text.setText("Please select a warehouse from the table");
        else{
            if(Warehouse.current.getMachines_list().size()==0){
                SQL_connection.deleteWarehouse(Warehouse.current, 1, null);
                observableList = FXCollections.observableArrayList();
                for(Map.Entry m : Warehouse.list.entrySet()){
                    observableList.add((Warehouse) m.getValue());
                }
                table.setItems(observableList);
            }
            else{
                delete_options.getItems().clear();
                delete_label.setText("Select new warehouse for machines:");
                delete_ok.setVisible(true);
                delete_options.setVisible(true);
                for(Map.Entry m : Warehouse.list.entrySet()){
                    if(!m.getKey().equals(Warehouse.current.getName()))
                    delete_options.getItems().add(m.getKey());
                }
            }
        }
    }

    public void okDelete(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(delete_options.getValue()==null)
            error_text.setText("Choose a warehouse for the machines");
        else {
            error_text.setText("");
            Warehouse replacment = Warehouse.list.get(delete_options.getValue());
            SQL_connection.deleteWarehouse(Warehouse.current, 2, replacment);
            observableList = FXCollections.observableArrayList();
            for (Map.Entry m : Warehouse.list.entrySet()) {
                observableList.add((Warehouse) m.getValue());
            }
            table.setItems(observableList);
        }
    }

    public void execute(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(Warehouse.m==1){
            boolean num = false;
            if(floors_text.getText().equals(""))
                num = true;
            else {
                    num = isNumeric(floors_text.getText());
            }

            if(num){
                if( floors_text.getText().equals("") || Integer.parseInt(floors_text.getText()) > 0){
                        SQL_connection.editWarehouse(address_text.getText(),
                                type_text.getText(), floors_text.getText());
                        Warehouse.m=0;
                        hide();
                        observableList = FXCollections.observableArrayList();
                        for(Map.Entry m : Warehouse.list.entrySet()){
                            observableList.add((Warehouse) m.getValue());
                        }
                        table.refresh();
                }
                else{
                    error_text.setText("Please enter a valid floors number");
                }
            }
            else{
                error_text.setText("Please enter a valid floors number");
            }
        }
        else if(Warehouse.m == 3){
            if(!name_text.getText().equals("") && !address_text.getText().equals("") &&
            !type_text.getText().equals("") && !floors_text.getText().equals("")){
                if(!Warehouse.list.containsKey(name_text.getText())){
                    if(isNumeric(floors_text.getText())){
                        if(Integer.parseInt(floors_text.getText()) > 0){
                            SQL_connection.addWarehouse(name_text.getText(), address_text.getText(),
                                    type_text.getText(), floors_text.getText());
                            Warehouse.m=0;
                            hide();
                            observableList = FXCollections.observableArrayList();
                            for(Map.Entry m : Warehouse.list.entrySet()){
                                observableList.add((Warehouse) m.getValue());
                            }
                            table.setItems(observableList);
                        }
                        else
                            error_text.setText("Please enter a valid floors number");
                    }
                    else{
                        error_text.setText("Please enter a valid floors number");
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

    public void hide(){
        visible_label.setText("");
        name_label.setText("");
        address_label.setText("");
        type_label.setText("");
        floors_label.setText("");
        error_text.setText("");
        name_text.setVisible(false);
        address_text.setVisible(false);
        type_text.setVisible(false);
        floors_text.setVisible(false);
        ok.setVisible(false);
    }

    public void hideDelete(){
        delete_label.setText("");
        delete_options.setVisible(false);
        delete_ok.setVisible(false);
    }

    public void show(String title){
        System.out.println(title);
        visible_label.setText(title);
        name_label.setText("Name:");
        address_label.setText("Address:");
        type_label.setText("Type:");
        floors_label.setText("Floors:");
        name_text.setVisible(true);
        name_text.setText("");
        address_text.setVisible(true);
        address_text.setText("");
        type_text.setVisible(true);
        type_text.setText("");
        floors_text.setVisible(true);
        floors_text.setText("");
        ok.setVisible(true);
    }

    public void show2(String title){
        System.out.println(title);
        visible_label.setText(title);
        address_label.setText("Address:");
        name_label.setText("");
        type_label.setText("Type:");
        floors_label.setText("Floors:");
        name_text.setVisible(false);
        address_text.setVisible(true);
        address_text.setText("");
        type_text.setVisible(true);
        type_text.setText("");
        floors_text.setVisible(true);
        floors_text.setText("");
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
