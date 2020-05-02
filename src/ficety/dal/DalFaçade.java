/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.dal;

import ficety.be.Client;
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
public interface DalFaçade {

//ClientDBDAO methods
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email);
    public ArrayList<Client> getAllClients();
    public Client editClient (Client editedClient,String name,float standardRate,String logoImgLocation, String email);
    public void deleteClient(Client clientToDelete);
    
       
// ProjectDBDAO methods    
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed);
    public Project editProject (Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr);
    public ArrayList<Project> get3RecentProjectsForUser(int userID);
    public ArrayList<Project> getAllProjectsForUserTab(int userID);
    public ArrayList<Project> getAllProjects();

    
// TaskDBDAO methods        
    public Task addNewTaskToDB(String taskName, Project associatedProject);
    public Task editTask (Task editedTask, String taskName, String description, int associatedProjectID);
    public void removeTaskFromDB(Task taskToDelete);
    public void addTasksToProject(Project p);
    public List<Task> getTasksForUserInfo(int user);
  

// SessionDBDAO methods            
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, LocalDateTime startTime);
    public List<Session> getAllSessionsOfAUser(int taskID);
    public void removeSessionFromDB(Session sessionToDelete);
    public void addFinishTimeToSession(Session currentSession, LocalDateTime finishTime);
  
    
// UserDBDAO methods
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin); 
    public User getUser(int userID);
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin); 
    public void removeUserFromDB(User userToDelete);
    public ArrayList<User> getAllUsers();
    
    
    
    
    public int checkUserLogin (String email, String password);
     
 
}
