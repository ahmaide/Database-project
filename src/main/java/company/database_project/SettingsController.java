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
import java.util.Locale;
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
    private Label error_text;

    @FXML
    private Stage stage;

    @FXML
    private Stage stage2;

    @FXML
    private PasswordField password_to_add;

    @FXML
    private TextField new_username;

    @FXML
    private PasswordField new_user_password;

    @FXML
    private Label new_user_error;

    @FXML
    private Label deactivate_error;

    @FXML
    private PasswordField password_deactive;

    @FXML
    private Label newPassword_error;

    @FXML
    private PasswordField currentPassword_fornew;

    @FXML
    private PasswordField newPassword2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(SQL_connection.m + " " + SQL_connection.m);
        if(SQL_connection.m==0)
            error_text.setText("");
        else if(SQL_connection.m==2)
            new_user_error.setText("");
        else if(SQL_connection.m==1)
            deactivate_error.setText("");
        else if(SQL_connection.m==3)
            newPassword_error.setText("");
    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }


    public void exit2(ActionEvent e){
        SQL_connection.m=0;
        stage2 = (Stage) pane2.getScene().getWindow();
        stage2.close();
    }

    public void exit3(ActionEvent e){
        SQL_connection.m=0;
        stage2 = (Stage) pane3.getScene().getWindow();
        stage2.close();
    }

    public void exit4(ActionEvent e){
        SQL_connection.m=0;
        stage2 = (Stage) pane4.getScene().getWindow();
        stage2.close();
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayDeactive(ActionEvent e) throws IOException {
        if(Users.currentUser.getUsername().toLowerCase(Locale.ROOT).equals("ahmaide")){
            error_text.setText("That is the Initial account you have it can't be deleted");
        }
        else {
            SQL_connection.m=1;
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("deactivate.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage2 = new Stage();
            stage2.setScene(scene);
            stage2.setTitle("Deactiviate");
            stage2.initStyle(StageStyle.UNDECORATED);
            stage2.show();
        }
    }

    public void displayNewAccount(ActionEvent e) throws IOException {
        SQL_connection.m=2;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Add new Account");
        stage2. initStyle(StageStyle. UNDECORATED);
        stage2.show();
    }

    public void displayChangePassword(ActionEvent e) throws IOException {
        SQL_connection.m=3;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("changePassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage2 = new Stage();
        stage2.setScene(scene);
        stage2.setTitle("Change Password");
        stage2. initStyle(StageStyle. UNDECORATED);
        stage2.show();
    }

}
