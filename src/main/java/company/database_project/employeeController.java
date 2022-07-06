package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class employeeController {

    @FXML
    private Button back;

    @FXML
    private Button driver;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button sellers;

    @FXML
    private Button x;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void driver(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("driver.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene driver = new Scene(root);
        stage.setScene(driver);
        stage.show();
        stage.setTitle("driver");
    }

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();

    }

    @FXML
    void sellers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("seller.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene seller = new Scene(root);
        stage.setScene(seller);
        stage.show();
        stage.setTitle("seller");
    }

}
