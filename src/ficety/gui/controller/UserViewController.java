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
import ficety.be.EntityItem;
import ficety.be.LoggedInUser;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.dal.ClientDBDAO;
import ficety.gui.model.UserViewModel;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
import javafx.util.Pair;
import javafx.util.StringConverter;
import javax.swing.JFrame;


/**
 *
 * @author Trigger
 */
public class UserViewController extends JFrame implements Initializable {

    private boolean debug = false;
    private Label label;
    private UserViewModel UVM ;
    private ObservableList<Task> dataTasks;
    private ObservableList<Client> dataClient;
    private List<Task> tasklist;
    private ArrayList<Client> clientlist;
    private List<User> userlist ;
    private ObservableList<Session> datasession;
    boolean added = true;
    boolean datepicked = false;
    boolean admdatepicked = false;
    
    boolean barUsrDataPicked = false;
    boolean barUsrTimePicked = false;
    

    
    //boolean selected = false;
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
private ObservableList<Task> datatask;
    @FXML
    private TableColumn col_task_bill1;
    @FXML
    private TableColumn col_pj_closed;
    @FXML
    private TableColumn col_pj_closed1;
    @FXML
    private JFXButton bn_exp1;
    @FXML
    private JFXButton bn_exp22;
    @FXML
    private JFXButton bn_exp23;
    @FXML
    private JFXButton bn_exp2;
    @FXML
    private JFXButton bn_exp21;
    private String startTime = "";
    private String finishTime = "";
    @FXML
    private JFXButton btn_refreshAll;
    @FXML
    private TreeTableColumn<EntityItem,String> over_col1;
    @FXML
    private TreeTableColumn<EntityItem,String> over_col2;
    @FXML
    private TreeTableView<EntityItem> tbv_over;
    @FXML
    private TreeTableColumn<EntityItem,String> over_col3;
    @FXML
    private TreeTableColumn<EntityItem,String> over_col4;
    @FXML
    private TreeTableColumn<EntityItem,String> over_col5;
    @FXML
    private TreeTableView<EntityItem> tbv_user_over;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_time;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_btime;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_tmonth;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_project;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_ptime;
    @FXML
    private TreeTableView<EntityItem> tree_tbv_adm;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_timeadm;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_btimeadm;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_tmonthadm;
    @FXML
    private TreeTableColumn<EntityItem,String> over_user_salaryadm;
    private TreeTableColumn<EntityItem,String> over_user_ptimeadm;
    private List<Project> treelist;
    private List<User> treeuserlist;
    private List<User> treeuserlist1;
    @FXML
    private TreeTableColumn<EntityItem,String> over_col25;
    public UserViewController()
    {
       
        MaxWidth = 260;
        min = true;
       UVM = new UserViewModel();
       treelist = UVM.getAllProjectsForUserTab();
      clientlist = UVM.getAllClients();
      dataClient =  FXCollections.observableArrayList(clientlist);
      userlist = UVM.getAllUsers();
      datasession =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
      datatask =  FXCollections.observableArrayList(UVM.getTasksForUserInfo());
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
    private TableColumn<Project, Client> Col_pj_clint;
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
    private TableColumn<Task,Project> Col_task_project;
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
    private TableColumn<Session, Task> col_sesion_taskname;
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
    private TableColumn<Project, Client> col_project_client;
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
    private TableColumn<Task, Project> col_task_project;
    @FXML
    private TableColumn<Task, String> col_task_user;
    @FXML
    private TableColumn<Task, Float> col_task_userRate;
    @FXML
    private TableColumn<Task, String> col_task_time;
    @FXML
    private TableColumn<Task, String> col_task_projectRate;



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

    private ObservableList<String> clientstring;
    
    private ObservableList<Project> datapj;
    private List<Project> listpj;
    private boolean treeuser = false;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     //   clientstring = FXCollections.observableArrayList();
    //    for(int i = 0;i<clientlist.size();i++)
    //    {
    //       clientstring.add(clientlist.get(i).getClientName());
           
     //   }
        listpj = UVM.getAllOpenProjects();
       datapj =  FXCollections.observableArrayList(listpj);
       datax = FXCollections.observableArrayList(UVM.get3RecentProjects());
       cb_project.getItems().addAll(datapj);
//      cb_task_project.getItems().addAll(datax);
       
   loadButtons();
         loaded = true;
       //  loadOver();

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
                  lu.setCurrentTask(tmp);
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
                stage.setMaxWidth(820);
                stage.setMinHeight(530); //prev 500
                stage.setMinWidth(820);
                tb_toggle.setLayoutY(409);
                bn_start_stop.setLayoutY(343);
                lb_tasktime.setLayoutY(342);
                lb_timetoday.setLayoutY(376);
                label_task.setLayoutY(356);
                label_today.setLayoutY(383);
                scroll.setVisible(true);
                admin_tab.setVisible(false);
                admpanel = false;

            }
        else{
               // Sp_last3.setVisible(false);
                ap.setVisible(false);
               //user_tabpane.setVisible(false);
                 min = false;

                debug("Size toggle false");
                Stage stage = (Stage) ap.getScene().getWindow();
                stage.setMaxHeight(200); //prev 208
                stage.setMaxWidth(245); //prev 340
                stage.setMinHeight(200);
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
        Pair<Task, Session> ourPair = UVM.addNewTaskAndSetItRunning(taskName, taskBillable, associatedProject);
        Task c = ourPair.getKey();
        Session s = ourPair.getValue();
        tasklist.add(c);
        datatask.add(c);
        datasession.add(s);
        if(isTimerRunning)
        {
            timer.stop();
            time = 0;
            isTimerRunning = false;
        }
        timer.start();
        time = 0;
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
export = 1;
    }

    @FXML
    private void load_session_tab(Event event) throws SQLException {

export = 2;

    }

    @FXML
    private void load_pj_tab(Event event) throws InterruptedException {

export = 3;



    }

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
    {  // tbv_task.setEditable(true);
	// allows the individual cells to be selected
	//tbv_task.getSelectionModel().cellSelectionEnabledProperty().set(true);
        
         Col_task_taskname.setCellValueFactory(new PropertyValueFactory<Task, String>("taskName"));
         Col_task_taskname.setCellFactory(TextFieldTableCell.forTableColumn());
         Col_task_taskname.setOnEditCommit(
                (TableColumn.CellEditEvent<Task, String> t) ->
                     
                    (
                            t.getTableView().getItems().get(
                           t.getTablePosition().getRow())
                    ).setTaskName(t.getNewValue())
                 
                );
        Col_task_description.setCellValueFactory(new PropertyValueFactory<Task, String>("desc"));
        Col_task_description.setCellFactory(TextFieldTableCell.forTableColumn());
        Col_task_description.setOnEditCommit(
                (TableColumn.CellEditEvent<Task, String> t) ->
                    ( t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDesc(t.getNewValue())
                );
        Col_task_project.setCellValueFactory(new PropertyValueFactory<>("associatedProjectName"));
        Col_task_project.setCellFactory(ComboBoxTableCell.forTableColumn(datax));
        Col_task_project.setOnEditCommit((TableColumn.CellEditEvent<Task,Project> e) -> 
        {
         String s = e.getNewValue().getProjectName();
         int id = e.getNewValue().getId();
         Task t = e.getRowValue();
         t.setAssociatedProjectID(id);
         t.setAssociatedProjectName(s);
    });
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
        col_sesion_taskname.setCellValueFactory(new PropertyValueFactory<>("taskName"));
       col_sesion_taskname.setCellFactory(ComboBoxTableCell.forTableColumn(dataTasks));
        col_sesion_taskname.setOnEditCommit((TableColumn.CellEditEvent<Session,Task> e) -> 
        {
         Task t = e.getNewValue();
         Session s = e.getRowValue();
         int id = t.getTaskID();
         s.setAssociatedTaskID(id);
         
    });
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
        Col_pj_clint.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        Col_pj_contact.setCellValueFactory(new PropertyValueFactory<Project,String>("phoneNr"));
        Col_pj_myhours.setCellValueFactory(new PropertyValueFactory<Project,Integer>("seconds"));
        Col_pj_name.setCellValueFactory(new PropertyValueFactory<Project,String>("projectName"));
        col_pj_closed.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Project, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Project, CheckBox> arg0) {
                Project user = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                checkBox.selectedProperty().setValue(user.getIsClosed());



                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                            Boolean old_val, Boolean new_val) {

                        user.setIsClosed(new_val);

                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
        Tbv_pj.setItems(datapj);
        lb_loginuser.setText(lu.getName());

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
              //  UVM.editTask(lu.getCurrentTask(), task_name.getText(), task_description.getText() , cb_task_project.getSelectionModel().getSelectedItem().getId() );
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
             List<Project> list = UVM.getAllProjectsForUserTab();
        ObservableList<Project> datapj =  FXCollections.observableArrayList(list);
        Project p = new Project(0,"All Projects",0,"",0,0,false);
        cb_stat_task.getItems().addAll(datapj);
        cb_stat_task.getItems().add(0, p);
        cb_stat_time.getItems().addAll("Last Month","Last Week","Current Month","Current Week","Custom Date");}
        
     
        
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
        for(Series<String, Integer> seriess : stat_graph.getData())
           {
               for(XYChart.Data<String, Integer> data : seriess.getData())
               {
                   time = 0;
                for(Coordinates c : list)
                {
                    if(c.getX() == Integer.parseInt(data.getXValue()))
                    {
                        time = c.getY();
                    }
                }
                Tooltip tooltip = new Tooltip();
                float wholeHours = time/3600;
                debug("" + wholeHours);
                float quarterHours = (float) (Math.round(((time%3600)/60)/15)*0.25);
                debug("" + quarterHours);
                float totalTime = wholeHours + quarterHours;
                tooltip.setText("Date: " + data.getXValue() + "; " + totalTime + " hours" );
                Tooltip.install(data.getNode(), tooltip);
               }
           }
        stat_graph.setLegendVisible(false);
    }

    private void showSelectedProjectForGraph(String startTime,String finishTime,int projectId)
    {
        ArrayList<Coordinates> list = UVM.getSingleProjectForUserGraph(lu.getId(),startTime,finishTime,projectId);
        XYChart.Series series = new XYChart.Series();
       stat_graph.setAnimated(false);
 
        for(int i = 0;i<list.size();i++)
        { 
            int hours = Math.round(list.get(i).getY()/3600);
            series.getData().add(new XYChart.Data<String,Integer>(Integer.toString(list.get(i).getX()),hours));
        }
        stat_graph.getData().add(series);
        for(Series<String, Integer> seriess : stat_graph.getData())
           {
               for(XYChart.Data<String, Integer> data : seriess.getData())
               {
                   time = 0;
                for(Coordinates c : list)
                {
                    if(c.getX() == Integer.parseInt(data.getXValue()))
                    {
                        time = c.getY();
                    }
                }
                Tooltip tooltip = new Tooltip();
                float wholeHours = time/3600;
                debug("" + wholeHours);
                float quarterHours = (float) (Math.round(((time%3600)/60)/15)*0.25);
                debug("" + quarterHours);
                float totalTime = wholeHours + quarterHours;
                tooltip.setText("Date: " + data.getXValue() + "; " + totalTime + " hours" );
                Tooltip.install(data.getNode(), tooltip);
               }
           }
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
            cb_bar_usr_time.getItems().addAll("Last Month","Last Week","Current Month","Current Week", "Custom Date");
        }
        
    }

   

    @FXML
    private void show_linechart(ActionEvent event) {
        datepicked = true;
       showLineChart();
          
    }

    
    
    private void showLineChart()
    {
        
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
        else if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Custom Date"))
        {
             try {
                 Parent root1;
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ficety/gui/view/DateSelection.fxml"));
                 root1 = (Parent) fxmlLoader.load();
                 fxmlLoader.<UserViewController>getController();
                 Stage addStage = new Stage();
                 Scene addScene = new Scene(root1);
                 addStage.setScene(addScene);
                 addStage.show();
             } catch (IOException ex) {
                 Logger.getLogger(UserViewController.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        
 
    }
    
  
    
    @FXML
    private void show_after_date(ActionEvent event) {
        if(datepicked)
            showLineChart();
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
        
        if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Last Month"))
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
            LocalDate lastMonth = today.minus(1, ChronoUnit.MONTHS); // Retrieve the date a month from now
            lu.setStartTime(String.valueOf(lastMonth.withDayOfMonth(1))); // retrieve the first date
            lu.setFinishTime(String.valueOf(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()))); // retrieve the last date   
        }       
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Current Month"))        
        {
            lu.setStartTime(String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())));
            lu.setFinishTime(String.valueOf(LocalDate.now()));
          ;
        }
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Last Week")) 
        {
            LocalDate today = LocalDate.now();  // Retrieve the date now
            LocalDate lastWeek = today.minus(1, ChronoUnit.WEEKS); // Retrieve the date a month from now
            lu.setStartTime(String.valueOf(lastWeek.with((DayOfWeek.MONDAY))));
            lu.setFinishTime(String.valueOf(lastWeek.with((DayOfWeek.SUNDAY))));
        }
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Current Week"))
        {
             LocalDate today = LocalDate.now();  // Retrieve the date now
             lu.setStartTime(String.valueOf(today.with((DayOfWeek.MONDAY))));
             lu.setFinishTime(String.valueOf(today.with((DayOfWeek.SUNDAY))));
             
        }
        else if(cb_bar_usr_time.getSelectionModel().getSelectedItem().equals("Custom Date"))
        {
             try {
                Parent root1;
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ficety/gui/view/DateSelection.fxml"));
                root1 = (Parent) fxmlLoader.load();
                fxmlLoader.<UserViewController>getController();
                Stage addStage = new Stage();
                Scene addScene = new Scene(root1);
                addStage.setScene(addScene);
                addStage.show();
            } catch (IOException ex) {
                Logger.getLogger(UserViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String startTime = lu.getStartTime();
        String finishTime = lu.getFinishTime();
        
        if(startTime != null && finishTime != null)
        {
        
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
            for(Data<String, Integer> data : seriesArray[j].getData())
           {
               Tooltip tooltip = new Tooltip();
               float wholeHours = time/3600;
               debug("" + wholeHours);
               float quarterHours = (float) (Math.round(((time%3600)/60)/15)*0.25);
               debug("" + quarterHours);
               float totalTime = wholeHours + quarterHours;
               tooltip.setText(list.get(j).getSubBar() + ": " + totalTime + " hours");
               Tooltip.install(data.getNode(), tooltip);
           }
        
        }
        stat_bar.setLegendVisible(false);
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
            for(Data<String, Integer> data : seriesArray[j].getData())
           {
               Tooltip tooltip = new Tooltip();
               float wholeHours = time/3600;
               debug("" + wholeHours);
               float quarterHours = (float) (Math.round(((time%3600)/60)/15)*0.25);
               debug("" + quarterHours);
               float totalTime = wholeHours + quarterHours;
               tooltip.setText(list.get(j).getSubBar() + ": " + totalTime + " hours");
               Tooltip.install(data.getNode(), tooltip);
           }
        
        }
        stat_bar.setLegendVisible(false);
    }

   
        
    

    
   

    @FXML
    private void update_selected(ActionEvent event) {
        Task t = null;
        Project pj = null;
        Session s = null;
        Client c = null;
        User u = null;
        if(admpanel == false)
        {  if(export == 3 )
         {  pj = Tbv_pj.getSelectionModel().getSelectedItem();
       if(UVM.isNumber(String.valueOf(pj.getPhoneNr())))
       { UVM.editProject(pj,pj.getProjectName(),pj.getAssociatedClientID(),pj.getProjectRate(),pj.getAllocatedHours(),pj.getIsClosed(),pj.getPhoneNr());}
       else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Error ! Invalid input : "+String.valueOf(pj.getPhoneNr()));
      alert.showAndWait();
       }
         }
       
        else if(export ==1)
        {
        t = tbv_task.getSelectionModel().getSelectedItem();
        
        UVM.editTask(t,t.getTaskName(),t.getDesc(),t.getAssociatedProjectID(),t.getBillable());
         }
        else if(export == 2)
        { s = tbv_session.getSelectionModel().getSelectedItem();
        if(UVM.isValidDate(s.getStartTime()) && UVM.isValidDate(s.getFinishTime()))
        {
         UVM.editSession(s,s.getStartTime(),s.getFinishTime(),s.getAssociatedTaskID());}
        else
        {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("Error ! Invalid input : "+s.getStartTime()+"  "+s.getFinishTime());
      alert.showAndWait();
        }
        }}
      
        
        }
    
    
    @FXML
    private void show_user_graph(ActionEvent event) {
         String startTimee = lu.getStartTime();
        String finishTimee = lu.getFinishTime();
        if(startTime != null && finishTime != null)
          if(cb_stat_task.getSelectionModel().getSelectedItem().getProjectName().equals("All Projects"))
        {
            stat_graph.getData().clear();
            if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Custom Date"))
            showAllprojectsForGraph(startTimee, finishTimee);
            else
             showAllprojectsForGraph(startTime, finishTime);
           
        }
        else
        {
             stat_graph.getData().clear();
             if(cb_stat_time.getSelectionModel().getSelectedItem().equals("Custom Date"))
             { int id = cb_stat_task.getSelectionModel().getSelectedItem().getId();
            showSelectedProjectForGraph(startTimee, finishTimee,id);      }
             else
             {
                 int id = cb_stat_task.getSelectionModel().getSelectedItem().getId();
            showSelectedProjectForGraph(startTime, finishTime,id);
             }
        }
    }

   
   
    
    
   

    @FXML
    private void show_user_column(ActionEvent event) {
        String startTime = lu.getStartTime();
        String finishTime = lu.getFinishTime();
               
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

    @FXML
    private void refresh_tables(ActionEvent event) {
        debug("refreshing tables.");
        loadCli = false;
        loadProject = false;
        loadUsers = false;
   
        
        tasklist = UVM.getAllTasksForAdmin();
        dataTasks =  FXCollections.observableArrayList(tasklist);
        clientlist = UVM.getAllClients();
        dataClient =  FXCollections.observableArrayList(clientlist);
        userlist = UVM.getAllUsers();
        datasession =  FXCollections.observableArrayList(UVM.getAllSessionsOfAUser());
        datatask =  FXCollections.observableArrayList(UVM.getTasksForUserInfo()); 
        List<Project> list = UVM.getAllOpenProjects();
        cb_project.getItems().removeAll(datapj);
        datapj =  FXCollections.observableArrayList(list);
        cb_project.getItems().addAll(datapj);
        
        loadAll();
       
        
        
    }

  
    

    @FXML
    private void load_user_overview(Event event) {
        if(treeuser == false)
        { loadUserTree();
          treeuser = true;
        }
      }
 private void loadUserTree()
 {
      EntityItem e = new EntityItem();
        e.setUserName("Show");
TreeItem<EntityItem> root = new TreeItem<>(e);
       String  startTime = String.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
       String   finishTime = String.valueOf(LocalDate.now());
        LocalDate today = LocalDate.now();  // Retrieve the date now
            LocalDate lastMonth = today.minus(1, ChronoUnit.MONTHS); // Retrieve the date a month from now
        String startTimee = String.valueOf(lastMonth.withDayOfMonth(1)); // retrieve the first date
        String finishTimee = String.valueOf(lastMonth.withDayOfMonth(lastMonth.lengthOfMonth())); // retrieve the last date   
User u = UVM.getLoggedInUserForOverview(startTime, finishTime);
User u1 = UVM.getLoggedInUserForOverview(startTimee, finishTimee);
u.setUserName("This Month");
u1.setUserName("Last Month");
TreeItem<EntityItem> user = new TreeItem<>(new EntityItem(u));
TreeItem<EntityItem> user1 = new TreeItem<>(new EntityItem(u1));
root.getChildren().addAll(user,user1);
for (Project project : treelist) {
    TreeItem<EntityItem> projectTreeItem = new TreeItem<>(new EntityItem(project));
    root.getChildren().add(projectTreeItem);
}

over_user_time.setCellValueFactory((cellData) -> 
                  cellData.getValue().getValue().userNameProperty());
over_user_btime.setCellValueFactory((cellData) -> 
                  cellData.getValue().getValue().userBillableTimeProperty());
over_user_tmonth.setCellValueFactory((cellData) -> 
                  cellData.getValue().getValue().userTotalTimeProperty());
over_user_project.setCellValueFactory((cellData) -> 
                  cellData.getValue().getValue().projectNameProperty());
over_user_ptime.setCellValueFactory((cellData) -> 
                  cellData.getValue().getValue().projectTotalTimeProperty());

    
        tbv_user_over.setRoot(root);
 }
    
   

    }

   

