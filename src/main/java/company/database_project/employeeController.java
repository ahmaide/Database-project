package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class employeeController implements Initializable {
    @FXML
    Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button back;

    @FXML
    private Button driver;

    @FXML
    private Button sallers;

    @FXML
    private Button x;

    public void exit(ActionEvent e) {
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


    public void driver(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sellers(ActionEvent actionEvent) {

    }
}
