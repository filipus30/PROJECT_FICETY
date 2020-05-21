/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import ficety.be.LoggedInUser;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author macos
 */
public class DateSelectionController implements Initializable {

    @FXML
    private JFXDatePicker startDateSelector;
    @FXML
    private JFXDatePicker finishDateSelector;
    @FXML
    private JFXButton acceptBtn;

    private LoggedInUser lu;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lu = lu.getInstance();
        // TODO
    }    

    @FXML
    private void selectChosenDates(ActionEvent event) {
        String startDate = startDateSelector.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String finishDate = finishDateSelector.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        lu.setStartTime(startDate);
        lu.setFinishTime(finishDate);
        
        Stage stage = (Stage) acceptBtn.getScene().getWindow();
        stage.close();
    }
    
}
