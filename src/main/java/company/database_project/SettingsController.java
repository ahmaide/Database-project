package company.database_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private AnchorPane pane;

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
    private VBox deactive_box;

    @FXML
    private PasswordField password_deactive;

    @FXML
    private Label error1;

    @FXML
    private Button deactive3;

    @FXML
    private VBox new_account_box;

    @FXML
    private PasswordField password_box1;

    @FXML
    private TextField new_username;

    @FXML
    private PasswordField new_password;

    @FXML
    private Label error2;

    @FXML
    private Button addAcocount;

    @FXML
    private VBox change_password_box;

    @FXML
    private PasswordField currentPassword_fornew;

    @FXML
    private PasswordField newPassword2;

    @FXML
    private PasswordField reenterNewPassword;

    @FXML
    private Label error3;

    @FXML
    private Button changePassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Node n : deactive_box.getChildren()){
            n.setManaged(false);
        }
        deactive_box.setManaged(false);
        for(Node n : new_account_box.getChildren()){
            n.setManaged(false);
        }
        new_account_box.setManaged(false);
        for(Node n : change_password_box.getChildren()){
            n.setManaged(false);
        }
        change_password_box.setManaged(false);
    }

    public void exit(ActionEvent e){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
