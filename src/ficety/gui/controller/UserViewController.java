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
import ficety.be.Session;
import ficety.be.Task;
import ficety.dal.SessionDBDAO;
import ficety.dal.TaskDBDAO;
import ficety.gui.model.UserViewModel;
import java.net.URL;
import java.security.Timestamp;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javax.swing.JFrame;


/**
 *
 * @author Trigger
 */
public class UserViewController extends JFrame implements Initializable {
    
    private Label label;
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
    private TableColumn<?, ?> Col_pj_myhours;
    @FXML
    private TextField pj_search;
    @FXML
    private Tab tab_task;
    @FXML
    private TableView<Task> tbv_task;
    @FXML
    private TableColumn<Task,String> Col_task_taskname;
    @FXML
    private TableColumn<Task,Integer> Col_task_project;
    @FXML
    private TableColumn<Task, Integer> Col_task_myhours;
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
    private Label lb_stat_taskhours;
    @FXML
    private Label lb_stat_totalhours;
    @FXML
    private TextField stat_search;
    @FXML
    private Tab tab_sesion;
    @FXML
    private TableColumn<Session, Integer> col_sesion_taskname;
    @FXML
    private TableColumn<Session, LocalDateTime> col_sesion_start;
    @FXML
    private TableColumn<Session, LocalDateTime> col_sesion_stop;
    @FXML
    private TableColumn<Session, Integer> col_sesion_myhours;
    @FXML
    private TextField sesion_search;
    @FXML
    
    private ScrollPane Sp_last3;
    
    private UserViewModel UVM;
    private LoggedInUser lu;
    int MaxWidth;
    boolean min;
    boolean isTimerRunning = false;
    @FXML
    private TableColumn<Task,String> Col_task_description;
    @FXML
    private TableView<Session> tbv_session;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UVM = new UserViewModel();
        lu = lu.getInstance();
    }    

    public UserViewController() {
        MaxWidth = 260;
        min = true;
    }
    
    public void sizeExpantion(){
        
        
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
    public void toggelSize(){
        
        if(min == false){    
                Sp_last3.setVisible(true);
                min = true;
           
                System.out.println("true");
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
            
                System.out.println("false");
                Stage stage = (Stage) Sp_last3.getScene().getWindow();
                stage.setMaxHeight(248);
                stage.setMaxWidth(255);
                stage.setMinHeight(248);
                stage.setMinWidth(255);
        }
    }
 
    @FXML
    private void handle_view(ActionEvent event) {
        sizeExpantion();
    }

    @FXML
    private void toggel_size(ActionEvent event) {
        toggelSize();
    }

    @FXML
    private void handle_startStop(ActionEvent event) {
        int userID = 1;
        Task currentTask = new Task(3, "a", "do smth", 4,8);
        lu.setId(userID);
        lu.setCurrentTask(currentTask);
        UVM.startStopSession();
        if(isTimerRunning){
            timer.stop();
        isTimerRunning = false;}
        else{
        timer.start();
        isTimerRunning = true;}
    }
    
AnimationTimer timer = new AnimationTimer() {
    private long timestamp;
    private long time = 0;
    private long fraction = 0;

    @Override
    public void start() {
        // current time adjusted by remaining time from last run
        timestamp = System.currentTimeMillis() - fraction;
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        // save leftover time not handled with the last update
        fraction = System.currentTimeMillis() - timestamp;
    }

    @Override
    public void handle(long now) {
        long newTime = System.currentTimeMillis();
        if (timestamp + 1000 <= newTime) {
            long deltaT = (newTime - timestamp) / 1000;
            time += deltaT;
            timestamp += 1000 * deltaT;
           String timee = String.format("%02d:%02d:%02d",time/3600 ,time / 60, time % 60);
            lb_tasktime.setText((timee));
        }
    }
    
};
private TaskDBDAO tbd = new TaskDBDAO();
    @FXML
    private void load_task_tab(Event event) throws SQLException {
        Task t = new Task(5,"lol","ok",8,0);
        ObservableList<Task> data =  FXCollections.observableArrayList(tbd.getTasksInfo(1));
      //  data.add(t);
        Col_task_taskname.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
        Col_task_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        Col_task_project.setCellValueFactory(new PropertyValueFactory<Task, Integer>("associatedProjectID"));
        Col_task_myhours.setCellValueFactory(new PropertyValueFactory<Task, Integer>("hours"));
        tbv_task.setItems(data);
        System.out.println(data.size());
    }
private SessionDBDAO sbd = new SessionDBDAO();
private LoggedInUser liu;
    @FXML
    private void load_session_tab(Event event) throws SQLException {
        liu = LoggedInUser.getInstance();
     //  Timestamp n
     //   Session s = new Session(3,3,3,now,now,0,"");
        ObservableList<Session> data =  FXCollections.observableArrayList(sbd.getAllSessionsOfATask(1));
        col_sesion_taskname.setCellValueFactory(new PropertyValueFactory<Session,Integer>("taskName"));
        col_sesion_start.setCellValueFactory(new PropertyValueFactory<Session,LocalDateTime>("startTime"));
        col_sesion_stop.setCellValueFactory(new PropertyValueFactory<Session,LocalDateTime>("finishTime"));
        col_sesion_myhours.setCellValueFactory(new PropertyValueFactory<Session,Integer>("hours"));
        tbv_session.setItems(data);
    }
    
}
