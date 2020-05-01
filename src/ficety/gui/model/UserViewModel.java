/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.gui.model;

import ficety.be.Project;
import ficety.be.User;
import ficety.bll.BllManager;
import java.util.ArrayList;

/**
 *
 * @author Trigger
 */
public class UserViewModel {
    private BllManager BllM = new BllManager();
    
    public void startStopSession()
    {
        BllM.startStopSession();
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
    
    public void addNewTaskAndSetItRunning(String taskName, Project associatedProject)
    {
        BllM.addNewTaskAndSetItRunning(taskName, associatedProject);
    }
    
    public ArrayList<User> getAllUsers()
    {
        return BllM.getAllUsers();
    }
}
