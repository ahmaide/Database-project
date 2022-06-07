package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;



public class AddMachineToShipmentController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Stage stage;

    @FXML
    private ChoiceBox<String> machine_types;

    @FXML
    private TextField number_of_machines;

    @FXML
    private Label title;

    @FXML
    private Label error_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_text.setText("");
        title.setText("Add Machines to shipment " + Shipment.current.getShipment_id());
        for(Map.Entry m : Machine_type.list.entrySet()){
            Machine_type mt = (Machine_type) m.getValue();
            machine_types.getItems().add(mt.getType_id());
        }
    }

    public void cancel(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
        if(Shipment.current.getMachines_list().size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deleting shipment");
            alert.setContentText("This shipment doesn't have any machines so it won't be saved");
            alert.show();
            SQL_connection.deleteShipment();
        }
        Parent root = FXMLLoader.load(getClass().getResource("shipment.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void insert(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(machine_types.getValue()==null || number_of_machines.getText().equals("") || !isNumeric(number_of_machines.getText()))
            error_text.setText("Please select a machine time or enter a valid number");
        else{
            if(Integer.parseInt(number_of_machines.getText()) > 20)
                error_text.setText("Number of machines should be at most 20");
            else {
                error_text.setText("");
                SQL_connection.addMachines(machine_types.getValue(), Integer.parseInt(number_of_machines.getText()));
                number_of_machines.setText("");
            }
        }
    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
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
