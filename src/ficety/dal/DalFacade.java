/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.dal;

import ficety.be.Client;
import ficety.be.Coordinates;
import java.sql.SQLException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import ficety.be.Project;
import ficety.be.Task;
import ficety.be.Session;
import ficety.be.User;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public interface DalFacade {

//ClientDBDAO methods
    public Client addNewClientToDB(String clientName,float standardRate,String email);
    public ArrayList<Client> getAllClients();
    public Client editClient (Client editedClient,String name,float standardRate,String email);
    public void deleteClient(Client clientToDelete);
    public ArrayList<Coordinates> getSingleClientForAdminGraph(String startTime,String finishTime,int clientId);
    public ArrayList<Coordinates> getAllClientsForAdminGraph(String startTime,String finishTime);
    public ArrayList<Coordinates> getAllClientsForAdmBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneClientForAdmBar(int clientId, String startTime, String finishTime);
    
       
// ProjectDBDAO methods    
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed);
    public Project editProject (Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr);
    public ArrayList<Project> get3RecentProjectsForUser(int userID);
    public ArrayList<Project> getAllOpenProjects();
    public ArrayList<Project> getAllProjectsForUserTab(int userID);
    public ArrayList<Project> getAllProjects();
    public void deleteProject(Project projectToDelete);
    public ArrayList<Coordinates> getAllProjectsForUserGraph(int userId,String startTime,String finishTime);
    public ArrayList<Coordinates> getSingleProjectForUserGraph(int userId,String startTime,String finishTime,int projectId);
    public ArrayList<Coordinates> getSingleProjectForAdmGraph(String startTime,String finishTime,int projectId);
    public ArrayList<Coordinates> getAllProjectsForAdmBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneProjectForAdmBar(int ProjectId, String startTime, String finishTime);
    public ArrayList<Coordinates> getAllProjectsForUsrBar(int currentUser, String startTime, String finishTime);
    public ArrayList<Coordinates> getOneProjectForUsrBar(int currentUser, int ProjectId, String startTime, String finishTime);
    public ArrayList<Project> getAllProjectsForSingleUser(int userID, String startTime, String finishTime);
    
// TaskDBDAO methods 
    public Task addNewTaskToDB(String taskName, String TaskDesc, boolean isbillable, Project associatedProject);
    public Task addNewTaskToDB(String taskName, boolean isbillable, Project associatedProject);
    public Task editTask (Task editedTask, String taskName, String description, int associatedProjectID,boolean isBillable);
    public void removeTaskFromDB(Task taskToDelete);
    public List<Task> getAllTasksForAdmin();
    public void addTasksToProject(Project p);
    public List<Task> getTasksForUserInfo(int user);
    
  

// SessionDBDAO methods            
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID,String taskName, LocalDateTime startTime);
    public List<Session> getAllSessionsOfAUser(int taskID);
    public Session editSession(Session currentSession, String startTime, String finishTime,int id);
    public void removeSessionFromDB(Session sessionToDelete);
    public Session addFinishTimeToSession(Session currentSession, LocalDateTime finishTime);
  
    
// UserDBDAO methods
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin); 
    public User getUser(int userID);
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin); 
    public void removeUserFromDB(User userToDelete);
    public ArrayList<User> getAllUsers();
    public ArrayList<Coordinates> getAllUsersForAdmBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneUserForAdmBar(int userId, String startTime, String finishTime);
    public ArrayList<User> getAllUsersForOverview(String startTime, String finishTime);
    public User getOneUserForOverview(int userId, String startTime, String finishTime);
    
    
    
    public int checkUserLogin (String email, String password);
     
 
}
