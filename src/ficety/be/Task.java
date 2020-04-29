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
    private long[] taskDuration;  //  total time used on a task

    
    public Task(int taskID, String name, String description, int associatedProject) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.description = description;
        this.associatedProjectID = associatedProjectID;
        
    }

    
    public int getTaskId() {
        return taskID;
    }

    public void setTaskId(int id) {
        this.taskID = taskID;
    }

    public String getName() {
        return taskName;
    }

    public void setName(String name) {
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

    public void setAssociatedProject(int associatedProjectID) {
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
}
