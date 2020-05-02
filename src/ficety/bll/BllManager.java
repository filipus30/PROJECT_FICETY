/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.bll;

import ficety.be.Client;
import ficety.be.LoggedInUser;
import java.util.List;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.dal.DalManager;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class BllManager implements IBLL {

    private boolean debug = false;
// private DalManager dalManager;
    
    public BllManager()
    {
       // dalManager = new DalManager();
    }

    private DalManager dalManager = new DalManager();
    private LoggedInUser lu = LoggedInUser.getInstance();

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
    
    
// TaskDBDAO methods                
    @Override
    public Task addNewTaskToDB(String taskName, Project associatedProject) {
        return dalManager.addNewTaskToDB(taskName, associatedProject);
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
    public void addNewTaskAndSetItRunning(String taskName, Project associatedProject)
    {
        if(lu.getCurrentSession() != null)
        {
            dalManager.addFinishTimeToSession(lu.getCurrentSession(), LocalDateTime.now());
            lu.setCurrentSession(null);
        }
        Task currentTask = addNewTaskToDB(taskName, associatedProject);
        lu.setCurrentTask(currentTask);
        startStopSession();
    }

    
    
// SessionDBDAO methods                    
    @Override
    public void startStopSession() {
        LocalDateTime now = LocalDateTime.now(); //The time right now
        int userID = lu.getId();
        Session thisSession = lu.getCurrentSession();

        if(thisSession == null) //If there is no session running
        {
            
            Task currentTask = lu.getCurrentTask(); //get the selected task
            int currentTaskId = currentTask.getTaskID();
            Session newSession = dalManager.addNewSessionToDB(userID, currentTaskId, now); //Add the session to DB.
            debug("SessionID = " + newSession.getSessionID());//DEBUG MESSAGE
            lu.setCurrentSession(newSession); //Set the current Session active

                       
        }
        else//IF there is a current session runnign then we only need to stop it.
        {
            //Session currentSession = lu.getCurrentSession();
            dalManager.addFinishTimeToSession(thisSession, now); //Add the end time.
            lu.setCurrentSession(null);

        }
        
    }

    @Override
    public List<Session> getAllSessionsOfAUser() {
        int userID = lu.getId();
        return dalManager.getAllSessionsOfAUser(userID);
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
