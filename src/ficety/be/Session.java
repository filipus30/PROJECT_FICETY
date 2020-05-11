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
import java.util.concurrent.TimeUnit;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Session {
    private int sessionID;
    private int associatedUserID;  // the person whom the Session is assigned to.
    private int associatedTaskID;  // the Task whom the Session is assigned to.
    private final StringProperty startTime = new SimpleStringProperty();
    private final StringProperty finishTime = new SimpleStringProperty();
    private StringProperty hours = new SimpleStringProperty();
    private StringProperty taskName = new SimpleStringProperty();
    private String start,finish;
//    private int sessionTime;  //  difference between start time and finish time ...maybe?

  
    
    public Session(int sessionID, int associatedUser, int associatedTask, Timestamp startTime, Timestamp finishTime,String timespent,String taskName) {
        this.sessionID = sessionID;
        this.associatedUserID = associatedUser;
        this.associatedTaskID = associatedTask;
        if(startTime != null)
        { start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime);}
        if(finishTime != null)
        { finish = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(finishTime);}
        this.startTime.set(start);
        this.finishTime.set(finish);
//        if( startTime != null && finishTime != null)
//        {
//            long millis = finishTime.getTime() - startTime.getTime();
//            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
//            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
//            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
//            this.hours.set(hms);
//        }  
//        else
        this.hours.set(timespent);
        
        this.taskName.set(taskName);
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
    
    public StringProperty taskNameProperty()
    {
        return taskName;
    }
    
    public String getTaskName() {
        return this.taskName.get();
    }

    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }
public String getStartTime() {
        return startTime.get();
    }

    public void setStartTime(String value) {
        startTime.set(value);
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }
    public String getFinishTime() {
        return finishTime.get();
    }

    public void setFinishTime(String value) {
        finishTime.set(value);
    }

    public StringProperty finishTimeProperty() {
        return finishTime;
    }
    public String getHours() {
        return hours.get();
    }

    public void setHours(String value) {
        hours.set(value);
    }

    public StringProperty hoursProperty() {
        return hours;
    }
    
}
