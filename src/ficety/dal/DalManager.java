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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class DalManager implements DalFaçade {
    private ProjectDBDAO projectDBDao ;
    private TaskDBDAO taskDBDao ;
    private SessionDBDAO sessionDBDao ;
    private UserDBDAO userDBDao ;
    private ClientDBDAO clientDBDao ;
    
  public DalManager()
  {
    projectDBDao = new ProjectDBDAO();
    taskDBDao = new TaskDBDAO();
    sessionDBDao = new SessionDBDAO();
    userDBDao = new UserDBDAO();
    clientDBDao = new ClientDBDAO();
 }
//ClientDBDAO methods
    @Override
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email)
    {
        return clientDBDao.addNewClientToDB(clientName, standardRate, logoImgLocation, email);
    }
    
    @Override
    public ArrayList<Coordinates> getAllClientsForAdminBar(String startTime, String finishTime) {
      return clientDBDao.getAllClientsForAdminBar(startTime, finishTime);
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
    
  @Override
    public ArrayList<Coordinates> getSingleClientForAdminBar(String startTime, String finishTime, int clientId) 
    {
        return clientDBDao.getSingleClientForAdminBar(startTime, finishTime, clientId);
    }
 // ProjectDBDAO methods       
    @Override
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed) {

        return projectDBDao.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);

    }
    
     @Override
    public ArrayList<Coordinates> getSingleProjectForUserBar(int userId, String startTime, String finishTime, int projectId) {
        return projectDBDao.getSingleProjectForUserBar(userId, startTime, finishTime, projectId);
    }
    
    @Override
    public ArrayList<Coordinates> getAllProjectsForUserBar(int userId, String startTime, String finishTime) {
        return projectDBDao.getAllProjectsForUserBar(userId, startTime, finishTime);
    }
   @Override
    public ArrayList<Coordinates> getSingleProjectForAdmBar(String startTime, String finishTime, int projectId) {
       return projectDBDao.getSingleProjectForAdmBar(startTime, finishTime, projectId);
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
    
    @Override
    public void deleteProject(Project projectToDelete)
    {
        projectDBDao.deleteProject(projectToDelete);
    }
    
// TaskDBDAO methods            
    @Override
    public Task addNewTaskToDB(String taskName, String taskDesc, boolean isBillable, Project associatedProject) {
        return taskDBDao.addNewTaskToDB(taskName, taskDesc, isBillable, associatedProject);
    }
    
    @Override
    public Task addNewTaskToDB(String taskName, boolean isBillable, Project associatedProject) {
        return taskDBDao.addNewTaskToDB(taskName, isBillable, associatedProject);
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
    public ArrayList<Task> getAllTasksForAdmin()
    {
        return taskDBDao.getAllTasksForAdmin();
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
    public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, String taskName, LocalDateTime startTime) {
        return sessionDBDao.addNewSessionToDB(associatedUserID, associatedTaskID, taskName, startTime);
    }
    
    @Override
    public List<Session> getAllSessionsOfAUser(int userID) {
        
        return sessionDBDao.getAllSessionsOfAUser(userID);

    }
    
    @Override
    public Session editSession(Session currentSession, String startTime, String finishTime,int id)
    {
        try {
            return sessionDBDao.editSession(currentSession, startTime, finishTime,id);
        } catch (ParseException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void removeSessionFromDB(Session sessionToDelete) {
        sessionDBDao.removeSessionFromDB(sessionToDelete);
    }
    
    @Override
    public Session addFinishTimeToSession(Session currentSession, LocalDateTime finishTime)
    {
        try {
            return sessionDBDao.addFinishTimeToSession(currentSession, finishTime);
        } catch (SQLException ex) {
            Logger.getLogger(DalManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
