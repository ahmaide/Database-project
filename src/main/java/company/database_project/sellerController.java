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


public class sellerController implements Initializable {
    @FXML
    Stage stage;

    @FXML
    private TableColumn<Seller, Boolean> Activtiy;

    @FXML
    private Label activity_label;

    @FXML
    private TextField activity_text;

    @FXML
    private Label actual_label;

    @FXML
    private TextField actual_text;
    @FXML
    private TableView<Seller> table;

    @FXML
    private Button add;
    @FXML
    private AnchorPane pane;

    @FXML
    private Button back;

    @FXML
    private Button delete;

    @FXML
    private Button edit;

    @FXML
    private Label error_text;

    @FXML
    private Label id_label;

    @FXML
    private TextField id_text;

    @FXML
    private TableColumn<Seller, Dates> leave_date;

    @FXML
    private Label leave_label;

    @FXML
    private TextField leave_text;

    @FXML
    private Label name_label;

    @FXML
    private TextField name_text;

    @FXML
    private Button ok;

    @FXML
    private Label phone_label;

    @FXML
    private TextField phone_text;

    @FXML
    private TableColumn<Seller, Dates> start_date;

    @FXML
    private Label start_label;

    @FXML
    private TextField start_text;

    @FXML
    private Label visible_label;

    @FXML
    private TableColumn<Seller, Integer> worker_actualID;

    @FXML
    private TableColumn<Seller, Integer> worker_id;

    @FXML
    private TableColumn<Seller, String> worker_name;

    @FXML
    private TableColumn<Seller, Integer> worker_phone;

    @FXML
    private Button x;
    private ObservableList<Seller> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Map.Entry m : Seller.list.entrySet()) {
            observableList.add((Seller) m.getValue());
        }
        worker_id.setCellValueFactory(new PropertyValueFactory<Seller, Integer>("worker_id"));
        worker_name.setCellValueFactory(new PropertyValueFactory<Seller, String>("worker_name"));
        worker_actualID.setCellValueFactory(new PropertyValueFactory<Seller, Integer>("worker_actualID"));
        worker_phone.setCellValueFactory(new PropertyValueFactory<Seller, Integer>("worker_phone"));
        Activtiy.setCellValueFactory(new PropertyValueFactory<Seller, Boolean>("Activity"));
        start_date.setCellValueFactory(new PropertyValueFactory<Seller, Dates>("start_date"));
        leave_date.setCellValueFactory(new PropertyValueFactory<Seller, Dates>("leave_date"));
        table.setItems(observableList);
        hide();

    }

    private void hide() {
        visible_label.setText("");
        id_label.setText("");
        name_label.setText("");
        actual_label.setText("");
        phone_label.setText("");
        activity_label.setText("");
        leave_label.setText("");
        start_date.setText("");
        error_text.setText("");
        name_text.setVisible(false);
        id_text.setVisible(false);
        phone_text.setVisible(false);
        actual_text.setVisible(false);
        activity_text.setVisible(false);
        leave_text.setVisible(false);
        start_text.setVisible(false);
        edit.setVisible(false);
        add.setVisible(false);

    }

    public void exit(ActionEvent e) {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void editShow(ActionEvent e) {
        Seller.m = 1;
        Seller.current = table.getSelectionModel().getSelectedItem();
        if (Seller.current == null)
            error_text.setText("Please select a Seller from the table");
        else {
            show2("Edit " + Seller.current.getWorker_id() + ":");
            System.out.println(Seller.current.getWorker_id());
            error_text.setText("");
            edit.setText("Edit");
        }
    }




        public void addShow(ActionEvent e)throws SQLException,ClassNotFoundException{
            Seller.m = 3;
            show("New Seller:");
            error_text.setText("");
            ok.setText("Add");
        }

        public void execute(ActionEvent e) throws  SQLException, ClassNotFoundException{
            if (Seller.m == 1) {
                boolean num = false;
                if (id_text.getText().equals(""))
                    num = true;
                else {
                    num = isNumeric(id_text.getText());
                }

                if (num) {
                    if (id_text.getText().equals("") || Integer.parseInt(id_text.getText()) > 0) {
                        SQL_connection2.editSeller(name_text.getText(),
                                actual_text.getText(), phone_text.getText(), activity_text.getText(), start_text.getText(), leave_label.getText());
                        Seller.m = 0;
                        hide();
                        observableList = FXCollections.observableArrayList();
                        for (Map.Entry m : Seller.list.entrySet()) {
                            observableList.add((Seller) m.getValue());
                        }
                        table.refresh();
                    } else {
                        error_text.setText("Please enter a valid id number");
                    }
                } else {
                    error_text.setText("Please enter a valid id number");
                }
            } else if (Seller.m == 3) {
                if (!id_text.getText().equals("") && !name_text.getText().equals("") &&
                        !actual_text.getText().equals("") && !phone_text.getText().equals("") && !start_text.getText().equals("") && !activity_text.getText().equals("") && !leave_text.getText().equals("")) {
                    if (!Seller.list.containsKey(id_text.getText())) {
                        if (isNumeric(id_text.getText())) {
                            if (Integer.parseInt(id_text.getText()) > 0) {
                                SQL_connection2.addSeller(id_text.getText(), name_text.getText(), actual_text.getText(),
                                        phone_text.getText(), activity_text.getText(), start_text.getText(), leave_text.getText());
                                Seller.m = 0;
                                hide();
                                observableList = FXCollections.observableArrayList();
                                for (Map.Entry m : Driver.list.entrySet()) {
                                    observableList.add((Seller) m.getValue());
                                }
                                table.setItems(observableList);
                            } else
                                error_text.setText("Please enter a valid id number");
                        } else {
                            error_text.setText("Please enter a valid id number");
                        }
                    } else {
                        error_text.setText("This warehouse worker name already exists, try another");
                    }
                } else {
                    error_text.setText("Please fill all fields");
                }
            }

        }
        public void show(String title){
            System.out.println(title);
            visible_label.setText(title);
            id_label.setText("Worker_id:");
            name_label.setText("Worker_name:");
            actual_label.setText("ActualID:");
            phone_label.setText("Phone_number:");
            start_label.setText("Start_date:");
            leave_label.setText("Leave_date:");
            activity_label.setText("Activity:");
            name_text.setVisible(true);
            name_text.setText("");
            id_text.setVisible(true);
            id_text.setText("");
            actual_text.setVisible(true);
            actual_text.setText("");
            phone_text.setVisible(true);
            phone_text.setText("");
            start_text.setVisible(true);
            start_text.setText("");
            leave_text.setVisible(true);
            leave_text.setText("");
            activity_text.setVisible(true);
            activity_text.setText("");
            add.setVisible(true);
        }

        public void show2(String title){
            System.out.println(title);
            visible_label.setText(title);
            name_label.setText("Worker_name");
            actual_label.setText("ActualID:");
            phone_label.setText("Worker_phone:");
            start_label.setText("Start_date:");
            leave_label.setText("Leave_date:");
            activity_label.setText("Leave_date:");
            name_text.setVisible(true);
            name_text.setText("");
            id_text.setVisible(false);
            id_label.setText("");
            actual_text.setVisible(true);
            actual_text.setText("");
            phone_text.setVisible(true);
            phone_text.setText("");
            start_text.setVisible(true);
            start_text.setText("");
            leave_text.setVisible(true);
            leave_text.setText("");
            activity_text.setVisible(true);
            activity_text.setText("");
            edit.setVisible(true);
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
