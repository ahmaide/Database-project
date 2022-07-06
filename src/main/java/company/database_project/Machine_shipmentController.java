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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Machine_shipmentController implements Initializable {

    @FXML
    private Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label title;

    @FXML
    private TableView<Machine> table;

    @FXML
    private TableColumn<Machine, String> machine_id;

    @FXML
    private TableColumn<Machine, String> machine_type;

    @FXML
    private TableColumn<Machine, Boolean> status;

    private ObservableList<Machine> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("View Machines for Shipment " + Shipment.current.getShipment_id());
        for(Map.Entry m : Shipment.current.getMachines_list().entrySet()){
            observableList.add((Machine) m.getValue());
        }
        machine_id.setCellValueFactory(new PropertyValueFactory<Machine, String>("machine_id"));
        machine_type.setCellValueFactory(new PropertyValueFactory<Machine, String>("type_id"));
        status.setCellValueFactory(new PropertyValueFactory<Machine, Boolean>("status"));
        table.setItems(observableList);
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("shipment.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
