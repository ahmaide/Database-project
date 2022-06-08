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

public class OrdersController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label error_text;

    @FXML
    private ChoiceBox<String> date_selector;

    @FXML
    private Label date_label;

    @FXML
    private TextField discount_box;

    @FXML
    private ChoiceBox<String> type_selector;

    @FXML
    private Label type_label;

    @FXML
    private ChoiceBox<String> pay_selector;

    @FXML
    private Label payMethod_label;

    @FXML
    private Label discount_label;

    @FXML
    private TextField customerId_box;

    @FXML
    private Label customerId_label;

    @FXML
    private ChoiceBox<String> seller_selector;

    @FXML
    private Label seller_label;

    @FXML
    private TextField customerName_box;

    @FXML
    private Label customerName_label;

    @FXML
    private Label address_label;

    @FXML
    private TextField address_box;

    @FXML
    private Label buisness_label;

    @FXML
    private TextField buisness_box;

    @FXML
    private Label phone_label;

    @FXML
    private TextField phone_box;

    @FXML
    private RadioButton delivery_radioButton;

    @FXML
    private ChoiceBox<String> deliveries_selector;

    @FXML
    private Label available_label;

    @FXML
    private Button addOrder;

    @FXML
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hideAll();
    }

    public void exit(ActionEvent e) throws IOException {
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void hideAll(){
        date_label.setVisible(false);
        date_selector.setVisible(false);
        type_label.setVisible(false);
        type_selector.setVisible(false);
        pay_selector.setVisible(false);
        payMethod_label.setVisible(false);
        discount_box.setVisible(false);
        discount_label.setVisible(false);
        customerId_box.setVisible(false);
        customerId_label.setVisible(false);
        customerName_box.setVisible(false);
        customerName_label.setVisible(false);
        address_box.setVisible(false);
        address_label.setVisible(false);
        buisness_label.setVisible(false);
        buisness_box.setVisible(false);
        phone_box.setVisible(false);
        phone_label.setVisible(false);
        seller_label.setVisible(false);
        seller_selector.setVisible(false);
        delivery_radioButton.setVisible(false);
        available_label.setVisible(false);
        deliveries_selector.setVisible(false);
        addOrder.setVisible(false);
        error_text.setText("");
    }

}
