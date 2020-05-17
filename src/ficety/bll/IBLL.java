/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.bll;

import ficety.be.Client;
import ficety.be.Coordinates;
import java.util.List;
import ficety.be.Project;
import ficety.be.Task;
import ficety.be.Session;
import ficety.be.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public interface IBLL {

//ClientDBDAO methods
    public Client addNewClientToDB(String clientName,float standardRate,String email);
    public ArrayList<Client> getAllClients();
    public Client editClient (Client editedClient,String name,float standardRate,String email);
    public void deleteClient(Client clientToDelete);
    public ArrayList<Coordinates> getSingleClientForAdminGraph(String startTime,String finishTime,int clientId);
    public ArrayList<Coordinates> getAllClientsForAdminGraph(String startTime,String finishTime);
    public ArrayList<Coordinates> getAllClientsForAdmBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneClientForAdmBar(Client client, String startTime, String finishTime);
    
       
// ProjectDBDAO methods    
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed);
    public Project editProject (Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr);
    public ArrayList<Project> get3RecentProjectsForUser();
    public ArrayList<Project> getAllProjectsForUserTab();
    public ArrayList<Project> getAllProjects();
    public void deleteProject(Project projectToDelete);
    public ArrayList<Coordinates> getAllProjectsForUserGraph(int userId,String startTime,String finishTime);
    public ArrayList<Coordinates> getSingleProjectForUserGraph(int userId,String startTime,String finishTime,int projectId);
    public ArrayList<Coordinates> getSingleProjectForAdmGraph(String startTime,String finishTime,int projectId);
    public ArrayList<Coordinates> getAllProjectsForAdmBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneProjectForAdmBar(Project project, String startTime, String finishTime);
    public ArrayList<Coordinates> getAllProjectsForUsrBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneProjectForUsrBar(Project project, String startTime, String finishTime); 
    
// TaskDBDAO methods        
    public Task addNewTaskToDB(String taskName, String taskDesc, boolean isBillable, Project associatedProject);
    public Task addNewTaskToDB(String taskName, boolean isBillable, Project associatedProject);
    public Task editTask (Task editedTask, String taskName, String description, int associatedProjectID);
    public void removeTaskFromDB(Task taskToDelete);
    public Pair<Task, Session> addNewTaskAndSetItRunning(String taskName, boolean isBillable, Project associatedProject);
    public List<Task> getTasksForUserInfo();
    public List<Task> getAllTasksForAdmin();
  

// SessionDBDAO methods            
    public Session startStopSession();
    public List<Session> getAllSessionsOfAUser();
    public Session editSession(Session currentSession, String startTime, String finishTime, int id);
    public void removeSessionFromDB(Session sessionToDelete);
  
    
// UserDBDAO methods
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin); 
    public User getUser(int userID);
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin); 
    public void removeUserFromDB(User userToDelete);
    public ArrayList<User> getAllUsers();
    public ArrayList<Coordinates> getAllUsersForAdmBar(String startTime, String finishTime);
    public ArrayList<Coordinates> getOneUserForAdmBar(User user, String startTime, String finishTime);
    
    
    
   
    public int checkUserLogin (String email, String password);
    
 
}

