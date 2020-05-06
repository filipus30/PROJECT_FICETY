/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Project {
    private int projectID;
    private StringProperty projectName = new SimpleStringProperty();
    private IntegerProperty associatedClientID = new SimpleIntegerProperty();  // the person whom the task is assigned to.
    private StringProperty phoneNr = new SimpleStringProperty();
    private FloatProperty projectRate = new SimpleFloatProperty();
    private IntegerProperty allocatedHours = new SimpleIntegerProperty();
    private List<Task> taskList;
    private BooleanProperty isClosed = new SimpleBooleanProperty();
    private StringProperty clientIMG = new SimpleStringProperty();
    private StringProperty clientName = new SimpleStringProperty();
    private StringProperty seconds = new SimpleStringProperty();
    private StringProperty calPayment = new SimpleStringProperty();


    public Project(int projectID, String projectName, int associatedClientID, String phoneNr, float projectRate, int allocatedHours, boolean isClosed, String clientIMG) {
        this.projectID = projectID;
        this.projectName.set(projectName);
        this.associatedClientID.set(associatedClientID);
        this.phoneNr.set(phoneNr);
        this.projectRate.set(projectRate);
        this.allocatedHours.set(allocatedHours);
       // this.taskList = taskList;
        this.isClosed.set(isClosed);
        this.clientIMG.set(clientIMG);
    }

    
    public int getId() {
        return projectID;
    }

    public void setId(int projectID) {
        this.projectID = projectID;
    }

    public int associatedClientID() {
        return this.associatedClientID.get();
    }

    public void setAssociatedClient(int associatedClientID) {
        this.associatedClientID.set(associatedClientID);
    }

    public String getPhoneNr() {
        return this.phoneNr.get();
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr.set(phoneNr);
    }

    public float getProjectRate() {
        return this.projectRate.get();
    }

    public void setProjectRate(float projectRate) {
        this.projectRate.set(projectRate);
    }

     public int getAllocatedHours() {
        return this.allocatedHours.get();
    }

    public void setAllocatedHours(int allocatedHours) {
        this.allocatedHours.set(allocatedHours);
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public boolean isClosed() {
        return this.isClosed.get();
    }

    public void setClosed(boolean isClosed) {
        this.isClosed.set(isClosed);
    }
    
    public String getClientIMG()
    {
        return this.clientIMG.get();
    }
    public void setClientIMG(String clientIMG)
    {
        this.clientIMG.set(clientIMG);
    }
    
    public String getSeconds() {
        return this.seconds.get();
    }

    public void setSeconds(String seconds) {
        this.seconds.set(seconds);
    }

    public String getClientName() {
        return this.clientName.get();
    }

    public void setClientName(String clientName) {
        this.clientName.set(clientName);
    }
    
    @Override
    public String toString()
    {
        return this.projectName.get();
    }
    public String getProjectName() {
        return this.projectName.get();
    }

    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
    }

    public String getCalPayment() {
        return this.calPayment.get();
    }

    public void setCalPayment(String calPayment) {
        this.calPayment.set(calPayment);
    }
    
}
