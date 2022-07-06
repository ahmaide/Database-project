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
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class Type_of_machineController implements Initializable {

    @FXML
    Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<Machine_type, String> Color;

    @FXML
    private TableColumn<Machine_type, String> Cups;

    @FXML
    private TableColumn<Machine_type, ArrayList> NumofDrink;

    @FXML
    private TableColumn<Machine_type, Double> Price;

    @FXML
    private Button back;

    @FXML
    private Button add;

    @FXML
    private Label color_label;

    @FXML
    private TextField color_text;

    @FXML
    private Label error_text;

    @FXML
    private Button insert;

    @FXML
    private Label price_label;

    @FXML
    private TextField price_text;

    @FXML
    private TableView<Machine_type> table;

    @FXML
    private Label type_of_cups_label;

    @FXML
    private TextField type_of_cups_text;


    @FXML
    private TableColumn<Machine_type, Integer> typeid;

    @FXML
    private Label typeid_label;

    @FXML
    private TextField typeid_text;

    @FXML
    private Button view;

    @FXML
    private Label visible_label;

    @FXML
    private Button x;

    private ObservableList<Machine_type> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Map.Entry m : Machine_type.list.entrySet()) {
            observableList.add((Machine_type) m.getValue());
        }
        typeid.setCellValueFactory(new PropertyValueFactory<Machine_type, Integer>("type_id"));
        Price.setCellValueFactory(new PropertyValueFactory<Machine_type, Double>("price"));
        Cups.setCellValueFactory(new PropertyValueFactory<Machine_type, String>("cups"));
        Color.setCellValueFactory(new PropertyValueFactory<Machine_type, String>("color"));
        table.setItems(observableList);
        hide();
    }

    public void hide() {
        visible_label.setText("");
        typeid_label.setText("");
        price_label.setText("");
        color_label.setText("");
        type_of_cups_label.setText("");
        error_text.setText("");
        typeid_text.setVisible(false);
        color_text.setVisible(false);
        price_text.setVisible(false);
        type_of_cups_text.setVisible(false);
        add.setVisible(false);
    }

    public void show(String title) {
        System.out.println(title);
        visible_label.setText(title);
        typeid_label.setText("Type_id:");
        price_label.setText("Price:");
        type_of_cups_label.setText("Type_of_cups:");
        color_label.setText("Color:");
        typeid_text.setVisible(true);
        typeid_text.setText("");
        price_text.setVisible(true);
        price_text.setText("");
        type_of_cups_text.setVisible(true);
        type_of_cups_text.setText("");
        color_text.setVisible(true);
        color_text.setText("");
        add.setVisible(true);
    }

    public void showMachines(ActionEvent e) throws IOException {
        hide();
        Machine_type.current = table.getSelectionModel().getSelectedItem();
        if (Machine_type.current == null)
            error_text.setText("Please select a Machine_type from the table");
        else {
            error_text.setText("");
            Parent root = FXMLLoader.load(getClass().getResource("machineForWarehouse.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void addMachines(ActionEvent actionEvent) {
        Machine_type.m = 3;
        show("New Type_of_machine:");
        error_text.setText("");
        add.setText("Add");
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

    public void add(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SQL_connection3.addType(typeid_text.getText(), price_text.getText(), type_of_cups_text.getText(), color_text.getText());
        observableList = FXCollections.observableArrayList();
        for (Map.Entry m : Machine_type.list.entrySet()) {
            observableList.add((Machine_type) m.getValue());
        }
        table.setItems(observableList);
     }
}

