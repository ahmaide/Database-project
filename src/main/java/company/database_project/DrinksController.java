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
    private TableColumn<Drinks, String> countryName;

    @FXML
    private Label country_label;

    @FXML
    private Button adddrink;

    @FXML
    private TextField country_text;
    @FXML
    private Label id_label;

    @FXML
    private TextField id_text;
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
        DrinkID.setCellValueFactory(new PropertyValueFactory<Drinks, Integer>("drink_id"));
        DrinkName.setCellValueFactory(new PropertyValueFactory<Drinks, String>("drink_name"));
        countryName.setCellValueFactory(new PropertyValueFactory<Drinks, String>("country"));
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
                if( price_text.getText().equals("") || Integer.parseInt(price_text.getText()) > 0){

                    SQL_connection.editdrink(name_text.getText(), country_text.getText(), price_text.getText());
                    Drinks.d = 0;
                    hide();
                    observableList = FXCollections.observableArrayList();
                    for (Map.Entry d : Drinks.list.entrySet()) {
                        observableList.add((Drinks) d.getValue());
                    }
                    table.refresh();

                }
                else{
                    error_text.setText("Please enter a valid price number");
                }
            }
            else{
                error_text.setText("Please enter a valid price number");
            }
        }

        else if(Drinks.d == 3){
            if(!id_text.getText().equals("")&&!name_text.getText().equals("") && !country_text.getText().equals("") &&
                    !price_text.getText().equals("")){

                if(!Drinks.list.containsKey(id_text.getText())){
                    if(isNumeric((price_text.getText()))){
                        if(Double.parseDouble(price_text.getText()) > 0 ){
                            SQL_connection3.addDrink(id_text.getText(),name_text.getText(), country_text.getText(),
                                    price_text.getText());
                            Drinks.d=0;
                            hide();
                            observableList = FXCollections.observableArrayList();
                            for(Map.Entry d : Drinks.list.entrySet()){
                                observableList.add((Drinks) d.getValue());
                            }
                            table.setItems(observableList);
                        }
                        else error_text.setText("this Drink doesn't exist");
                    }
                    else
                        error_text.setText("Please enter a valid id number");
                }
                else{
                    error_text.setText("This Drink already existsmvx" +
                            "mvx");
                }

            }
            else{
                error_text.setText("Please fill all fields");
            }
        }
    }


    public void hide(){
        visible_label.setText("");
        id_label.setText("");
        name_label.setText("");
        country_label.setText("");
        price_label.setText("");
        error_text.setText("");
        id_text.setVisible(false);
        name_text.setVisible(false);
        country_text.setVisible(false);
        price_text.setVisible(false);
        ok.setVisible(false);
    }

    public void show(String title){
        System.out.println(title);
        visible_label.setText(title);
        id_label.setText("Drink_Id:");
        name_label.setText("Name:");
        country_label.setText("Country:");
        price_label.setText("Price:");
        id_text.setVisible(true);
        id_text.setText("");
        name_text.setVisible(true);
        name_text.setText("");
        country_text.setVisible(true);
        country_text.setText("");
        price_text.setVisible(true);
        price_text.setText("");
        add.setVisible(true);
        ok.setVisible(false);
    }

    public void show2(String title){
        System.out.println(title);
        visible_label.setText(title);
        id_label.setText("");
        name_label.setText("Name:");
        country_label.setText("Country:");
        price_label.setText("Price:");
        id_text.setVisible(false);
        name_text.setVisible(true);
        name_text.setText("");
        country_text.setVisible(true);
        country_text.setText("");
        price_text.setVisible(true);
        price_text.setText("");
        ok.setVisible(true);
        add.setVisible(false);
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
        add.setText("Add");
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
}
