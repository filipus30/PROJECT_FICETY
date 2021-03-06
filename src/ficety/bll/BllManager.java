/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.bll;

import ficety.be.Client;
import ficety.be.Coordinates;
import ficety.be.LoggedInUser;
import java.util.List;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.dal.DalManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class BllManager implements IBLL {

    private boolean debug = false;
// private DalManager dalManager;
     private DalManager dalManager ;
    private LoggedInUser lu;
    public BllManager()
    {
        dalManager = new DalManager();
    lu = LoggedInUser.getInstance();
    }

    

//CLientDBDAO methods
    @Override
    public Client addNewClientToDB(String clientName,float standardRate,String email)
    {
        return dalManager.addNewClientToDB(clientName, standardRate, email);
    }
    @Override
    public ArrayList<Coordinates> getAllClientsForAdminGraph(String startTime, String finishTime) {
      return dalManager.getAllClientsForAdminGraph(startTime, finishTime);
    }
     @Override
    public ArrayList<Coordinates> getSingleClientForAdminGraph(String startTime, String finishTime, int clientId) {
      return dalManager.getSingleClientForAdminGraph(startTime, finishTime, clientId);
    }
    @Override
    public ArrayList<Client> getAllClients()
    {
        return dalManager.getAllClients();
    }
  
    @Override
    public Client editClient (Client editedClient,String name,float standardRate, String email)
    {
        return dalManager.editClient(editedClient, name, standardRate, email);
    }
    
    @Override
    public void deleteClient(Client clientToDelete)
    {
        dalManager.deleteClient(clientToDelete);
    }
    
    @Override
    public ArrayList<Coordinates> getAllClientsForAdmBar(String startTime, String finishTime)
    {
        return dalManager.getAllClientsForAdmBar(startTime, finishTime);
    }
    
    @Override
    public ArrayList<Coordinates> getOneClientForAdmBar(Client client, String startTime, String finishTime)
    {
        int clientId = client.getId();
        return dalManager.getOneClientForAdmBar(clientId, startTime, finishTime);
    }
  
    
 // ProjectDBDAO methods           
    @Override
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed) {
        return dalManager.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);
    }
    
    @Override
    public ArrayList<Coordinates> getSingleProjectForAdmGraph(String startTime, String finishTime, int projectId) {
        return dalManager.getSingleProjectForAdmGraph(startTime, finishTime, projectId);
    }

    @Override
    public ArrayList<Coordinates> getSingleProjectForUserGraph(int userId, String startTime, String finishTime, int projectId) {
      return dalManager.getSingleProjectForUserGraph(userId, startTime, finishTime, projectId);
    }
    @Override
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr) {
        return dalManager.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed, phoneNr);
    }
    
    @Override
    public ArrayList<Project> get3RecentProjectsForUser()
    {
        int userId = lu.getId();
        ArrayList<Project> projects = dalManager.get3RecentProjectsForUser(userId);
        for(Project p : projects)
        {
            dalManager.addTasksToProject(p);
        }
        return projects;
    }
    
    @Override
    public ArrayList<Project> getAllOpenProjects()
    {
        ArrayList<Project> projects = dalManager.getAllOpenProjects();
         for(Project p : projects)
        {
            dalManager.addTasksToProject(p);
        }
        return projects;
    }
    
    @Override
    public ArrayList<Project> getAllProjectsForUserTab()
    {
        int userId = lu.getId();
        return dalManager.getAllProjectsForUserTab(userId);
    }

    @Override
    public ArrayList<Project> getAllProjects()
    {
         ArrayList<Project> projects = dalManager.getAllProjects();
         for(Project p : projects)
        {
            dalManager.addTasksToProject(p);
        }
        return projects;
    }
    
    @Override 
    public void deleteProject(Project projectToDelete)
    {
        dalManager.deleteProject(projectToDelete);
    }
    @Override
    public ArrayList<Coordinates> getAllProjectsForUserGraph(int userId, String startTime, String finishTime) {
     return dalManager.getAllProjectsForUserGraph(userId, startTime, finishTime);
    }
    
    @Override
    public ArrayList<Coordinates> getAllProjectsForAdmBar(String startTime, String finishTime)
    {
        return dalManager.getAllProjectsForAdmBar(startTime, finishTime);
    }
    
    @Override
    public ArrayList<Coordinates> getOneProjectForAdmBar(Project project, String startTime, String finishTime)
    {
        int projectId = project.getId();
        return dalManager.getOneProjectForAdmBar(projectId, startTime, finishTime);
    }
    
    @Override
    public ArrayList<Coordinates> getAllProjectsForUsrBar(String startTime, String finishTime)
    {
        int currentUser = lu.getId();
        return dalManager.getAllProjectsForUsrBar(currentUser, startTime, finishTime);
    }
    
    @Override
    public ArrayList<Coordinates> getOneProjectForUsrBar(Project p, String startTime, String finishTime)
    {
        int currentUser = lu.getId();
        int projectId = p.getId();
        return dalManager.getOneProjectForUsrBar(currentUser, projectId, startTime, finishTime);
    }
    
    @Override
    public ArrayList<Project> getAllProjectsForSingleUser(int userId, String startTime, String finishTime)
    {
        return dalManager.getAllProjectsForSingleUser(userId, startTime, finishTime);
    }
    
    
// TaskDBDAO methods                
    @Override
    public Task addNewTaskToDB(String taskName, String taskDesc, boolean isBillable, Project associatedProject) {
        return dalManager.addNewTaskToDB(taskName, taskDesc, isBillable, associatedProject);
    }

    @Override
    public Task addNewTaskToDB(String taskName, boolean isBillable, Project associatedProject) {
        return dalManager.addNewTaskToDB(taskName, isBillable, associatedProject);
    }
    
    @Override
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID,boolean isBillable) {
        return dalManager.editTask(editedTask, taskName, description, associatedProjectID,isBillable);
    }

    @Override
    public void removeTaskFromDB(Task taskToDelete) {
        dalManager.removeTaskFromDB(taskToDelete);
    }
    
    @Override
    public List<Task> getTasksForUserInfo()
    {
        int user = lu.getId();
        return dalManager.getTasksForUserInfo(user);
    }
    
    @Override
    public List<Task> getAllTasksForAdmin()
    {
        return dalManager.getAllTasksForAdmin();
    }        
    
    @Override
    public Pair<Task, Session> addNewTaskAndSetItRunning(String taskName, boolean isBillable, Project associatedProject)
    {
        Session tempSession;
        if(lu.getCurrentSession() != null) //If we have a sesssion in progress.
        {
            tempSession = dalManager.addFinishTimeToSession(lu.getCurrentSession(), LocalDateTime.now());
            lu.setCurrentSession(null);          
        }
        
        Task currentTask = addNewTaskToDB(taskName, isBillable, associatedProject);
        lu.setCurrentTask(currentTask);
        tempSession = startStopSession();
        Pair<Task, Session> temp = new Pair(currentTask, tempSession);
        return temp;
    }

    
    
// SessionDBDAO methods                    
    @Override
    public Session startStopSession() {
        LocalDateTime now = LocalDateTime.now(); //The time right now
        
        int userID = lu.getId();
        Session thisSession = lu.getCurrentSession();

        if(thisSession == null) //If there is no session running
        {
            
            Task currentTask = lu.getCurrentTask(); //get the selected task
            int currentTaskId = currentTask.getTaskID();
            String taskName = currentTask.getTaskName();
            Session newSession = dalManager.addNewSessionToDB(userID, currentTaskId, taskName, now); //Add the session to DB.
            debug("SessionID = " + newSession.getSessionID());//DEBUG MESSAGE
            lu.setCurrentSession(newSession); //Set the current Session active
            return newSession;

                       
        }
        else//IF there is a current session runnign then we only need to stop it.
        {
            //Session currentSession = lu.getCurrentSession();
            lu.setCurrentSession(null);
            return dalManager.addFinishTimeToSession(thisSession, now); //Add the end time.
            

        }
        
    }

    @Override
    public List<Session> getAllSessionsOfAUser() {
        int userID = lu.getId();
        return dalManager.getAllSessionsOfAUser(userID);
    }

    @Override
    public Session editSession(Session currentSession, String startTime, String finishTime,int id)
    {
        return dalManager.editSession(currentSession, startTime, finishTime,id);
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
    @Override
    public int checkUserLogin (String email, String password) {
        return dalManager.checkUserLogin(email, password);
    }
    
    @Override
    public ArrayList<User> getAllUsers()
    {
        return dalManager.getAllUsers();
    }
    
    @Override
    public ArrayList<Coordinates> getAllUsersForAdmBar(String startTime, String finishTime)
    {
        return dalManager.getAllUsersForAdmBar(startTime, finishTime);
    }
    
    @Override
    public ArrayList<Coordinates> getOneUserForAdmBar(User user, String startTime, String finishTime)
    {
        int userId = user.getUserId();
        return dalManager.getOneUserForAdmBar(userId, startTime, finishTime);
    }
    
    @Override
    public ArrayList<User> getAllUsersForOverview(String startTime, String finishTime)
    {
        return dalManager.getAllUsersForOverview(startTime, finishTime);
    }
    
    @Override
    public User getOneUserForOverview(String startTime, String finishTime)
    {
        int userId = lu.getId();
        return dalManager.getOneUserForOverview(userId, startTime, finishTime);
    }
    
    
    
    
    
    private void debug(String msg)
    {
        if(debug==true)
        {
            System.out.println(msg);
        }
    }

  
   

   

   

    
}
