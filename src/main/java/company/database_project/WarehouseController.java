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
        Warehouse.current = table.getSelectionModel().getSelectedItem();
        if(Warehouse.current == null)
            error_text.setText("Please select an item from the table");
        else {
            show("Edit " + Warehouse.current.getName() + ":");
            System.out.println(Warehouse.current.getName());
            error_text.setText("");
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

    public void show(String title){
        System.out.println(title);
        visible_label.setText(title);
        name_label.setText("Name:");
        address_label.setText("Address:");
        type_label.setText("Type:");
        floors_label.setText("Floors:");
        name_text.setVisible(true);
        address_text.setVisible(true);
        type_text.setVisible(true);
        floors_text.setVisible(true);
        ok.setVisible(true);
        ok.setText("Edit");
    }

}
