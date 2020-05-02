/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.model;

import ficety.be.Client;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import ficety.bll.BllManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trigger
 */
public class UserViewModel {
    private BllManager BllM = new BllManager();
    
    //CLIENT related
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email)
    {
        return BllM.addNewClientToDB(clientName, standardRate, logoImgLocation, email);
    }
    
    public ArrayList<Client> getAllClients()
    {
        return BllM.getAllClients();
    }
  
    public Client editClient (Client editedClient,String name,float standardRate,String logoImgLocation, String email)
    {
        return BllM.editClient(editedClient, name, standardRate, logoImgLocation, email);
    }
    
    public void deleteClient(Client clientToDelete)
    {
        BllM.deleteClient(clientToDelete);
    }
    
    //PROJECT Related
    
    public Project addNewProjectToDB(String projectName, Client associatedClient, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed)
    {
        return BllM.addNewProjectToDB(projectName, associatedClient, phoneNr, projectRate, hoursAllocated, isClosed);
    }
    
    public Project editProject(Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr) {
        return BllM.editProject(editedProject, projectName, associatedClientID, projectRate, allocatedHours, isClosed, phoneNr);
    }
    
    public ArrayList<Project> get3RecentProjects()
    {
        return BllM.get3RecentProjectsForUser();
    }
    
    public ArrayList<Project> getAllProjectsForUserTab()
    {
        return BllM.getAllProjectsForUserTab();
    }
    
    public ArrayList<Project> getAllProjects()
    {
        return BllM.getAllProjects();
    }
    
    //TASK Related
    
    public Task addNewTaskToDB(String taskName, Project associatedProject) {
        return BllM.addNewTaskToDB(taskName, associatedProject);
    }
    
    public Task editTask(Task editedTask, String taskName, String description, int associatedProjectID) {
        return BllM.editTask(editedTask, taskName, description, associatedProjectID);
    }
    
    public void removeTaskFromDB(Task taskToDelete) {
        BllM.removeTaskFromDB(taskToDelete);
    }
    
    public List<Task> getTasksForUserInfo()
    {
        return BllM.getTasksForUserInfo();
    }
    
    public void addNewTaskAndSetItRunning(String taskName, Project associatedProject)
    {
        BllM.addNewTaskAndSetItRunning(taskName, associatedProject);
    }
    
    
    //USER Related
    public ArrayList<User> getAllUsers()
    {
        return BllM.getAllUsers();
    }
    
    
    //SESSION Related
    public void startStopSession()
    {
        BllM.startStopSession();
    }
    
     public List<Session> getAllSessionsOfAUser() {
        return BllM.getAllSessionsOfAUser();
    }
     
    public void removeSessionFromDB(Session sessionToDelete)
    {
        BllM.removeSessionFromDB(sessionToDelete);
    }
    
        
        
}
