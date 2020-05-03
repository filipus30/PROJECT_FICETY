/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import java.util.List;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Task {
    private int taskID;
    private String taskName;
    private String description;
    private int associatedProjectID;  // the project that the task is assigned to.
//or  private List<int> associatedUserIDs;  // the people to whom the task is assigned to.
    private List<Session> sessions;  //time??
    private long[] taskDuration;  //  total time used on a 
    private String hours;
    private String users;
    private String associatedProjectName;



    
    

    
    public Task(int taskID, String name, String description, int associatedProject, String associatedProjectName, String hours) {
        this.taskID = taskID;
        this.taskName = name;
        this.description = description;
        this.associatedProjectID = associatedProject;
        this.hours = hours;
        this.associatedProjectName = associatedProjectName;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssociatedProjectID() {
        return associatedProjectID;
    }

    public void setAssociatedProjectID(int associatedProjectID) {
        this.associatedProjectID = associatedProjectID;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public long[] getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(long[] taskDuration) {
        this.taskDuration = taskDuration;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return  taskName;
    }
   
    public String getAssociatedProjectName() {
        return associatedProjectName;
    }

    public void setAssociatedProjectName(String associatedProjectName) {
        this.associatedProjectName = associatedProjectName;
    }
}
