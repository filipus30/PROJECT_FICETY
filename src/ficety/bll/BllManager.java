/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.bll;

import ficety.be.LoggedInUser;
import java.util.List;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.dal.DalManager;
import java.time.LocalDateTime;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class BllManager implements IBLL {
    private DalManager dalManager = new DalManager();
    private LoggedInUser lu = LoggedInUser.getInstance();
   
    
 // ProjectDBDAO methods           
    @Override
    public Project addNewProjectToDB(String projectName, int associatedClientID, int phoneNr, float projectRate, int hoursAllocated, boolean isClosed) {
        return dalManager.addNewProjectToDB(projectName, associatedClientID, phoneNr, projectRate, hoursAllocated, isClosed);
    }

    @Override
    public Project getProject(int projectID) {
        return dalManager.getProject(projectID);
    }

    @Override
    public List<Project> getAllProjectIDsAndNamesOfAClient(int clientID) {
        return dalManager.getAllProjectIDsAndNamesOfAClient(clientID);
    }

    @Override
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed) {
        return dalManager.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed);
    }

    
    
// TaskDBDAO methods                
    @Override
    public Task addNewTaskToDB(String taskName, String description, int associatedProjectID) {
        return dalManager.addNewTaskToDB(taskName, description, associatedProjectID);
    }

    @Override
    public Task getTask(int taskID) {
        return dalManager.getTask(taskID);
    }

    @Override
    public List<Task> getAllTaskIDsAndNamesOfAProject(int projectID) {
        return dalManager.getAllTaskIDsAndNamesOfAProject(projectID);
    }

    @Override
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID) {
        return dalManager.editTask(editedTask, taskName, description, associatedProjectID);
    }

    @Override
    public void removeTaskFromDB(Task taskToDelete) {
        dalManager.removeTaskFromDB(taskToDelete);
    }

    
    
// SessionDBDAO methods                    
    @Override
    public void startStopSession() {
        LocalDateTime now = LocalDateTime.now();
        int userID = lu.getId();
        Session thisSession = lu.getCurrentSession();

        if(thisSession == null)
        {
            
            Task currentTask = lu.getCurrentTask();
            int currentTaskId = currentTask.getTaskId();
            Session newSession = dalManager.addNewSessionToDB(userID, currentTaskId, now);
            System.out.println("SessionID = " + newSession.getSessionId());
            lu.setCurrentSession(newSession);

                       
        }
        else
        {
            //Session currentSession = lu.getCurrentSession();
            dalManager.addFinishTimeToSession(thisSession, now);
            //lu.setCurrentSession(null);

        }
        
    }

    @Override
    public List<Session> getAllSessionsOfATask(int taskID) {
        return dalManager.getAllSessionsOfATask(taskID);
    }

    @Override
    public void removeSessionFromDB(Session sessionToDelete) {
        dalManager.removeSessionFromDB(sessionToDelete);
    }

    
    
// UserDBDAO methods        
    @Override
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin) {
        return dalManager.addNewUserToDB(userName, email, password, salary, isAdmin);
    }

    @Override
    public User getUser(int userID) {
        return dalManager.getUser(userID);
    }

    @Override
    public User editUser(User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin) {
        return dalManager.editUser(userToEdit, userName, email, password, salary, isAdmin);
    }

    @Override
    public void removeUserFromDB(User userToDelete) {
        dalManager.removeUserFromDB(userToDelete);
    }
    
}
