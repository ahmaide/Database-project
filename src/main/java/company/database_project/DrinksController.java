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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class DrinksController implements Initializable {

    @FXML
    Stage stage;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<Drinks, Integer> DrinkID;

    @FXML
    private TableColumn<Drinks, String> DrinkName;

    @FXML
    private Button add;

    @FXML
    private Button back;

    @FXML
    private TableColumn<Drinks, String> companyName;

    @FXML
    private Label company_label;

    @FXML
    private TextField company_text;

    @FXML
    private Button delete;

    @FXML
    private Button edit;

    @FXML
    private Label error_text;

    @FXML
    private Label name_label;

    @FXML
    private TextField name_text;

    @FXML
    private Button ok;

    @FXML
    private TableColumn<Drinks, Double> priceNum;


    @FXML
    private Label price_label;

    @FXML
    private TextField price_text;


    @FXML
    private TableView<Drinks> table;

    @FXML
    private Label visible_label;

    @FXML
    private Button x;

    private ObservableList<Drinks> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Map.Entry m : Drinks.list.entrySet()) {
            observableList.add((Drinks) m.getValue());
        }
        DrinkID.setCellValueFactory(new PropertyValueFactory<Drinks, Integer>("ID"));
        DrinkName.setCellValueFactory(new PropertyValueFactory<Drinks, String>("Name"));
        companyName.setCellValueFactory(new PropertyValueFactory<Drinks, String>("company Name"));
        priceNum.setCellValueFactory(new PropertyValueFactory<Drinks, Double>("price"));
        table.setItems(observableList);
        hide();
    }
    public void exit(ActionEvent e){
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



    public void execute(ActionEvent e) throws SQLException, ClassNotFoundException {
        if(Drinks.d==1){
            boolean num = false;
            if(price_text.getText().equals(""))
                num = true;
            else {
                num = isNumeric(price_text.getText());
            }

            if(num){
                if( price_text.getText().equals("") || Double.parseDouble(price_text.getText()) > 0){

                    Drinks.d=0;
                    hide();
                    observableList = FXCollections.observableArrayList();
                    for(Map.Entry m : Drinks.list.entrySet()){
                        observableList.add((Drinks) m.getValue());
                    }
                    table.refresh();
                }
                else{
                    error_text.setText("Please enter a valid price");
                }
            }
            else{
                error_text.setText("Please enter a valid price");
            }
        }
        else if(Drinks.d == 3){
            if(!company_text.getText().equals("") &&
                    !company_text.getText().equals("") && !price_text.getText().equals("")){
                if(!Drinks.list.containsKey(name_text.getText())){
                    if(isNumeric(price_text.getText())){
                        if(Integer.parseInt(price_text.getText()) > 0){
                            SQL_connection.editdrink(name_text.getText(), company_text.getText(), price_text.getText());
                            Drinks.d=0;
                            hide();
                            observableList = FXCollections.observableArrayList();
                            for(Map.Entry m : Drinks.list.entrySet()){
                                observableList.add((Drinks) m.getValue());
                            }
                            table.setItems(observableList);
                        }
                        else
                            error_text.setText("Please enter a valid price: ");
                    }
                    else{
                        error_text.setText("Please enter a valid price:");
                    }
                }
                else{
                    error_text.setText("This ID Drink already exists, try another");
                }
            }
            else{
                error_text.setText("Please fill all fields");
            }
        }
    }

    public void hide(){
        visible_label.setText("");
        name_label.setText("");
        company_label.setText("");
        price_label.setText("");
        error_text.setText("");
        name_text.setVisible(false);
        company_text.setVisible(false);
        price_text.setVisible(false);
        ok.setVisible(false);
    }

    public void show(String title){
        System.out.println(title);
        visible_label.setText(title);
        name_label.setText("Name:");
        company_label.setText("Company:");
        price_label.setText("Price:");
        name_text.setVisible(true);
        name_text.setText("");
        company_text.setVisible(true);
        company_text.setText("");
        price_text.setVisible(true);
        price_text.setText("");
        ok.setVisible(true);
    }

    public void show2(String title){
        System.out.println(title);
        visible_label.setText(title);
        company_label.setText("Company:");
        name_label.setText("");
        price_label.setText("Price:");
        name_text.setVisible(false);
        company_text.setVisible(true);
        company_text.setText("");
        price_text.setVisible(true);
        price_text.setText("");
        ok.setVisible(true);
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

    public void addshow(ActionEvent actionEvent) {
        Drinks.d = 3;
        show("New Drink:");
        error_text.setText("");
        ok.setText("Add");
    }

    public void editshow(ActionEvent actionEvent) {
        Drinks.d=1;
        Drinks.current = table.getSelectionModel().getSelectedItem();
        if(Drinks.current == null)
            error_text.setText("Please select a Drink from the table");
        else {
            show2("Edit " + Drinks.current.getDrink_id() + ":");
            System.out.println(Drinks.current.getDrink_id());
            error_text.setText("");
            ok.setText("Edit");
        }
    }

    public void delete(ActionEvent actionEvent) {
    }
}
