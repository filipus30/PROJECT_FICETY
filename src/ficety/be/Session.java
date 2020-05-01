/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import java.time.LocalDate;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Session {
    private int sessionID;
    private int associatedUserID;  // the person whom the Session is assigned to.
    private int associatedTaskID;  // the Task whom the Session is assigned to.
    private Timestamp startTime;
    private Timestamp finishTime;
    private String hours;
    private String taskName;
//    private int sessionTime;  //  difference between start time and finish time ...maybe?

  
    
    public Session(int sessionID, int associatedUser, int associatedTask, Timestamp startTime, Timestamp finishTime,String timespent,String taskName) {
        this.sessionID = sessionID;
        this.associatedUserID = associatedUser;
        this.associatedTaskID = associatedTask;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.hours = timespent;
        this.taskName = taskName;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getAssociatedUserID() {
        return associatedUserID;
    }

    public void setAssociatedUserID(int associatedUserID) {
        this.associatedUserID = associatedUserID;
    }

    public int getAssociatedTaskID() {
        return associatedTaskID;
    }

    public void setAssociatedTaskID(int associatedTaskID) {
        this.associatedTaskID = associatedTaskID;
    }

    public String getStartTime() {
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(finishTime);
        return s;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(finishTime);
        return s;
    }

    public void setFinishTime(Timestamp finishTime) {
           this.finishTime = finishTime;
    }

     public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

   
    
    
}
