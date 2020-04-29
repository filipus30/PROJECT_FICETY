/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.dal;

import java.sql.SQLException;
import java.util.List;
import ficety.be.Project;
import ficety.be.Task;
import ficety.be.Session;
import ficety.be.User;
import java.time.LocalDateTime;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public interface DalFaçade {
    
       
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
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, LocalDateTime startTime);
    public List<Session> getAllSessionsOfATask(int taskID);
    public void removeSessionFromDB(Session sessionToDelete);
    public void addFinishTimeToSession(Session currentSession, LocalDateTime finishTime);
  
    
// UserDBDAO methods
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin); 
    public User getUser(int userID);
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin); 
    public void removeUserFromDB(User userToDelete);
    
 
}
