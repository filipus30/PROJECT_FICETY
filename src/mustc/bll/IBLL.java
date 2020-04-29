/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mustc.bll;

import java.util.List;
import mustc.be.Project;
import mustc.be.Task;
import mustc.be.Session;
import mustc.be.User;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public interface IBLL {
    
       
// ProjectDBDAO methods    
    public Project addNewProjectToDB(String projectName, int associatedClientID, int phoneNr, float projectRate, int hoursAllocated, boolean isClosed);
    public Project getProject(int projectID);
    public List<Project> getAllProjectIDsAndNamesOfAClient(int clientID);
    public Project editProject (Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed);

    
// TaskDBDAO methods        
    public Task addNewTaskToDB(String taskName, String description, int associatedProjectID);
    public Task getTask(int taskID);
    public List<Task> getAllTaskIDsAndNamesOfAProject(int projectID);
    public Task editTask (Task editedTask, String taskName, String description, int associatedProjectID);
    public void removeTaskFromDB(Task taskToDelete);
  

// SessionDBDAO methods            
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, String startTime, String finishTime);
    public Session getSession(int sessionID);
    public List<Session> getAllSessionsOfATask(int taskID);
    public void removeSessionFromDB(Session sessionToDelete);
  
    
// UserDBDAO methods
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin); 
    public User getUser(int userID);
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin); 
    public void removeUserFromDB(User userToDelete);
    
 
}

