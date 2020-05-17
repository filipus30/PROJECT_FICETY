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
import ficety.be.Client;
import ficety.be.LoggedInUser;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.bll.Exporter;
import ficety.gui.model.UserViewModel;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javax.swing.JFrame;


/**
 *
 * @author Trigger
 */
public class UserViewController extends JFrame implements Initializable {
    
    private boolean debug = true;
    private Label label;
    @FXML

    private TextField tf_newtask;
    @FXML
    private ComboBox<Project> cb_project;
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
    private Button bn_expandview;
    @FXML
    private FlowPane fp_last3task;
    @FXML
    private Tab tab_pj;
    @FXML
    private TableView<Project> Tbv_pj;
    @FXML
    private TableColumn<Project, String> Col_pj_name;
    @FXML
    private TableColumn<Project, String> Col_pj_clint;
    @FXML
    private TableColumn<Project, String> Col_pj_contact;
    @FXML
    private TableColumn<Project, Integer> Col_pj_myhours;
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
    private JFXComboBox<Project> cb_task_project;
    @FXML
    private JFXButton bn_task_add;
    @FXML
    private JFXButton bn_task_eddit;
    @FXML
    private JFXButton bn_task_delete;
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
    private Tab tab_sesion;
    @FXML
    private TableColumn<Session, Integer> col_sesion_taskname;
    @FXML
    private TableColumn<Session, LocalDateTime> col_sesion_start;
    @FXML
    private TableColumn<Session, LocalDateTime> col_sesion_stop;
    @FXML
    private TableColumn<Session, Integer> col_sesion_myhours;
    private ScrollPane Sp_last3;
    
    private UserViewModel UVM;
    private LoggedInUser lu;
    int MaxWidth;
    boolean min;
    boolean isTimerRunning = false;
    boolean loaded = false;
    private long time = 0;
    private int export = 3;
    boolean added = true;
     private ObservableList<Task> dataTasks;
    private ObservableList<Client> dataClient;
    private List<Task> tasklist;
    private ArrayList<Client> clientlist;
    private List<User> userlist ;
    private ObservableList<User> dataUsers ;
    private ObservableList<Session> datasession;
    @FXML
    private TableColumn col_task_bill;
    
   
        
    
    @FXML
    private TableColumn<Task,String> Col_task_description;
    @FXML
    private TableView<Session> tbv_session;
    @FXML
    private AnchorPane ap;
    @FXML
    private JFXButton txtpj1;
    @FXML
    private ImageView img1;
    @FXML
    private JFXComboBox<Task> jcb1;
    @FXML
    private JFXButton txtpj2;
    @FXML
    private ImageView img2;
    @FXML
    private JFXComboBox<Task> jcb2;
    @FXML
    private JFXButton txtpj3;
    @FXML
    private ImageView img3;
    @FXML
    private JFXComboBox<Task> jcb3;
    @FXML
    private JFXTextField task_description;
    private ObservableList<Project> datax;
    @FXML
    private JFXTextField session_start;
    @FXML
    private JFXTextField session_stop;
    @FXML
    private Text label_task;
    @FXML
    private Text label_today;
    @FXML
    private Button bn_exp;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UVM = new UserViewModel();
        lu = lu.getInstance();
         MaxWidth = 260;
        min = true;
     tasklist = UVM.getAllTasksForAdmin();
     dataTasks =  FXCollections.observableArrayList(tasklist);
      clientlist = UVM.getAllClients();
      dataClient =  FXCollections.observableArrayList(clientlist);
      userlist = UVM.getAllUsers();
      dataUsers =  FXCollections.observableArrayList(userlist);
      datasession =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
        
       datax = FXCollections.observableArrayList(UVM.getAllProjects());
       cb_project.getItems().addAll(datax);
       cb_task_project.getItems().addAll(datax);
       ObservableList<Project> last3data = FXCollections.observableArrayList(UVM.get3RecentProjects());
       loadAll();
       if(last3data.size() >= 1)
       { 
       ObservableList<Task> tasklist1 = FXCollections.observableArrayList(last3data.get(0).getTaskList());
              jcb1.getItems().addAll(tasklist1);
       txtpj1.setText(last3data.get(0).getProjectName());
       txtpj2.setVisible(false);
       txtpj3.setVisible(false);
       jcb2.setVisible(false);
       jcb3.setVisible(false);}
       if (last3data.size() >= 2)
       { 
       txtpj2.setVisible(true);
       jcb2.setVisible(true);
       ObservableList<Task> tasklist2 = FXCollections.observableArrayList(last3data.get(1).getTaskList());
       jcb2.getItems().addAll(tasklist2);
       txtpj2.setText(last3data.get(1).getProjectName());
       txtpj3.setVisible(false);
       jcb3.setVisible(false);}
       if (last3data.size() == 3)
       {
       txtpj3.setVisible(true);
       jcb3.setVisible(true);
       ObservableList<Task> tasklist3 = FXCollections.observableArrayList(last3data.get(2).getTaskList());
       jcb3.getItems().addAll(tasklist3);
       txtpj3.setText(last3data.get(2).getProjectName());}
        
        
//         List<Project> list = UVM.getAllProjectsForUserTab();
//         ObservableList<Project> datapj =  FXCollections.observableArrayList(list);
//         Col_pj_clint.setCellValueFactory(new PropertyValueFactory<Project,String>("clientName"));
//         Col_pj_contact.setCellValueFactory(new PropertyValueFactory<Project,String>("phoneNr"));
//         Col_pj_myhours.setCellValueFactory(new PropertyValueFactory<Project,Integer>("seconds"));
//         Col_pj_name.setCellValueFactory(new PropertyValueFactory<Project,String>("projectName"));
//         Tbv_pj.setItems(datapj);
         loaded = true;
        
    }    

   
   public void sizeExpantion(){
        
        
            if(min == false){
                Stage stage = (Stage) bn_expandview.getScene().getWindow();
                stage.setMaxHeight(550); //prev 500
                stage.setMaxWidth(800);
                stage.setMinHeight(480);
                stage.setMinWidth(800);
                MaxWidth = 1044;
             //   Sp_last3.setVisible(true);
              ap.setVisible(true);
                min = true;
            }
            else{
                Stage stage = (Stage) bn_expandview.getScene().getWindow();
                stage.setMaxHeight(550); //prev 500
                stage.setMaxWidth(260); //prev 240
                stage.setMinHeight(480);
                stage.setMinWidth(255);
                MaxWidth = 260;
              //  Sp_last3.setVisible(true
               ap.setVisible(true);
                min = false;
            }
        }
       
    
    public void toggelSize(){
        
        if(min == false){    
              //  Sp_last3.setVisible(true);
                min = true;
                 ap.setVisible(true);
                debug("Toggle size true");
                Stage stage = (Stage) ap.getScene().getWindow();
                stage.setMaxHeight(550); //prev 470
                stage.setMaxWidth(800);
                stage.setMinHeight(490); //prev 470
                stage.setMinWidth(800);
                tb_toggle.setLayoutY(409);
                bn_start_stop.setLayoutY(343);
                lb_tasktime.setLayoutY(342);
                lb_timetoday.setLayoutY(376);
                label_task.setLayoutY(356);
                label_today.setLayoutY(383);
                
            }
        else{
               // Sp_last3.setVisible(false);
                ap.setVisible(false);
               //user_tabpane.setVisible(false);
                 min = false;
            
                debug("Toggle size false");
                Stage stage = (Stage) ap.getScene().getWindow();
                stage.setMaxHeight(230); //prev 208
                stage.setMaxWidth(260); //prev 240
                stage.setMinHeight(230);
                stage.setMinWidth(260);
                 tb_toggle.setLayoutY(100); //-20 px all the way
                 bn_start_stop.setLayoutY(120);
                 lb_tasktime.setLayoutY(130);
                lb_timetoday.setLayoutY(150);
                label_task.setLayoutY(144);
                label_today.setLayoutY(160);
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
    private void addTaskAndSetItRunning(ActionEvent event) {
        Project associatedProject = cb_project.getSelectionModel().getSelectedItem();
        String taskName = tf_newtask.getText();
        boolean taskBillable = true;
        Pair<Task, Session> temp = UVM.addNewTaskAndSetItRunning(taskName, taskBillable, associatedProject);
        if(isTimerRunning)
        {
            timer.stop();
            time = 0;
            isTimerRunning = false;
        }
        timer.start();
        isTimerRunning = true;
       
    }

    @FXML
    private void handle_startStop(ActionEvent event) {

         if(added)
        {
        datasession.add(UVM.startStopSession());
        added = false;
        }
        else{
        UVM.startStopSession();
        added = true;
        }

        if(isTimerRunning){
            timer.stop();
        isTimerRunning = false;}
        else{
        timer.start();
        isTimerRunning = true;}

    }
    
AnimationTimer timer = new AnimationTimer() {
    private long timestamp;
    private long timefortotal;
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
            timefortotal += deltaT;
            timestamp += 1000 * deltaT;
           String timee = String.format("%02d:%02d:%02d",time/3600 ,time / 60, time % 60);
           String timetotal = String.format("%02d:%02d:%02d",timefortotal/3600 ,timefortotal / 60, timefortotal % 60);
            lb_tasktime.setText(timee);
            lb_timetoday.setText(timetotal);
        }
    }
    
};

    @FXML
    private void load_task_tab(Event event) throws SQLException {
//        ObservableList<Task> data =  FXCollections.observableArrayList(UVM.getTasksForUserInfo());
//        Col_task_taskname.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
//        Col_task_description.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
//        Col_task_project.setCellValueFactory(new PropertyValueFactory<Task, Integer>("associatedProjectID"));
//        Col_task_myhours.setCellValueFactory(new PropertyValueFactory<Task, Integer>("hours"));
//        tbv_task.setItems(data);
//        debug(data.size() + "");
export = 1;
    }

    @FXML
    private void load_session_tab(Event event) throws SQLException {
       
     //  Timestamp n
     //   Session s = new Session(3,3,3,now,now,0,"");
//        ObservableList<Session> data =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
//        col_sesion_taskname.setCellValueFactory(new PropertyValueFactory<Session,Integer>("taskName"));
//        col_sesion_start.setCellValueFactory(new PropertyValueFactory<Session,LocalDateTime>("startTime"));
//        col_sesion_stop.setCellValueFactory(new PropertyValueFactory<Session,LocalDateTime>("finishTime"));
//        col_sesion_myhours.setCellValueFactory(new PropertyValueFactory<Session,Integer>("hours"));
//        tbv_session.setItems(data);
//        cb_project.getSelectionModel().getSelectedItem();
//      //  cb_project.getItems().addAll(c);
       export = 2;
    }

    @FXML
    private void load_pj_tab(Event event) throws InterruptedException {
        
//        {   List<Project> list = UVM.getAllProjectsForUserTab();
//         ObservableList<Project> data =  FXCollections.observableArrayList(list);
//         Col_pj_clint.setCellValueFactory(new PropertyValueFactory<Project,String>("clientName"));
//         Col_pj_contact.setCellValueFactory(new PropertyValueFactory<Project,String>("phoneNr"));
//         Col_pj_myhours.setCellValueFactory(new PropertyValueFactory<Project,Integer>("seconds"));
//         Col_pj_name.setCellValueFactory(new PropertyValueFactory<Project,String>("projectName"));
//         Tbv_pj.setItems(data);}
        
            export = 3;
        
    }
    
    @FXML //On the task selection for both the Task-tab as well as the Tasks INSIDE 3 most recent projects.
    private void chooseSelectedTask(Event event)
    {
        if(event.getSource().equals(jcb1)) //From First recent Project
        {
            if(jcb1.getValue() != null)
            {
                debug("Value 1 box selected"); //DEBUG MESSAGE
                Task tmp = jcb1.getValue();
                lu.setCurrentTask(tmp);
                time = 0;
            }
        }
        
        else if(event.getSource().equals(jcb2)) //From Second recent Project
        {
            if(jcb2.getValue() != null)
            {
                debug("Value 2 box selected"); //DEBUG MESSAGE
                Task tmp = jcb2.getValue();
                lu.setCurrentTask(tmp);
            }
        }
        
        else if(event.getSource().equals(jcb3)) //From Third recent Project
        {
            if(jcb3.getValue() != null)
            {
                debug("Value 3 box selected");//DEBUG MESSAGE
                Task tmp = jcb3.getValue();
                lu.setCurrentTask(tmp);
            }
        }
        else if(event.getSource().equals(tbv_task))//From Task table view.
        {
            debug("TableView selected.");//DEBUG MESSAGE
            Task tmp = tbv_task.getSelectionModel().getSelectedItem();
            lu.setCurrentTask(tmp);
            task_name.setText(lu.getCurrentTask().getTaskName());
            task_description.setText(lu.getCurrentTask().getDesc());
            for(int i = 0;i<datax.size();i++)
            {
               if(datax.get(i).getId() == lu.getCurrentTask().getAssociatedProjectID())
                   cb_task_project.getSelectionModel().select(datax.get(i));
            }
            
            
        }
    }
    private void loadAll()
    {  tbv_task.setEditable(true);
	// allows the individual cells to be selected
	tbv_task.getSelectionModel().cellSelectionEnabledProperty().set(true);
         dataTasks =  FXCollections.observableArrayList(UVM.getTasksForUserInfo());
        Col_task_taskname.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
        Col_task_taskname.setCellFactory(TextFieldTableCell.forTableColumn());
        Col_task_taskname.setOnEditCommit(
                (TableColumn.CellEditEvent<Task, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTaskName(t.getNewValue())
                );
        Col_task_description.setCellValueFactory(new PropertyValueFactory<Task, String>("desc"));
        Col_task_project.setCellValueFactory(new PropertyValueFactory<Task, Integer>("associatedProjectName"));
        Col_task_myhours.setCellValueFactory(new PropertyValueFactory<Task, Integer>("hours"));
        col_task_bill.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Task, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Task, CheckBox> arg0) {
                Task user = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(user.getBillable());



                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {

                        user.setBillable(new_val);

                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
        tbv_task.setItems(dataTasks);
        datasession =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
        col_sesion_taskname.setCellValueFactory(new PropertyValueFactory<Session,Integer>("taskName"));
        col_sesion_start.setCellValueFactory(new PropertyValueFactory<Session,LocalDateTime>("startTime"));
        col_sesion_stop.setCellValueFactory(new PropertyValueFactory<Session,LocalDateTime>("finishTime"));
        col_sesion_myhours.setCellValueFactory(new PropertyValueFactory<Session,Integer>("hours"));
        tbv_session.setItems(datasession);
        cb_project.getSelectionModel().getSelectedItem();
        List<Project> list = UVM.getAllProjectsForUserTab();
        ObservableList<Project> datapj =  FXCollections.observableArrayList(list);
        Col_pj_clint.setCellValueFactory(new PropertyValueFactory<Project,String>("clientName"));
        Col_pj_contact.setCellValueFactory(new PropertyValueFactory<Project,String>("phoneNr"));
        Col_pj_myhours.setCellValueFactory(new PropertyValueFactory<Project,Integer>("seconds"));
        Col_pj_name.setCellValueFactory(new PropertyValueFactory<Project,String>("projectName"));
        Tbv_pj.setItems(datapj);
        lb_loginuser.setText(lu.getName());
        
    }
     @FXML
    private void chooseSession(Event event)
    {
        if(event.getSource().equals(tbv_session))
        { Session ses = tbv_session.getSelectionModel().getSelectedItem();
           session_start.setText(ses.getStartTime());
           session_stop.setText(ses.getFinishTime());
        }
        
    }
    

    @FXML
    private void edit_session(ActionEvent event) {
        UVM.editSession(tbv_session.getSelectionModel().getSelectedItem(),session_start.getText(),session_stop.getText(),tbv_session.getSelectionModel().getSelectedItem().getSessionID());
    }

    @FXML
    private void delete_session(ActionEvent event) {
        UVM.removeSessionFromDB(tbv_session.getSelectionModel().getSelectedItem());
    }
    
    private void debug (String msg)
    {
        if(debug == true)
        {
            System.out.println(msg);
        }
    }

    @FXML
    private void editTask(ActionEvent event) {
        if(task_name.getText() != null)
        {
            if(cb_task_project != null)
            {
                 debug("Editing task, passing down stack");
                UVM.editTask(lu.getCurrentTask(), task_name.getText(), task_description.getText() , cb_task_project.getSelectionModel().getSelectedItem().getId() );
                lu.setCurrentTask(null);
            }
            else 
            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Please select project for task you want to edit");
      alert.showAndWait();}
            
        }
        else
        {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Please enter name for task");
      alert.showAndWait();}
        }
    

    @FXML
    private void addTask(ActionEvent event)
    {
        if(task_name.getText() != null)
        {
            if(cb_task_project != null)
            {
                debug("Adding task to DB passing down stack");

                boolean taskBillable = true; //NEEDS FIXING!
                UVM.addNewTaskToDB(task_name.getText(), task_description.getText(), taskBillable, cb_task_project.getSelectionModel().getSelectedItem());
                lu.setCurrentTask(null);
                Session temp = UVM.startStopSession();
            }
            else 
            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Please select project for task you want to edit");
      alert.showAndWait();
            }
        }
        else
        {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Please enter name for task");
      alert.showAndWait();
        }
    }
    
    @FXML
    private void deleteTask(ActionEvent event) {
        if(task_name.getText() != null)
        {
            if(cb_task_project != null)
            {
                debug("Removing task from DB passing down stack");
                UVM.removeTaskFromDB(lu.getCurrentTask());
                lu.setCurrentTask(null);
            }
            
        }
       
    }

    @FXML
    private void export_table(ActionEvent event) {
        if(export ==3 )
        UVM.export(Tbv_pj,search.getText());
        else if(export ==2)
        UVM.export(tbv_session,search.getText());
        else if(export == 1)
        UVM.export(tbv_task,search.getText());
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("File exported succesfully ! You can find it in your project folder");
      alert.showAndWait();
    }

    
}
