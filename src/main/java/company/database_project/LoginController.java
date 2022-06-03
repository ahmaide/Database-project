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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button login_Button;

    @FXML
    private TextField username_box;

    @FXML
    private PasswordField password_box;

    @FXML
    private Label error_text;

    @FXML
    private Button exit_button;

    @FXML
    private Stage stage;

    @FXML
    private AnchorPane pane;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_text.setText("");

    }

    public void exit(ActionEvent e){
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void loginTry(ActionEvent e) throws IOException {
        if(username_box.getText() == ""){
            error_text.setText("Username is empty!");
        }
        else{
            if(password_box.getText() == ""){
                error_text.setText("Password is empty!");
            }
            else{
                if(Users.list.containsKey(username_box.getText())){
                    if(Users.list.get(username_box.getText()).getPassword().equals(password_box.getText()) ){
                        Users.currentUser = Users.list.get(username_box.getText());
                        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
                        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        error_text.setText("Wrong password");
                    }
                }
                else{
                    error_text.setText("This username doesn't exist");
                }
            }
        }
    }
}