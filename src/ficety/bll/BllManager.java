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
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email)
    {
        return dalManager.addNewClientToDB(clientName, standardRate, logoImgLocation, email);
    }
    
    @Override
    public ArrayList<Client> getAllClients()
    {
        return dalManager.getAllClients();
    }
  
    @Override
    public Client editClient (Client editedClient,String name,float standardRate,String logoImgLocation, String email)
    {
        return dalManager.editClient(editedClient, name, standardRate, logoImgLocation, email);
    }
    
    @Override
    public void deleteClient(Client clientToDelete)
    {
        dalManager.deleteClient(clientToDelete);
    }
  
    
 // ProjectDBDAO methods           
    @Override
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed) {
        return dalManager.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);
    }
    
 @Override
    public ArrayList<Coordinates> getSingleProjectForUserBar(int userId, String startTime, String finishTime, int projectId) {
      return dalManager.getSingleProjectForUserBar(userId, startTime, finishTime, projectId);
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
    public ArrayList<Project> getAllProjectsForUserTab()
    {
        int userId = lu.getId();
        return dalManager.getAllProjectsForUserTab(userId);
    }

    @Override
    public ArrayList<Project> getAllProjects()
    {
        return dalManager.getAllProjects();
    }
    
    @Override 
    public void deleteProject(Project projectToDelete)
    {
        dalManager.deleteProject(projectToDelete);
    }
    @Override
    public ArrayList<Coordinates> getAllProjectsForUserBar(int userId, String startTime, String finishTime) {
     return dalManager.getAllProjectsForUserBar(userId, startTime, finishTime);
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
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID) {
        return dalManager.editTask(editedTask, taskName, description, associatedProjectID);
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
    
    private void debug(String msg)
    {
        if(debug==true)
        {
            System.out.println(msg);
        }
    }

   

    
}
