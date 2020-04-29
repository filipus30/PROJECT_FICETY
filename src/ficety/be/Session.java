/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Session {
    private int sessionID;
    private int associatedUserID;  // the person whom the Session is assigned to.
    private int associatedTaskID;  // the Task whom the Session is assigned to.
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
//    private int sessionTime;  //  difference between start time and finish time ...maybe?

    
    public Session(int sessionID, int associatedUser, int associatedTask, LocalDateTime startTime, LocalDateTime finishTime) {
        this.sessionID = sessionID;
        this.associatedUserID = associatedUserID;
        this.associatedTaskID = associatedTaskID;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    
    public int getSessionId() {
        return sessionID;
    }

    public void setSessionId(int sessionId) {
        this.sessionID = sessionId;
    }

    public int getAssociatedUserID() {
        return associatedUserID;
    }

    public void setAssociatedUserID(int associatedUserID) {
        this.associatedUserID = associatedUserID;
    }

    public int getAssociatedTask() {
        return associatedTaskID;
    }

    public void setAssociatedTask(int associatedTaskID) {
        this.associatedTaskID = associatedTaskID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
    
    
    
}
