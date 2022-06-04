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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
