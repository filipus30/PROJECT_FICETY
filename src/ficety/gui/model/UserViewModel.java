/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.model;

import ficety.be.Client;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.bll.BllManager;
import ficety.bll.Exporter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.util.Pair;

/**
 *
 * @author Trigger
 */
public class UserViewModel {
    private BllManager BllM = new BllManager();
    private Exporter ex = new Exporter();
    
    //CLIENT related
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email)
    {
        return BllM.addNewClientToDB(clientName, standardRate, logoImgLocation, email);
    }
    
    public ArrayList<Client> getAllClients()
    {
        return BllM.getAllClients();
    }
  
    public Client editClient (Client editedClient,String name,float standardRate,String logoImgLocation, String email)
    {
        return BllM.editClient(editedClient, name, standardRate, logoImgLocation, email);
    }
    
    public void deleteClient(Client clientToDelete)
    {
        BllM.deleteClient(clientToDelete);
    }
    
    //PROJECT Related
    
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed)
    {
        return BllM.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);
    }
    
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr) {
        return BllM.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed, phoneNr);
    }
    
    public ArrayList<Project> get3RecentProjects()
    {
        return BllM.get3RecentProjectsForUser();
    }
    
    public ArrayList<Project> getAllProjectsForUserTab()
    {
        return BllM.getAllProjectsForUserTab();
    }
    
    public ArrayList<Project> getAllProjects()
    {
        return BllM.getAllProjects();
    }
    
    public void deleteProject(Project projectToDelete)
    {
        BllM.deleteProject(projectToDelete);
    }
    
    //TASK Related
    
    public Task addNewTaskToDB(String taskName, String taskDesc, boolean isBillable, Project associatedProject) {
        return BllM.addNewTaskToDB(taskName, taskDesc, isBillable, associatedProject);
    }
    
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID) {
        return BllM.editTask(editedTask, taskName, description, associatedProjectID);
    }
    
    public void removeTaskFromDB(Task taskToDelete) {
        BllM.removeTaskFromDB(taskToDelete);
    }
    
    public List<Task> getTasksForUserInfo()
    {
        return BllM.getTasksForUserInfo();
    }
    
    public List<Task> getAllTasksForAdmin()
    {
        return BllM.getAllTasksForAdmin();
    }
    
    public Pair <Task, Session> addNewTaskAndSetItRunning(String taskName, boolean isBillable, Project associatedProject)
    {
        return BllM.addNewTaskAndSetItRunning(taskName, isBillable, associatedProject);
    }
    
    
    //USER Related
    public ArrayList<User> getAllUsers()
    {
        return BllM.getAllUsers();
    }
    
    public User editUser(User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin)
    {
        return BllM.editUser(userToEdit, userName, email, password, salary, isAdmin);
    }
    
    public User createUser(String userName, String email, String password, float salary, boolean isAdmin)
    {
        return BllM.addNewUserToDB(userName, email, password, salary, isAdmin);
    }
    
    
    //SESSION Related
    public Session startStopSession()
    {
        return BllM.startStopSession();
    }
    
     public List<Session> getAllSessionsOfAUser() {
        return BllM.getAllSessionsOfAUser();
    }
     
    public Session editSession(Session currentSession, String startTime, String finishTime,int id)
    {
        return BllM.editSession(currentSession, startTime, finishTime,id);
    }
     
    public void removeSessionFromDB(Session sessionToDelete)
    {
        BllM.removeSessionFromDB(sessionToDelete);
    }
    
      public void export(TableView<?> tableView,String exportName)
      {
          ex.export(tableView, exportName);
      }
        
}
