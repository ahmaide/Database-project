package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane pane2;

    @FXML
    private AnchorPane pane3;

    @FXML
    private AnchorPane pane4;

    @FXML
    private Button back;

    @FXML
    private Button deactive;

    @FXML
    private Button newAccount;

    @FXML
    private Button change_password;

    @FXML
    private Button x;

    @FXML
    private Stage stage;

    @FXML
    private Stage stage2;

    static int m=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }


    public void exit2(ActionEvent e){
        stage2 = (Stage) pane2.getScene().getWindow();
        stage2.close();
        m=0;
    }

    public void exit3(ActionEvent e){
        stage2 = (Stage) pane3.getScene().getWindow();
        stage2.close();
        m=0;
    }

    public void exit4(ActionEvent e){
        stage2 = (Stage) pane4.getScene().getWindow();
        stage2.close();
        m=0;
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayDeactive(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("deactivate.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Deactiviate");
        stage2. initStyle(StageStyle. UNDECORATED);
        stage2.show();
        m=1;
    }

    public void displayNewAccount(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Add new Account");
        stage2. initStyle(StageStyle. UNDECORATED);
        stage2.show();
        m=2;
    }

    public void displayChangePassword(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("changePassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Change Password");
        stage2. initStyle(StageStyle. UNDECORATED);
        stage2.show();
        m=3;
    }
}
