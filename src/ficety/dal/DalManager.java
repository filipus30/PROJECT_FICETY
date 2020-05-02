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
public class DalManager implements DalFaçade {
    private ProjectDBDAO projectDBDao = new ProjectDBDAO();
    private TaskDBDAO taskDBDao = new TaskDBDAO();
    private SessionDBDAO sessionDBDao = new SessionDBDAO();
    private UserDBDAO userDBDao = new UserDBDAO();
    private ClientDBDAO clientDBDao = new ClientDBDAO();
    
  public DalManager()
  {
       
 }
//ClientDBDAO methods
    @Override
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email)
    {
        return clientDBDao.addNewClientToDB(clientName, standardRate, logoImgLocation, email);
    }
    
    @Override
    public ArrayList<Client> getAllClients()
    {
        return clientDBDao.getAllClients();
    }
  
    @Override
    public Client editClient (Client editedClient,String name,float standardRate,String logoImgLocation, String email)
    {
        return clientDBDao.editClient(editedClient, name, standardRate, logoImgLocation, email);
    }
    
    @Override
    public void deleteClient(Client clientToDelete)
    {
        clientDBDao.deleteClient(clientToDelete);
    }
  
 // ProjectDBDAO methods       
    @Override
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed) {

        return projectDBDao.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);

    }
    
    
    @Override
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr) {
        return projectDBDao.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed, phoneNr);
    }
    
    @Override
    public ArrayList<Project> get3RecentProjectsForUser(int userID)
    {
        return projectDBDao.get3RecentProjectsForUser(userID);
    }
    
    @Override
    public ArrayList<Project> getAllProjectsForUserTab (int userID)
    {
        return projectDBDao.getAllProjectsForUserTab(userID);
    }
    
    @Override
    public ArrayList<Project> getAllProjects()
    {
        return projectDBDao.getAllProjects();
    }
    
// TaskDBDAO methods            
    @Override
    public Task addNewTaskToDB(String taskName, Project associatedProject) {
        try {
            return taskDBDao.addNewTaskToDB(taskName, associatedProject);
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
    
    @Override
    public void addTasksToProject(Project p) {
        taskDBDao.addTasksToProject(p);
    }

    @Override
    public List<Task> getTasksForUserInfo(int user)
    {
        return taskDBDao.getTasksForUserInfo(user);
    }
    
// SessionDBDAO methods                
    @Override
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, LocalDateTime startTime) {
        return sessionDBDao.addNewSessionToDB(associatedUserID, associatedTaskID, startTime);
    }
    
    @Override
    public List<Session> getAllSessionsOfAUser(int userID) {
        
        return sessionDBDao.getAllSessionsOfAUser(userID);

    }
    
    @Override
    public void removeSessionFromDB(Session sessionToDelete) {
        sessionDBDao.removeSessionFromDB(sessionToDelete);
    }
    
    @Override
    public void addFinishTimeToSession(Session currentSession, LocalDateTime finishTime)
    {
        try {
            sessionDBDao.addFinishTimeToSession(currentSession, finishTime);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

// UserDBDAO methods    
    @Override
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin) {
        return userDBDao.addNewUserToDB(userName, email, password, salary, isAdmin);
    }
    
    @Override
    public User getUser(int userID) {
        return userDBDao.getUser(userID);
    }
    
    @Override
    public User editUser(User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin) {
        return userDBDao.editUser(userToEdit, userName, email, password, salary, isAdmin);
    }
    
    @Override
    public void removeUserFromDB(User userToDelete) {
        userDBDao.removeUserFromDB(userToDelete);
    }

    @Override
    public int checkUserLogin(String email, String password) {
        try {
           return userDBDao.checkUserLogin(email, password);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 4;
    }

    @Override
    public ArrayList<User> getAllUsers()
    {
        return userDBDao.getAllUsers();
    }

    
}
