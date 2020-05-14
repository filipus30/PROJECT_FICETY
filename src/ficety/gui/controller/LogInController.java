/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.controller;

import com.jfoenix.controls.JFXButton;
import ficety.bll.BllManager;
import ficety.dal.DalManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Trigger
 */
public class LogInController implements Initializable {

    private JFXButton bn_admin;
    @FXML
    private JFXButton bn_user;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_password;
    private BllManager bm;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       bm = new BllManager();
      
    }    

  //  private void handle_adminlogin(ActionEvent event) throws IOException {
   //     adminlogin();
   // }
    

    @FXML
    private void handle_userlogin(ActionEvent event) throws IOException {
        String email = tf_email.getText();
        String password = tf_password.getText();
        bm = new BllManager();
        int number = 10;
        if(tf_email.getText().isEmpty() == false || tf_password.getText().isEmpty() == false)
         number =  bm.checkUserLogin(email,password);
     if(number == 1)
         adminlogin();
     else if (number == 2)
         userlogin();
      else
     { Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Wrong login details");
      alert.showAndWait();}
    }

    private void adminlogin() throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ficety/gui/view/AdminView.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<AdminViewController>getController();
        Stage addStage = new Stage();
        Scene addScene = new Scene(root1);
        addStage.setScene(addScene);
        addStage.show();

        Stage stage = (Stage) bn_user.getScene().getWindow();
        stage.close();
    }

    private void userlogin() throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ficety/gui/view/UserView.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<UserViewController>getController();
        Stage addStage = new Stage();
        Scene addScene = new Scene(root1);
        addStage.setScene(addScene);
        addStage.show();

        Stage stage = (Stage) bn_user.getScene().getWindow();
        stage.close();   
    }
    
}
