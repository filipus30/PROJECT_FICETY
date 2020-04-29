/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.dal;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ficety.be.Project;
import ficety.be.Task;
import ficety.be.Session;
import ficety.be.User;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class DalManager implements DalFaçade {
    private ProjectDBDAO projectDBDao;
    private TaskDBDAO taskDBDao;
    private SessionDBDAO sessionDBDao;
    private UserDBDAO userDBDao;
    
  
    
 // ProjectDBDAO methods       
    @Override
    public Project addNewProjectToDB(String projectName, int associatedClientID, int phoneNr, float projectRate, int hoursAllocated, boolean isClosed) {
        try {
            return projectDBDao.addNewProjectToDB(projectName, associatedClientID, phoneNr, projectRate, hoursAllocated, isClosed);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Project getProject(int projectID) {
        try {
            return projectDBDao.getProject(projectID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public List<Project> getAllProjectIDsAndNamesOfAClient(int clientID) {
        try {
            return projectDBDao.getAllProjectIDsAndNamesOfAClient(clientID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed) {
        return projectDBDao.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed);
    }
    
    
    
// TaskDBDAO methods            
    @Override
    public Task addNewTaskToDB(String taskName, String description, int associatedProjectID) {
        try {
            return taskDBDao.addNewTaskToDB(taskName, description, associatedProjectID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Task getTask(int taskID) {
        try {
            return taskDBDao.getTask(taskID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public List<Task> getAllTaskIDsAndNamesOfAProject(int projectID) {
        try {
            return taskDBDao.getAllTaskIDsAndNamesOfAProject(projectID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID) {
        return taskDBDao.editTask(editedTask, taskName, description, associatedProjectID);
    }
    
    @Override
    public void removeTaskFromDB(Task taskToDelete) {
        taskDBDao.removeTaskFromDB(taskToDelete);
    }

    
    
// SessionDBDAO methods                
    @Override
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, String startTime, String finishTime) {
        return sessionDBDao.addNewSessionToDB(associatedUserID, associatedTaskID, startTime, finishTime);
    }
    
    @Override
    public Session getSession(int sessionID) {
        try {
            return sessionDBDao.getSession(sessionID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Session> getAllSessionsOfATask(int taskID) {
        try {
            return sessionDBDao.getAllSessionsOfATask(taskID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void removeSessionFromDB(Session sessionToDelete) {
        sessionDBDao.removeSessionFromDB(sessionToDelete);
    }
    
    

// UserDBDAO methods    
    @Override
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin) {
        return userDBDao.addNewUserToDB(userName, email, password, salary, isAdmin);
    }
    
    @Override
    public User getUser(int userID) {
        try {
            return userDBDao.getUser(userID);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public User editUser(User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin) {
        return userDBDao.editUser(userToEdit, userName, email, password, salary, isAdmin);
    }
    
    @Override
    public void removeUserFromDB(User userToDelete) {
        userDBDao.removeUserFromDB(userToDelete);
    }

    
}
