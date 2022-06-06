package company.database_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Machine_warehousrController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button back;

    @FXML
    private Label title;

    @FXML
    private Button send;

    @FXML
    private Button change;

    @FXML
    private Button delete;

    @FXML
    private Button x;

    @FXML
    private Label error_text;

    @FXML
    private TableView<Stored_machine> table;

    @FXML
    private TableColumn<Stored_machine, String> machine_id;

    @FXML
    private TableColumn<Stored_machine, String> machine_type;

    @FXML
    private TableColumn<Stored_machine, Integer> machine_shipment;

    @FXML
    private Button ok2;

    @FXML
    private ChoiceBox<?> other_types;

    @FXML
    private ChoiceBox<?> other_warehouses;

    @FXML
    private Button ok1;

    private ObservableList<Stored_machine> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("View machines for " + Warehouse.current.getName());
        for(Map.Entry m : Warehouse.current.getMachines_list().entrySet()){
            observableList.add((Stored_machine) m.getValue());
        }
        machine_id.setCellValueFactory(new PropertyValueFactory<Stored_machine, String>("machine_id"));
        machine_type.setCellValueFactory(new PropertyValueFactory<Stored_machine, String>("type_id"));
        machine_shipment.setCellValueFactory(new PropertyValueFactory<Stored_machine, Integer>("shipment_id"));
        table.setItems(observableList);
        other_warehouses.setVisible(false);
        other_types.setVisible(false);
        ok1.setVisible(false);
        ok2.setVisible(false);
        error_text.setText("");
    }

    public void clickDelete(ActionEvent e){

    }
}
