/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import ficety.be.LoggedInUser;
import ficety.be.Task;
import ficety.gui.model.UserViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Trigger
 */
public class AdminViewController implements Initializable {

    private boolean debug = false;
    @FXML
    private TextField tf_newtask;
    @FXML
    private ComboBox<?> cb_project;
    @FXML
    private Button bn_add;
    @FXML
    private TextField search;
    @FXML
    private ToggleButton tb_toggle;
    @FXML
    private Button bn_start_stop;
    @FXML
    private Label lb_tasktime;
    @FXML
    private Label lb_timetoday;
    @FXML
    private Label lb_loginuser;
    @FXML
    private JFXButton bn_settings;
    @FXML
    private Button bn_expandview;
    @FXML
    private FlowPane fp_last3task;
    @FXML
    private Tab tab_pj;
    @FXML
    private TableView<?> Tbv_pj;
    @FXML
    private TableColumn<?, ?> Col_pj_name;
    @FXML
    private TableColumn<?, ?> Col_pj_clint;
    @FXML
    private TableColumn<?, ?> Col_pj_contact;
    @FXML
    private TableColumn<?, ?> Col_pj_nroftask;
    @FXML
    private TextField pj_search;
    @FXML
    private Tab tab_task;
    @FXML
    private TableView<?> tbv_task;
    @FXML
    private TableColumn<?, ?> Col_task_taskname;
    @FXML
    private TableColumn<?, ?> Col_task_project;
    @FXML
    private TableColumn<?, ?> Col_task_devs;
    @FXML
    private JFXTextField task_name;
    @FXML
    private JFXComboBox<?> cb_task_project;
    @FXML
    private JFXButton bn_task_add;
    @FXML
    private JFXButton bn_task_eddit;
    @FXML
    private JFXButton bn_task_delete;
    @FXML
    private JFXDatePicker dp_task_from;
    @FXML
    private JFXDatePicker dp_task_to;
    @FXML
    private TextField task_search;
    @FXML
    private Tab tab_stat;
    @FXML
    private BarChart<?, ?> stat_graf;
    @FXML
    private JFXComboBox<?> cb_stat_project;
    @FXML
    private JFXComboBox<?> cb_stat_task;
    @FXML
    private JFXDatePicker dp_stat_from;
    @FXML
    private JFXDatePicker dp_stat_to;
    @FXML
    private TextField stat_search;
    @FXML
    private Tab tab_sesion;
    @FXML
    private TableColumn<?, ?> col_sesion_taskname;
    @FXML
    private TableColumn<?, ?> col_sesion_date;
    @FXML
    private TableColumn<?, ?> col_sesion_start;
    @FXML
    private TableColumn<?, ?> col_sesion_stop;
    @FXML
    private TableColumn<?, ?> col_sesion_myhours;
    @FXML
    private TextField sesion_search;
    @FXML
    private Tab tab_clint;
    @FXML
    private TableView<?> Tbv_pj1;
    @FXML
    private TableColumn<?, ?> Col_clint_name;
    @FXML
    private TableColumn<?, ?> Col_clint_email;
    @FXML
    private TableColumn<?, ?> Col_clint_nrofpj;
    @FXML
    private TableColumn<?, ?> Col_clint_$perhour;
    @FXML
    private TableColumn<?, ?> Col_clint_totalhours;
    @FXML
    private TableColumn<?, ?> Col_clint_totalprice;
    @FXML
    private TextField clint_search;
    @FXML
    private JFXTextField tf_clint_name;
    @FXML
    private JFXButton bn_clint_add;
    @FXML
    private JFXButton bn_clint_eddit;
    @FXML
    private JFXButton bn_clint_delete;
    @FXML
    private JFXTextField tf_clint_email;
    @FXML
    private JFXTextField tf_clint_$perhour;
    @FXML
    private TableColumn<?, ?> Col_pj_totalhours;
    @FXML
    private TableColumn<?, ?> Col_pj_totalprice;
    @FXML
    private JFXTextField tf_pj_name;
    @FXML
    private JFXTextField tf_pj_nr;
    @FXML
    private JFXTextField tf_pj_$perhour;
    @FXML
    private JFXComboBox<?> cb_pj_clint;
    @FXML
    private JFXButton bn_pj_add;
    @FXML
    private JFXButton bn_pj_eddit;
    @FXML
    private JFXButton bn_pj_delete;
    @FXML
    private JFXDatePicker dp_pj_from;
    @FXML
    private JFXDatePicker dp_pj_to;
    @FXML
    private TableColumn<?, ?> Col_task_$perhour;
    @FXML
    private TableColumn<?, ?> Col_task_totalhours;
    @FXML
    private TableColumn<?, ?> Col_task_totalprice;
    @FXML
    private JFXTextField task_$perhour;
    @FXML
    private Label lb_stat_priceperhour;
    @FXML
    private Label lb_stat_totalprice;
    @FXML
    private JFXComboBox<?> cb_stat_clint;
    @FXML
    private JFXComboBox<?> cb_stat_dev;
    @FXML
    private Tab tab_user;
    @FXML
    private TableColumn<?, ?> col_user_name;
    @FXML
    private TableColumn<?, ?> col_user_hoursthisweek;
    @FXML
    private TableColumn<?, ?> col_user_$perhour;
    @FXML
    private TableColumn<?, ?> col_user_admin;
    @FXML
    private TableColumn<?, ?> col_user_startdate;
    @FXML
    private TextField user_search;
    @FXML
    private JFXTextField tf_user_name;
    @FXML
    private JFXTextField tf_user_$perhour;
    @FXML
    private JFXComboBox<?> cb_user_admin;
    @FXML
    private JFXButton bn_user_add;
    @FXML
    private JFXButton bn_user_eddit;
    @FXML
    private JFXButton bn_user_delete;
    private ScrollPane Sp_last3;
    int MaxWidth;
    boolean min;
    LoggedInUser lu = LoggedInUser.getInstance();
    UserViewModel UVM = new UserViewModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //to do
    }    

    public AdminViewController() {
        MaxWidth = 260;
        min = true;
    }
    
    public void SS(){
        
        
        if(MaxWidth == 260){
        
        Stage stage = (Stage) bn_expandview.getScene().getWindow();
        stage.setMaxHeight(488);
        stage.setMaxWidth(1044);
        stage.setMinHeight(488);
        stage.setMinWidth(1044);
        MaxWidth = 1044;
        Sp_last3.setVisible(true);
            min = true;
        
        }
        else{
            if(min == false){
                Stage stage = (Stage) bn_expandview.getScene().getWindow();
                stage.setMaxHeight(488);
                stage.setMaxWidth(1044);
                stage.setMinHeight(488);
                stage.setMinWidth(1044);
                MaxWidth = 1044;
                Sp_last3.setVisible(true);
                min = true;
            }
            else{
                Stage stage = (Stage) bn_expandview.getScene().getWindow();
                stage.setMaxHeight(488);
                stage.setMaxWidth(260);
                stage.setMinHeight(488);
                stage.setMinWidth(260);
                MaxWidth = 260;
                Sp_last3.setVisible(true);
                min = true;
            }
        }
       
    }
    public void ToggelSize(){
        
        if(min == false){    
            Sp_last3.setVisible(true);
            min = true;
           
                debug("true");
                Stage stage = (Stage) Sp_last3.getScene().getWindow();
                stage.setMaxHeight(488);
                stage.setMaxWidth(260);
                stage.setMinHeight(488);
                stage.setMinWidth(260);
                MaxWidth = 260;
            }
        else{
            Sp_last3.setVisible(false);
            min = false;
            
            debug("false");
            Stage stage = (Stage) Sp_last3.getScene().getWindow();
            stage.setMaxHeight(248);
            stage.setMaxWidth(255);
            stage.setMinHeight(248);
            stage.setMinWidth(255);
        }
    }
 
    
    

    @FXML
    private void handle_view(ActionEvent event) {
        SS();
    }

    @FXML
    private void toggel_size(ActionEvent event) {
        ToggelSize();
    }
    
        @FXML
    private void handle_startStop(ActionEvent event) {
        Task currentTask = new Task(3, "a", "do smth", 4,"");
        lu.setCurrentTask(currentTask);
        UVM.startStopSession();
    }
        
   
    private void debug (String msg)
    {
        if(debug == true)
        {
            System.out.println(msg);
        }
    }
}
