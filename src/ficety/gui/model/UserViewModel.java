/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.model;

import ficety.be.Client;
import ficety.be.Coordinates;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.bll.BllManager;
import ficety.bll.Exporter;
import ficety.bll.Validator;
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
    private Validator val = new Validator();
    
    //Validators
     public boolean isNumber(String text)
     {
         return val.isNumber(text);
     }
     public boolean isAlpha(String name)
     {
         return val.isAlpha(name);
     }
     public  boolean isValidDate(String text)
     {
         return val.isValidDate(text);
     }
     public boolean isStringFloat(String s)
     {
         return val.isStringFloat(s);
     }
    
    
    //CLIENT related
    public Client addNewClientToDB(String clientName,float standardRate,String email)
    {
        return BllM.addNewClientToDB(clientName, standardRate, email);
    }
    
    public ArrayList<Client> getAllClients()
    {
        return BllM.getAllClients();
    }
    public ArrayList<Coordinates> getAllClientsForAdminGraph(String startTime,String finishTime)
    {
        return BllM.getAllClientsForAdminGraph(startTime, finishTime);
    }
  
    public ArrayList<Coordinates> getSingleClientForAdminGraph(String startTime, String finishTime, int clientId) 
    {
       return BllM.getSingleClientForAdminGraph(startTime, finishTime, clientId);
    }
    public Client editClient (Client editedClient,String name,float standardRate,String email)
    {
        return BllM.editClient(editedClient, name, standardRate, email);
    }
    
    public void deleteClient(Client clientToDelete)
    {
        BllM.deleteClient(clientToDelete);
    }
    
    public ArrayList<Coordinates> getAllClientsForAdmBar(String startTime, String finishTime)
    {
        return BllM.getAllClientsForAdmBar(startTime, finishTime);
    }
    
    public ArrayList<Coordinates> getOneClientForAdmBar(Client client, String startTime, String finishTime)
    {
        
        return BllM.getOneClientForAdmBar(client, startTime, finishTime);
    }
    

    //PROJECT Related
    
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed)
    {
        return BllM.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);
    }
     public ArrayList<Coordinates> getSingleProjectForAdmGraph(String startTime,String finishTime,int projectId)
     {
         return BllM.getSingleProjectForAdmGraph(startTime, finishTime, projectId);
     }
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr) {
        return BllM.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed, phoneNr);
    }
    
    public ArrayList<Project> get3RecentProjects()
    {
        return BllM.get3RecentProjectsForUser();
    }
    
    public ArrayList<Project> getAllOpenProjects()
    {
        
        return BllM.getAllOpenProjects();
    }
    
    public ArrayList<Coordinates> getSingleProjectForUserGraph(int userId,String startTime,String finishTime,int projectId)
    {
        return BllM.getSingleProjectForUserGraph(userId, startTime, finishTime, projectId);
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
    
   public ArrayList<Coordinates> getAllProjectsForUserGraph(int userId,String startTime,String finishTime)
   {
      return BllM.getAllProjectsForUserGraph(userId, startTime, finishTime);
   }
   
   
    public ArrayList<Coordinates> getAllProjectsForAdmBar(String startTime, String finishTime)
    {
        return BllM.getAllProjectsForAdmBar(startTime, finishTime);
    }
    

    public ArrayList<Coordinates> getOneProjectForAdmBar(Project project, String startTime, String finishTime)
    {
        return BllM.getOneProjectForAdmBar(project, startTime, finishTime);
    }
    
    public ArrayList<Coordinates> getAllProjectsForUsrBar(String startTime, String finishTime)
    {
        return BllM.getAllProjectsForUsrBar(startTime, finishTime);
    }
    
    public ArrayList<Coordinates> getOneProjectForUsrBar(Project project, String startTime, String finishTime)
    {
        return BllM.getOneProjectForUsrBar(project, startTime, finishTime);
    }
    
    public ArrayList<Project> getAllProjectsForSingleUser(int userID, String startTime, String finishTime)
    {
        return BllM.getAllProjectsForSingleUser(userID, startTime, finishTime);
    }
   
   
    //TASK Related
    
    public Task addNewTaskToDB(String taskName, String taskDesc, boolean isBillable, Project associatedProject) {
        return BllM.addNewTaskToDB(taskName, taskDesc, isBillable, associatedProject);
    }
    
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID,boolean isBillable) {
        return BllM.editTask(editedTask, taskName, description, associatedProjectID,isBillable);
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
    public void removeUser(User user)
    {
        BllM.removeUserFromDB(user);
    }
    
    public User editUser(User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin)
    {
        return BllM.editUser(userToEdit, userName, email, password, salary, isAdmin);
    }
    
    public User createUser(String userName, String email, String password, float salary, boolean isAdmin)
    {
        return BllM.addNewUserToDB(userName, email, password, salary, isAdmin);
    }
    
    public ArrayList<Coordinates> getAllUsersForAdmBar(String startTime, String finishTime)
    {
        return BllM.getAllUsersForAdmBar(startTime, finishTime);
    }
    
    public ArrayList<Coordinates> getOneUserForAdmBar(User user, String startTime, String finishTime)
    {
        return BllM.getOneUserForAdmBar(user, startTime, finishTime);
    }
    
    public ArrayList<User> getAllUsersForOverview(String startTime, String finishTime)
    {
        return BllM.getAllUsersForOverview(startTime, finishTime);
    }
    
    public User getLoggedInUserForOverview(String startTime, String finishTime)
    {
        return BllM.getOneUserForOverview(startTime, finishTime);
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
