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
import ficety.be.Coordinates;
import ficety.be.LoggedInUser;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.dal.ClientDBDAO;
import ficety.gui.model.UserViewModel;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JFrame;


/**
 *
 * @author Trigger
 */
public class AdminViewController extends JFrame implements Initializable {

    private boolean debug = false;
    private Label label;
    private UserViewModel UVM ;
    private ObservableList<Task> dataTasks;
    private ObservableList<Client> dataClient;
    private List<Task> tasklist;
    private ArrayList<Client> clientlist;
    private List<User> userlist ;
    private ObservableList<User> dataUsers ;
    private ObservableList<Session> datasession;
    private ObservableList<Project> choosedatauser;
    boolean added = true;
    boolean datepicked = false;
    boolean admdatepicked = false;
    
    boolean barUsrDataPicked = false;
    boolean barUsrTimePicked = false;
    
    boolean barAdmDataPicked = false;
    boolean barAdmTimePicked = false;
    
    @FXML
    private Button bn_exp;
    @FXML
    private TableColumn col_task_bill;
    @FXML
    private LineChart<String,Integer> stat_graph;
    @FXML
    private Tab tab_column;
    @FXML
    private StackedBarChart<String, Integer> stat_bar;
    @FXML
    private JFXComboBox<String> cb_stat_time;
    @FXML
    private LineChart<String,Integer> stat_adm_graph;
    @FXML
    private JFXComboBox<String> cb_stat_adm_time;
    @FXML
    private JFXComboBox<Client> cb_stat_adm_task;
    @FXML
    private StackedBarChart<String, Integer> adm_stack_bar;
    @FXML
    private JFXComboBox<String> cb_bar_adm_data;
    @FXML
    private JFXComboBox<String> cb_bar_adm_time;
    @FXML
    private Tab Col_chart_adm_tab;
    @FXML
    private AnchorPane col_tab_anchor;
    @FXML
    private AnchorPane scrollpane;
    @FXML
    private ScrollPane scroll;


    public AdminViewController()
    {
        MaxWidth = 260;
        min = true;
       UVM = new UserViewModel();
      tasklist = UVM.getAllTasksForAdmin();
      dataTasks =  FXCollections.observableArrayList(tasklist);
      clientlist = UVM.getAllClients();
      dataClient =  FXCollections.observableArrayList(clientlist);
      userlist = UVM.getAllUsers();
      dataUsers =  FXCollections.observableArrayList(userlist);
      datasession =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
      choosedatauser =  FXCollections.observableArrayList(UVM.getAllProjects());
    }
    @FXML
    private TextField tf_newtask;
    @FXML
    private ComboBox<Project> cb_project;
    @FXML
    private Button bn_add;
    @FXML
    private TextField search;
    @FXML
    private JFXButton tb_toggle;
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
    private JFXTextField task_name;
    private JFXComboBox<Project> cb_task_project;
    @FXML
    private Tab tab_stat;
    @FXML
    private JFXComboBox<Project> cb_stat_task;
    @FXML
    private Label lb_stat_taskhours;
    @FXML
    private Label lb_stat_totalhours;
    @FXML
    private Tab tab_sesion;
    @FXML
    private TableColumn<Session, Integer> col_sesion_taskname;
    @FXML
    private TableColumn<Session, String> col_sesion_start;
    @FXML
    private TableColumn<Session, String> col_sesion_stop;
    @FXML
    private TableColumn<Session, Integer> col_sesion_myhours;
    private ScrollPane Sp_last3;

   // private UserViewModel UVM = new UserViewModel();
    private LoggedInUser lu = LoggedInUser.getInstance();
    int MaxWidth;
    boolean min;
    boolean isTimerRunning = false;
    boolean loaded = false;
    private long time = 0;
    private boolean admpanel = false;
    private int export = 3;
    @FXML
    private TableColumn<Task,String> Col_task_description;
    @FXML
    private TableView<Session> tbv_session;
    @FXML
    private AnchorPane ap;
    private JFXButton txtpj1;
    private ImageView img1;
    private JFXComboBox<Task> jcb1;
    private JFXButton txtpj2;
    private ImageView img2;
    private JFXComboBox<Task> jcb2;
    private JFXButton txtpj3;
    private ImageView img3;
    private JFXComboBox<Task> jcb3;
    private JFXTextField task_description;
    private ObservableList<Project> datax;
    private JFXTextField session_start;
    private JFXTextField session_stop;
    @FXML
    private TableView<Client> admin_clients;
    @FXML
    private TableColumn<Client, String> col_client_name;
    @FXML
    private TableColumn<Client, String> col_client_email;
    @FXML
    private TableColumn<Client, Integer> col_client_projectNr;
    @FXML
    private TableColumn<Client, Float> col_client_standardRate;
    private boolean loadCli = false;
    @FXML
    private TableView<Project> admin_projects;
    @FXML
    private TableColumn<Project, String> col_project_name;
    @FXML
    private TableColumn<Project, String> col_project_client;
    @FXML
    private TableColumn<Project, String> col_project_contact;
    @FXML
    private TableColumn<Project, String> col_project_time;
    @FXML
    private TableColumn<Project, String> col_project_payment;
    private boolean loadProject = false;


    @FXML
    private TableView<Task> admin_tasks;
    @FXML
    private TableColumn<Task, String> col_task_name;
    @FXML
    private TableColumn<Task, String> col_task_project;
    @FXML
    private TableColumn<Task, String> col_task_user;
    @FXML
    private TableColumn<Task, Float> col_task_userRate;
    @FXML
    private TableColumn<Task, String> col_task_time;
    @FXML
    private TableColumn<Task, String> col_task_projectRate;

    private boolean loadAdminTasks = false;

    @FXML
    private TableView<User> admin_users;

    @FXML
    private TableColumn<User, String> col_user_name;
    @FXML
    private TableColumn<User, String> col_user_time;
    @FXML
    private TableColumn<User, Float> col_user_salary;
    @FXML
    private TableColumn col_user_admin;

    private boolean loadUsers = false;
    @FXML
    private TabPane admin_tab;
    @FXML
    private TabPane user_tabpane;
    @FXML
    private Text label_task;
    @FXML
    private Text label_today;
    @FXML
    private TableColumn<Project, Integer> col_project_allocatedhours;
    @FXML
    private TableColumn<User, String> col_user_email;
    @FXML
    private TextField tf_adm_client_standardRate;
    @FXML
    private TextField tf_adm_client_email;
    @FXML
    private TextField tf_adm_client_name;
    @FXML
    private TextField tf_adm_project_hours;
    @FXML
    private TextField tf_adm_project_contact;
    @FXML
    private TextField tf_adm_project_name;
    @FXML
    private JFXComboBox<Client> cb_adm_project_client;
    @FXML
    private TextField tf_adm_project_payment;
    @FXML
    private TextField tf_adm_task_name;
    @FXML
    private JFXComboBox<Project> cb_adm_task_project;
    @FXML
    private JFXComboBox<User> cb_adm_task_user;
    @FXML
    private TextField tf_adm_task_rate;
    @FXML
    private TextField tf_adm_task_task_payment;
    @FXML
    private TextField tf_adm_user_email;
    @FXML
    private TextField tf_adm_user_payperh;
    @FXML
    private TextField tf_adm_user_name;
    @FXML
    private JFXComboBox<Boolean> cb_adm_user_admin;
    @FXML
    private TableColumn<Task, String> col_task_description;
    @FXML
    private TextField tf_adm_task_task_description;
    @FXML
    private TableColumn<User, String> col_user_password;
    @FXML
    private TextField tf_adm_user_password;

    @FXML
    private JFXComboBox<Project> cb_bar_usr_data;
    
    @FXML
    private JFXComboBox<String> cb_bar_usr_time;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
       admin_tab.setVisible(false);
       datax = FXCollections.observableArrayList(UVM.get3RecentProjects());
       cb_project.getItems().addAll(datax);
//       cb_task_project.getItems().addAll(datax);
       
   loadButtons();
         loaded = true;

    }
    public void loadButtons()
    {
        ObservableList<Project> last3data = FXCollections.observableArrayList(UVM.get3RecentProjects());
       loadAll();
      
              for(int i =0;i<last3data.size();i++)
              {
                  ObservableList<Task> tasklist1 = FXCollections.observableArrayList(last3data.get(i).getTaskList());
                  ComboBox<Task> c = new ComboBox();
                  c.getItems().addAll(tasklist1);
                  c.setLayoutY(30);
                  c.setMinWidth(237);
                  c.setMaxWidth(237);
                  c.setPromptText("Select Task");
                  c.setOnAction((e) -> {
                   Task tmp = c.getSelectionModel().getSelectedItem();
               //    lu.setCurrentTask(tmp);
                      System.out.println(tmp.getTaskName());
        });
                  Pane p = new Pane();
                  Label l = new Label();
                  l.setText(last3data.get(i).getProjectName());
                  l.setTextFill(Color.WHITE);
                  l.setFont(new Font("Arial", 24));
                  l.setMinWidth(220);
                 p.getChildren().addAll(l,c);
                 p.setLayoutY(i*58);
                 p.setStyle("-fx-background-color:#256FA8;-fx-border-color: WHITE;");
                  scrollpane.getChildren().add(p);
                  scrollpane.setStyle("-fx-background-color:#5cb4fd");
              }
    }


    public void sizeExpantion(){


            if(min == false){
                Stage stage = (Stage) bn_expandview.getScene().getWindow();
                stage.setMaxHeight(550); //prev 500
                stage.setMaxWidth(800);
                stage.setMinHeight(480); //prev 500
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
                debug("Size toggle true");
                Stage stage = (Stage) ap.getScene().getWindow();
                stage.setMaxHeight(550); //prev 500
                stage.setMaxWidth(800);
                stage.setMinHeight(530); //prev 500
                stage.setMinWidth(800);
                tb_toggle.setLayoutY(409);
                bn_start_stop.setLayoutY(343);
                lb_tasktime.setLayoutY(342);
                lb_timetoday.setLayoutY(376);
                label_task.setLayoutY(356);
                label_today.setLayoutY(383);
                scroll.setVisible(true);

            }
        else{
               // Sp_last3.setVisible(false);
                ap.setVisible(false);
               //user_tabpane.setVisible(false);
                 min = false;

                debug("Size toggle false");
                Stage stage = (Stage) ap.getScene().getWindow();
                stage.setMaxHeight(180); //prev 208
                stage.setMaxWidth(245); //prev 340
                stage.setMinHeight(180);
                stage.setMinWidth(245);
                 tb_toggle.setLayoutY(80); // - 20 px all the way down
                 bn_start_stop.setLayoutY(105);
                 lb_tasktime.setLayoutY(110);
                lb_timetoday.setLayoutY(130);
                label_task.setLayoutY(129);
                label_today.setLayoutY(145);
               
               scroll.setVisible(false);
                
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
        UVM.addNewTaskAndSetItRunning(taskName, taskBillable, associatedProject);
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
            //task_name.setText(lu.getCurrentTask().getTaskName());
            task_description.setText(lu.getCurrentTask().getDesc());
            for(int i = 0;i<datax.size();i++)
            {
               if(datax.get(i).getId() == lu.getCurrentTask().getAssociatedProjectID())
                   cb_task_project.getSelectionModel().select(datax.get(i));
            }


        }
    }
    
     
    private void loadAll()
    {   tbv_task.setEditable(true);
	// allows the individual cells to be selected
	tbv_task.getSelectionModel().cellSelectionEnabledProperty().set(true);
         ObservableList<Task> datatask =  FXCollections.observableArrayList(UVM.getTasksForUserInfo());
         Col_task_taskname.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
         Col_task_taskname.setCellFactory(TextFieldTableCell.forTableColumn());
         Col_task_taskname.setOnEditCommit(
                (TableColumn.CellEditEvent<Task, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTaskName(t.getNewValue())
                );
        Col_task_description.setCellValueFactory(new PropertyValueFactory<Task, String>("desc"));
        Col_task_description.setCellFactory(TextFieldTableCell.forTableColumn());
        col_task_description.setOnEditCommit(
                (TableColumn.CellEditEvent<Task, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDesc(t.getNewValue())
                );
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
        tbv_task.setItems(datatask);
        //datasession =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
        col_sesion_taskname.setCellValueFactory(new PropertyValueFactory<Session,Integer>("taskName"));
        col_sesion_start.setCellValueFactory(new PropertyValueFactory<Session,String>("startTime"));
        col_sesion_start.setCellFactory(TextFieldTableCell.forTableColumn());
        col_sesion_start.setOnEditCommit(
                (TableColumn.CellEditEvent<Session, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setStartTime(t.getNewValue())
                );
        col_sesion_stop.setCellValueFactory(new PropertyValueFactory<Session,String>("finishTime"));
        col_sesion_stop.setCellFactory(TextFieldTableCell.forTableColumn());
        col_sesion_stop.setOnEditCommit(
                (TableColumn.CellEditEvent<Session, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setFinishTime(t.getNewValue())
                );
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


    private void edit_session(ActionEvent event) {
        UVM.editSession(tbv_session.getSelectionModel().getSelectedItem(),session_start.getText(),session_stop.getText(),tbv_session.getSelectionModel().getSelectedItem().getSessionID());
    }

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


    private void addTask(ActionEvent event)
    {
        if(task_name.getText() != null)
        {
            if(cb_task_project != null)
            {
                debug("Adding task to DB passing down stack");
                boolean taskBillable = true; //NEEDS EDITING IMPORTANT
                debug("TaskBillable hardcoded to true.");
                UVM.addNewTaskToDB(task_name.getText(), task_description.getText(), taskBillable, cb_task_project.getSelectionModel().getSelectedItem());
                lu.setCurrentTask(null);
                UVM.startStopSession();
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
    private void show_admin(ActionEvent event) {
        if(admpanel == false)
        { Stage stage = (Stage) bn_expandview.getScene().getWindow();
        stage.setMaxHeight(830);
        stage.setMinHeight(830); //prev 900
        admin_tab.setVisible(true);
        admpanel = true;}
        else
        {
             Stage stage = (Stage) bn_expandview.getScene().getWindow();
        stage.setMaxHeight(530);//prev 500
        stage.setMaxWidth(800);
        stage.setMinHeight(520); //prev 500
        stage.setMinWidth(800);
        admin_tab.setVisible(false);
        admpanel = false;

                }

    }

    @FXML
    private void load_admin_clients(Event event) {
        if(loadCli == false)
        {

            col_client_name.setCellValueFactory(new PropertyValueFactory<Client,String>("clientName"));
            col_client_email.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
            col_client_projectNr.setCellValueFactory(new PropertyValueFactory<Client,Integer>("projectNr"));
            col_client_standardRate.setCellValueFactory(new PropertyValueFactory<Client,Float>("standardRate"));
            admin_clients.setItems(dataClient);
            loadCli = true;
        }
        else
        {
            debug("Clients already loaded");
        }

    }

    @FXML
    private void load_admin_projects(Event event) {
        if(loadProject == false)
        {
            //ArrayList<Project> list = UVM.getAllProjects();
          //  ObservableList<Project> dataProject =  FXCollections.observableArrayList(list);
            col_project_name.setCellValueFactory(new PropertyValueFactory<Project,String>("projectName"));
            col_project_name.setCellFactory(TextFieldTableCell.forTableColumn());
            col_project_name.setOnEditCommit(
                (TableColumn.CellEditEvent<Project, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setProjectName(t.getNewValue())
                );
            col_project_client.setCellValueFactory(new PropertyValueFactory<Project,String>("clientName"));
            col_project_contact.setCellValueFactory(new PropertyValueFactory<Project,String>("phoneNr"));
            col_project_contact.setCellFactory(TextFieldTableCell.forTableColumn());
            col_project_contact.setOnEditCommit(
                (TableColumn.CellEditEvent<Project, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPhoneNr(t.getNewValue())
                );
            col_project_time.setCellValueFactory(new PropertyValueFactory<Project,String>("seconds"));
            col_project_payment.setCellValueFactory(new PropertyValueFactory<Project,String>("calPayment"));
            col_project_allocatedhours.setCellValueFactory(new PropertyValueFactory<Project,Integer>("allocatedHours"));
            admin_projects.setItems(datax);
            loadProject= true;
        }
        else
        {
            debug("Projects already loaded");
        }

    }

    @FXML
    private void load_admin_tasks(Event event) {
        if(loadAdminTasks == false)
        {
           // List<Task> tasklist = UVM.getAllTasksForAdmin();
          //  ObservableList<Task> dataTasks =  FXCollections.observableArrayList(tasklist);
            col_task_name.setCellValueFactory(new PropertyValueFactory<Task,String>("taskName"));
            col_task_project.setCellValueFactory(new PropertyValueFactory<Task,String>("associatedProjectName"));
            col_task_user.setCellValueFactory(new PropertyValueFactory<Task ,String>("users"));
            col_task_userRate.setCellValueFactory(new PropertyValueFactory<Task, Float>("salary"));
            col_task_time.setCellValueFactory(new PropertyValueFactory<Task, String>("hours"));
            col_task_projectRate.setCellValueFactory(new PropertyValueFactory<Task,String>("projectPayment"));
            col_task_description.setCellValueFactory(new PropertyValueFactory<Task,String>("desc"));
            admin_tasks.setItems(dataTasks);
            loadAdminTasks= true;
        }
        else
        {
            debug("tasks already loaded");
        }

    }

    @FXML
    private void load_admin_users(Event event) {
        if(loadUsers == false)
        {

            col_user_name.setCellValueFactory(new PropertyValueFactory<User,String>("userName"));
            col_user_time.setCellValueFactory(new PropertyValueFactory<User, String>("niceTime"));
            col_user_salary.setCellValueFactory(new PropertyValueFactory<User ,Float>("salary"));
            col_user_admin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<User, CheckBox> arg0) {
                User user = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(user.getIsAdmin());



                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {

                        user.setIsAdmin(new_val);

                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
            col_user_email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
            col_user_password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
            admin_users.setItems(dataUsers);
            loadUsers= true;
        }
        else
        {
            debug("users already loaded");
        }
    }

    @FXML
    private void adm_choose_client(MouseEvent event) {
        tf_adm_client_email.setText(admin_clients.getSelectionModel().getSelectedItem().getEmail());
        tf_adm_client_name.setText(admin_clients.getSelectionModel().getSelectedItem().getClientName());
        float f = admin_clients.getSelectionModel().getSelectedItem().getStandardRate();
        String s= Float.toString(f);
        tf_adm_client_standardRate.setText(s);
    }

    @FXML
    private void adm_edit_client(ActionEvent event) {
        UVM.editClient(admin_clients.getSelectionModel().getSelectedItem(),tf_adm_client_name.getText(),Float.valueOf(tf_adm_client_standardRate.getText()),tf_adm_client_email.getText());
    }

    @FXML
    private void adm_add_client(ActionEvent event) {
       Client c =  UVM.addNewClientToDB(tf_adm_client_name.getText(),Float.valueOf(tf_adm_client_standardRate.getText()),tf_adm_client_email.getText());
         dataClient.add(c);
    }

    @FXML
    private void adm_choose_project(MouseEvent event) {
    tf_adm_project_contact.setText(admin_projects.getSelectionModel().getSelectedItem().getPhoneNr());
    tf_adm_project_hours.setText(admin_projects.getSelectionModel().getSelectedItem().getSeconds());
    tf_adm_project_name.setText(admin_projects.getSelectionModel().getSelectedItem().getProjectName());
    tf_adm_project_payment.setText(admin_projects.getSelectionModel().getSelectedItem().getCalPayment());
     ArrayList<Client> list = UVM.getAllClients();
     ObservableList<Client> admdataClient =  FXCollections.observableArrayList(list);
            cb_adm_project_client.getItems().clear();
    cb_adm_project_client.getItems().addAll(admdataClient);
    for(int i = 0;i<admdataClient.size();i++)
            {
               if(admdataClient.get(i).getClientName().equals(admin_projects.getSelectionModel().getSelectedItem().getClientName()))
                 cb_adm_project_client.getSelectionModel().select(admdataClient.get(i));

            }
    }

    @FXML
    private void adm_edit_project(ActionEvent event) {
        UVM.editProject(admin_projects.getSelectionModel().getSelectedItem(),tf_adm_project_name.getText(),cb_adm_project_client.getSelectionModel().getSelectedItem().getId(),Float.valueOf(tf_adm_project_payment.getText()),Integer.valueOf(tf_adm_project_hours.getText()),false,tf_adm_project_contact.getText());
    }

    @FXML
    private void adm_add_project(ActionEvent event) {
        datax.add(UVM.addNewProjectToDB(tf_adm_project_name.getText(),cb_adm_project_client.getSelectionModel().getSelectedItem(),tf_adm_project_contact.getText(),Float.valueOf(tf_adm_project_payment.getText()),Integer.valueOf(tf_adm_project_hours.getText()), false));
    }

    @FXML
    private void adm_choose_task(MouseEvent event) {
        ArrayList<Project> list = UVM.getAllProjects();
            ObservableList<Project> admdataProject =  FXCollections.observableArrayList(list);
             List<User> zlist = UVM.getAllUsers();
            ObservableList<User> admdataUsers =  FXCollections.observableArrayList(zlist);
        tf_adm_task_name.setText(admin_tasks.getSelectionModel().getSelectedItem().getTaskName());
        tf_adm_task_rate.setText(String.valueOf(admin_tasks.getSelectionModel().getSelectedItem().getSalary()));
        tf_adm_task_task_payment.setText(admin_tasks.getSelectionModel().getSelectedItem().getProjectPayment());
        tf_adm_task_task_description.setText(admin_tasks.getSelectionModel().getSelectedItem().getDesc());
         cb_adm_task_project.getItems().clear();
        cb_adm_task_user.getItems().clear();
        cb_adm_task_project.getItems().addAll(admdataProject);
        cb_adm_task_user.getItems().addAll(admdataUsers);
        for(int i = 0;i<admdataProject.size();i++)
            {
               if(admdataProject.get(i).getId() == admin_projects.getSelectionModel().getSelectedItem().getId())
                   cb_adm_task_project.getSelectionModel().select(admdataProject.get(i));
            }
        for(int i = 0;i<admdataUsers.size();i++)
            {
               if(admdataUsers.get(i).getEmail().equals(admin_users.getSelectionModel().getSelectedItem().getEmail()))
                   cb_adm_task_user.getSelectionModel().select(admdataUsers.get(i));;
            ;}
    }

    @FXML
    private void adm_add_task(ActionEvent event) {

    }

    @FXML
    private void adm_edit_task(ActionEvent event) {
        UVM.editTask(admin_tasks.getSelectionModel().getSelectedItem(),tf_adm_task_name.getText(),tf_adm_task_task_description.getText(),admin_tasks.getSelectionModel().getSelectedItem().getAssociatedProjectID());
    }

    @FXML
    private void adm_choose_user(MouseEvent event) {
        tf_adm_user_email.setText(admin_users.getSelectionModel().getSelectedItem().getEmail());
        tf_adm_user_name.setText(admin_users.getSelectionModel().getSelectedItem().getUserName());
        tf_adm_user_payperh.setText(String.valueOf(admin_users.getSelectionModel().getSelectedItem().getSalary()));
        tf_adm_user_password.setText(admin_users.getSelectionModel().getSelectedItem().getPassword());
         cb_adm_user_admin.getItems().clear();
        cb_adm_user_admin.getItems().addAll(true,false);

        if(admin_users.getSelectionModel().getSelectedItem().getIsAdmin() == true)
            cb_adm_user_admin.getSelectionModel().select(true);
        else if(admin_users.getSelectionModel().getSelectedItem().getIsAdmin() == false)
            cb_adm_user_admin.getSelectionModel().select(false);

    }

    @FXML
    private void adm_edit_user(ActionEvent event) {
        UVM.editUser(admin_users.getSelectionModel().getSelectedItem(),tf_adm_user_name.getText(),tf_adm_user_email.getText(),tf_adm_user_password.getText(),Float.valueOf(tf_adm_user_payperh.getText()), false);

    }

    @FXML
    private void adm_add_user(ActionEvent event) {
        dataUsers.add(UVM.createUser(tf_adm_user_name.getText(),tf_adm_user_email.getText(),tf_adm_user_password.getText(),Float.valueOf(tf_adm_user_payperh.getText()), false));
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

    @FXML
    private void load_stat_tab(Event event) {
        if(cb_stat_task.getItems().isEmpty())
        {  
        Project p = new Project(0,"All Projects",0,"",0,0,false);
        cb_stat_task.getItems().addAll(datax);
        cb_stat_task.getItems().add(0, p);
        cb_stat_time.getItems().addAll("Last Month","Last Week","Current Month","Current Week");}
        
     
        
    }
    private void showAllprojectsForGraph(String startTime,String finishTime)
    {
        ArrayList<Coordinates> list = UVM.getAllProjectsForUserGraph(lu.getId(),startTime,finishTime);
        XYChart.Series series = new XYChart.Series();
       stat_graph.setAnimated(false);
 
        for(int i = 0;i<list.size();i++)
        { 
            int hours = Math.round(list.get(i).getY()/3600);
            series.getData().add(new XYChart.Data<String,Integer>(Integer.toString(list.get(i).getX()),hours));
        }
        stat_graph.getData().add(series);
         stat_graph.setLegendVisible(false);
    }

    private void showSelectedProjectForGraph(String startTime,String finishTime,int projectId)
    {
        ArrayList<Coordinates> list = UVM.getSingleProjectForUserGraph(lu.getId(),startTime,finishTime,projectId);
        XYChart.Series series = new XYChart.Series();
       stat_graph.setAnimated(false);
 
        for(int i = 0;i<list.size();i++)
        { 
            series.getData().add(new XYChart.Data<String,Integer>(Integer.toString(list.get(i).getX()),list.get(i).getY()));
        }
        stat_graph.getData().add(series);
         stat_graph.setLegendVisible(false);
    }
    
    private void showSingleClientForAdmGraph(String startTime,String finishTime,int clientId)
    {
         ArrayList<Coordinates> list = UVM.getSingleClientForAdminGraph(startTime,finishTime,clientId);
        XYChart.Series series = new XYChart.Series();
       stat_adm_graph.setAnimated(false);
 
        for(int i = 0;i<list.size();i++)
        { 
            series.getData().add(new XYChart.Data<String,Integer>(Integer.toString(list.get(i).getX()),list.get(i).getY()));
        }
        stat_adm_graph.getData().add(series);
         stat_adm_graph.setLegendVisible(false);
    }
    
    private void showAllClientsForAdmGraph(String startTime,String finishTime)
    {
           ArrayList<Coordinates> list = UVM.getAllClientsForAdminGraph(startTime,finishTime);
        XYChart.Series series = new XYChart.Series();
       stat_adm_graph.setAnimated(false);
 
        for(int i = 0;i<list.size();i++)
        { 
            series.getData().add(new XYChart.Data<String,Integer>(Integer.toString(list.get(i).getX()),list.get(i).getY()));
        }
        stat_adm_graph.getData().add(series);
         stat_adm_graph.setLegendVisible(false);
    }
    
    
     private void showSingleProjectForAdmGraph(String startTime,String finishTime)
    {
           ArrayList<Coordinates> list = UVM.getSingleProjectForAdmGraph(startTime,finishTime,lu.getId());
        XYChart.Series series = new XYChart.Series();
       stat_graph.setAnimated(false);
 
        for(int i = 0;i<list.size();i++)
        { 
            series.getData().add(new XYChart.Data<String,Integer>(Integer.toString(list.get(i).getX()),list.get(i).getY()));
        }
        stat_graph.getData().add(series);
         stat_graph.setLegendVisible(false);
    }

    @FXML
    private void load_column_tab(Event event) {
        if(cb_bar_usr_data.getItems().isEmpty())
        {  
            Project p = new Project(-1,"All Projects",0,"",0,0,false);
            ArrayList<Project> projectsUsr = UVM.getAllProjectsForUserTab();
            projectsUsr.add(0,p);
            cb_bar_usr_data.getItems().addAll(projectsUsr);
            cb_bar_usr_time.getItems().addAll("Last Month","Last Week","Current Month","Current Week");
        }
        
    }

    @FXML
    private void load_admin_column(Event event) {
        if(cb_bar_adm_data.getItems().isEmpty())
        {  
            Project p = new Project(-1,"All Projects",0,"",0,0,false);
            ArrayList<Project> projectsAdmBar = UVM.getAllProjects();
            projectsAdmBar.add(0,p);
            cb_bar_adm_data.getItems().addAll("Clients", "Projects", "Users");
            cb_bar_adm_time.getItems().addAll("Last Month","Last Week","Current Month","Current Week");
        }
        
    }

    @FXML
    private void show_linechart(ActionEvent event) {
        datepicked = true;
       showLineChart();
          
    }

    
    
    private void showLineChart()
    {
         String startTime = "";
        String finishTime = "";
        if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Last Month"))
        {
           LocalDate today = LocalDate.now();  // Retrieve the date now
           LocalDate lastMonth = today.minus(1, ChronoUnit.MONTHS); // Retrieve the date a month from now
          startTime = String.valueOf(lastMonth.withDayOfMonth(1)); // retrieve the first date
          finishTime = String.valueOf(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())); // retrieve the last date   
        }       
        else if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Current Month"))        
        {
            startTime = String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
            finishTime = String.valueOf(LocalDate.now());
          ;
        }
        else if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Last Week")) 
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
           LocalDate lastWeek = today.minus(1, ChronoUnit.WEEKS); // Retrieve the date a month from now
          startTime = String.valueOf(lastWeek.with((DayOfWeek.MONDAY)));
          finishTime = String.valueOf(lastWeek.with((DayOfWeek.SUNDAY)));
        }
        else if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Current Week"))
        {
             LocalDate today = LocalDate.now();  // Retrieve the date now
             startTime = String.valueOf(today.with((DayOfWeek.MONDAY)));
             finishTime = String.valueOf(today.with((DayOfWeek.SUNDAY)));
             
        }
        
        
        if(cb_stat_task.getSelectionModel().getSelectedItem().getProjectName().equals("All Projects"))
        {
            stat_graph.getData().clear();
            showAllprojectsForGraph(startTime, finishTime);
        }
        else
        {
             stat_graph.getData().clear();
          int id = cb_stat_task.getSelectionModel().getSelectedItem().getId();
            showSelectedProjectForGraph(startTime, finishTime,id);          
        }
    }
    
    private void showAdmLineChart()
    {
         String startTime = "";
        String finishTime = "";
        if(cb_stat_adm_time.getSelectionModel().getSelectedItem().equals("Last Month"))
        {
           LocalDate today = LocalDate.now();  // Retrieve the date now
           LocalDate lastMonth = today.minus(1, ChronoUnit.MONTHS); // Retrieve the date a month from now
          startTime = String.valueOf(lastMonth.withDayOfMonth(1)); // retrieve the first date
          finishTime = String.valueOf(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())); // retrieve the last date  
         
        }       
        else if(cb_stat_adm_time.getSelectionModel().getSelectedItem().equals("Current Month"))        
        {
            startTime = String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
            finishTime = String.valueOf(LocalDate.now());
         
        }
        else if(cb_stat_adm_time.getSelectionModel().getSelectedItem().equals("Last Week")) 
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
           LocalDate lastWeek = today.minus(1, ChronoUnit.WEEKS); // Retrieve the date a month from now
          startTime = String.valueOf(lastWeek.with((DayOfWeek.MONDAY)));
          finishTime = String.valueOf(lastWeek.with((DayOfWeek.SUNDAY)));
     
        }
        else if(cb_stat_adm_time.getSelectionModel().getSelectedItem().equals("Current Week"))
        {
             LocalDate today = LocalDate.now();  // Retrieve the date now
             startTime = String.valueOf(today.with((DayOfWeek.MONDAY)));
             finishTime = String.valueOf(today.with((DayOfWeek.SUNDAY)));
            
        }
        
        
        if(cb_stat_adm_task.getSelectionModel().getSelectedItem().getClientName().equals("All Clients"))
        {
            stat_adm_graph.getData().clear();
            showAllClientsForAdmGraph(startTime, finishTime);
        }
        else
        {
             stat_adm_graph.getData().clear();
          int id = cb_stat_adm_task.getSelectionModel().getSelectedItem().getId();
           showSingleClientForAdmGraph(startTime,finishTime,id);  
        }
    }
    
    @FXML
    private void show_after_date(ActionEvent event) {
        if(datepicked)
            showLineChart();
    }

    @FXML
    private void admin_showlinechart(ActionEvent event) {
        showAdmLineChart();
        admdatepicked = true;
    }

    @FXML
    private void adm_show_afterdate(ActionEvent event) {
        if(admdatepicked)
            showAdmLineChart();
    }

    @FXML
    private void load_admin_linechart(Event event) {
        if(cb_stat_adm_time.getItems().isEmpty())
        {    
            Client c = new Client(0,"All Clients",0,"");
        cb_stat_adm_time.getItems().addAll("Last Month","Last Week","Current Month","Current Week");
        cb_stat_adm_task.getItems().addAll(dataClient);
        cb_stat_adm_task.getItems().add(0,c);
                }
    }

    @FXML
    private void selectDataBarUsr(ActionEvent event) {
        barUsrDataPicked = true;
        if(barUsrTimePicked)
        {
            showUsrBarChart();
        }
    
    }

    @FXML
    private void selectTimeBarUsr(ActionEvent event) {
        barUsrTimePicked = true;
        if(barUsrDataPicked)
        {
            showUsrBarChart();
        }
    }

    private void showUsrBarChart() {
        String startTime = "";
        String finishTime = "";
        if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Last Month"))
        {
           LocalDate today = LocalDate.now();  // Retrieve the date now
           LocalDate lastMonth = today.minus(1, ChronoUnit.MONTHS); // Retrieve the date a month from now
          startTime = String.valueOf(lastMonth.withDayOfMonth(1)); // retrieve the first date
          finishTime = String.valueOf(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())); // retrieve the last date   
        }       
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Current Month"))        
        {
            startTime = String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
            finishTime = String.valueOf(LocalDate.now());
          ;
        }
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Last Week")) 
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
           LocalDate lastWeek = today.minus(1, ChronoUnit.WEEKS); // Retrieve the date a month from now
          startTime = String.valueOf(lastWeek.with((DayOfWeek.MONDAY)));
          finishTime = String.valueOf(lastWeek.with((DayOfWeek.SUNDAY)));
        }
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Current Week"))
        {
             LocalDate today = LocalDate.now();  // Retrieve the date now
             startTime = String.valueOf(today.with((DayOfWeek.MONDAY)));
             finishTime = String.valueOf(today.with((DayOfWeek.SUNDAY)));
             
        }
        
        
        if(cb_bar_usr_data.getSelectionModel().getSelectedItem().getProjectName().equals("All Projects"))
        {
            stat_bar.getData().clear();
            showAllprojectsBarUsr(startTime, finishTime);
        }
        else
        {
             stat_bar.getData().clear();
          Project p = cb_bar_usr_data.getSelectionModel().getSelectedItem();
            showOneProjectBarUsr(p, startTime, finishTime);          
        }
    }

    private void showAllprojectsBarUsr(String startTime, String finishTime) {
        stat_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getAllProjectsForUsrBar(startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        stat_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            seriesArray[j].setName(list.get(j).getSubBar());
            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getTopBar(),hours));
            stat_bar.getData().add(seriesArray[j]);
        
        }
        stat_bar.setLegendVisible(true);
    }
    
    
    
    
    private void showOneProjectBarUsr(Project p, String startTime, String finishTime) {
        stat_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getOneProjectForUsrBar(p, startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        stat_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getSubBar(),hours));
            stat_bar.getData().add(seriesArray[j]);
        
        }
        stat_bar.setLegendVisible(false);
    }

    @FXML
    private void selectDataBarAdm(ActionEvent event) {
        if(cb_bar_adm_data.getSelectionModel().getSelectedItem().equals("Clients"))
        {
            ComboBox<Client> cb_bar_adm_data2 = new JFXComboBox<Client>();
            ArrayList<Client> clients = UVM.getAllClients();
            Client c = new Client(-1, "All Clients", 0, "");
            clients.add(0, c);
            cb_bar_adm_data2.getItems().addAll(clients);
            col_tab_anchor.getChildren().add(cb_bar_adm_data2);
            cb_bar_adm_data2.setOnAction(e -> {
                barAdmDataPicked = true;
                if(barAdmTimePicked)
                {
                    Client tempclient = cb_bar_adm_data2.getSelectionModel().getSelectedItem();
                    showAdmBarChart(tempclient);
                }
            });
        }
        else if(cb_bar_adm_data.getSelectionModel().getSelectedItem().equals("Projects"))
        {
            ComboBox<Project> cb_bar_adm_data2 = new JFXComboBox<Project>();
            ArrayList<Project> projects = UVM.getAllProjects();
            Project p = new Project(-1, "All Projects", 0, "", 0, 0, false);
            projects.add(0, p);
            cb_bar_adm_data2.getItems().addAll(projects);
            col_tab_anchor.getChildren().add(cb_bar_adm_data2);
            cb_bar_adm_data2.setOnAction(e -> {
                barAdmDataPicked = true;
                if(barAdmTimePicked)
                {
                    Project tempproject = cb_bar_adm_data2.getSelectionModel().getSelectedItem();
                    showAdmBarChart(tempproject);
                }
            });
        }
        if(cb_bar_adm_data.getSelectionModel().getSelectedItem().equals("Users"))
        {
            ComboBox<User> cb_bar_adm_data2 = new JFXComboBox<User>();
            ArrayList<User> users = UVM.getAllUsers();
            User u = new User(-1, "All Users", "", "", 0, false);
            users.add(0, u);
            cb_bar_adm_data2.getItems().addAll(users);
            col_tab_anchor.getChildren().add(cb_bar_adm_data2);
            cb_bar_adm_data2.setOnAction(e -> {
                barAdmDataPicked = true;
                if(barAdmTimePicked)
                {
                    User tempuser = cb_bar_adm_data2.getSelectionModel().getSelectedItem();
                    showAdmBarChart(tempuser);
                }
            });
            
        }
        
        
    }

    @FXML
    private void selectTimeBarAdm(ActionEvent event) {
        barAdmTimePicked = true;
    }

    private void showAdmBarChart(Client c) {
        String[] times = getAdminBarTimes();
        String startTime = times[0];
        String finishTime = times[1];
        
        
        if(c.getClientName().equals("All Clients"))
        {
            adm_stack_bar.getData().clear();
            showAllClientsBarAdm(startTime, finishTime);
        }
        else
        {
            adm_stack_bar.getData().clear();
            showOneClientBarAdm(c, startTime, finishTime);          
        }     
    }
    
        private void showAdmBarChart(Project p) {
        String[] times = getAdminBarTimes();
        String startTime = times[0];
        String finishTime = times[1];
        
        if(p.getProjectName().equals("All Projects"))
        {
            adm_stack_bar.getData().clear();
            showAllprojectsBarAdm(startTime, finishTime);
        }
        else
        {
            adm_stack_bar.getData().clear();
            showOneProjectBarAdm(p, startTime, finishTime);          
        }        
    }
    
        private void showAdmBarChart(User u) {
        String[] times = getAdminBarTimes();
        String startTime = times[0];
        String finishTime = times[1];
        
        if(u.getUserName().equals("All Users"))
        {
            adm_stack_bar.getData().clear();
            showAllUsersBarAdm(startTime, finishTime);
        }
        else
        {
            adm_stack_bar.getData().clear();
            showOneUserBarAdm(u, startTime, finishTime);          
        }              
    }

    private void showAllprojectsBarAdm(String startTime, String finishTime) {
        adm_stack_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getAllProjectsForAdmBar(startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        adm_stack_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            seriesArray[j].setName(list.get(j).getSubBar());
            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getTopBar(),hours));
            adm_stack_bar.getData().add(seriesArray[j]);
        
        }
        adm_stack_bar.setLegendVisible(true);
    }

    private void showOneProjectBarAdm(Project p, String startTime, String finishTime) {
        adm_stack_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getOneProjectForAdmBar(p, startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        adm_stack_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getSubBar(),hours));
            adm_stack_bar.getData().add(seriesArray[j]);
        
        }
        adm_stack_bar.setLegendVisible(false);
    }

    private void showAllClientsBarAdm(String startTime, String finishTime) {
        adm_stack_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getAllClientsForAdmBar(startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        adm_stack_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            seriesArray[j].setName(list.get(j).getSubBar());
            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getTopBar(),hours));
            adm_stack_bar.getData().add(seriesArray[j]);
        
        }
        adm_stack_bar.setLegendVisible(true);
    }

    private void showOneClientBarAdm(Client c, String startTime, String finishTime) {
        adm_stack_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getOneClientForAdmBar(c, startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        adm_stack_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            seriesArray[j].setName(list.get(j).getSubBar());
            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getTopBar(),hours));
            adm_stack_bar.getData().add(seriesArray[j]);
        
        }
        adm_stack_bar.setLegendVisible(true);
    }

    private void showAllUsersBarAdm(String startTime, String finishTime) {
        adm_stack_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getAllUsersForAdmBar(startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        adm_stack_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getSubBar(),hours));
            adm_stack_bar.getData().add(seriesArray[j]);
        
        }
        adm_stack_bar.setLegendVisible(false);
    }

    private void showOneUserBarAdm(User u, String startTime, String finishTime) {
        adm_stack_bar.getData().clear();
        int k = 0;
        ArrayList<Coordinates> list = UVM.getOneUserForAdmBar(u, startTime, finishTime);
        XYChart.Series series = new XYChart.Series();
        adm_stack_bar.setAnimated(false);
        int size = list.size();
        XYChart.Series<String, Integer>[] seriesArray = Stream.<XYChart.Series<String, Integer>>generate(XYChart.Series::new).limit(size).toArray(XYChart.Series[]::new);

        for(int j = 0;j<list.size();j++)
        {

            seriesArray[j].setName(list.get(j).getSubBar());
            long time = list.get(j).getTaskSeconds();
            debug("Time in seconds " + time);
            int hours = Math.round(time/3600);
            debug("After rounding: " + hours);
            seriesArray[j].getData().add(new XYChart.Data<String,Integer>(list.get(j).getTopBar(),hours));
            adm_stack_bar.getData().add(seriesArray[j]);
        
        }
        adm_stack_bar.setLegendVisible(true);
    }

    private String[] getAdminBarTimes() {
        String startTime = "";
        String finishTime ="";
       if(cb_bar_adm_time.getSelectionModel().getSelectedItem().equals("Last Month"))
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
            LocalDate lastMonth = today.minus(1, ChronoUnit.MONTHS); // Retrieve the date a month from now
            startTime = String.valueOf(lastMonth.withDayOfMonth(1)); // retrieve the first date
            finishTime = String.valueOf(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())); // retrieve the last date   
        }       
        else if(cb_bar_adm_time.getSelectionModel().getSelectedItem().equals("Current Month"))        
        {
            startTime = String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
            finishTime = String.valueOf(LocalDate.now());
        }
        else if(cb_bar_adm_time.getSelectionModel().getSelectedItem().equals("Last Week")) 
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
            LocalDate lastWeek = today.minus(1, ChronoUnit.WEEKS); // Retrieve the date a month from now
            startTime = String.valueOf(lastWeek.with((DayOfWeek.MONDAY)));
            finishTime = String.valueOf(lastWeek.with((DayOfWeek.SUNDAY)));
        }
        else if(cb_bar_adm_time.getSelectionModel().getSelectedItem().equals("Current Week"))
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
            startTime = String.valueOf(today.with((DayOfWeek.MONDAY)));
            finishTime = String.valueOf(today.with((DayOfWeek.SUNDAY)));
             
        } 
       String[] dates = {startTime, finishTime};
       return dates;
       
    }
}
