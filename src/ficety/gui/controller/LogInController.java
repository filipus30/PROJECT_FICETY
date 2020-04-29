/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Trigger
 */
public class LogInController implements Initializable {

    @FXML
    private JFXButton bn_admin;
    @FXML
    private JFXButton bn_user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handle_adminlogin(ActionEvent event) throws IOException {
        adminlogin();
    }
    

    @FXML
    private void handle_userlogin(ActionEvent event) throws IOException {
        userlogin();
    }

    private void adminlogin() throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/view/AdminView.fxml"));
        root1 = (Parent) fxmlLoader.load();
        fxmlLoader.<AdminViewController>getController();
        Stage addStage = new Stage();
        Scene addScene = new Scene(root1);
        addStage.setScene(addScene);
        addStage.show();

        Stage stage = (Stage) bn_admin.getScene().getWindow();
        stage.close();
    }

    private void userlogin() throws IOException {
        Parent root1;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/view/UserView.fxml"));
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
