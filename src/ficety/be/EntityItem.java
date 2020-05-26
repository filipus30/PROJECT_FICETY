/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author cille
 */
public class EntityItem {

    //Project
    private final StringProperty projectName = new SimpleStringProperty(this, "projectName", "");
    private final StringProperty projectTotalTime = new SimpleStringProperty(this, "seconds", "");
    private final IntegerProperty projectBillableTime = new SimpleIntegerProperty(this, "billableTime");
    //Task
    private final StringProperty taskName = new SimpleStringProperty(this, "taskName", "");
    private final StringProperty taskTime = new SimpleStringProperty(this, "hours", "");
    private final BooleanProperty taskBillable = new SimpleBooleanProperty(this, "billable");
    
    
    public EntityItem(){
        
    }
    
    public EntityItem(Project p){
        projectName.bindBidirectional(p.ProjectNameProperty());
        projectTotalTime.bindBidirectional(p.secondsProperty());
        projectBillableTime.bindBidirectional(p.billableTimeProperty());
    }
    
    public EntityItem(Task t){
        taskName.bindBidirectional(t.taskNameProperty());
        taskTime.bindBidirectional(t.hoursProperty());
        taskBillable.bindBidirectional(t.billableProperty());
    }

    public boolean isTaskBillable() {
        return taskBillable.get();
    }

    public void setTaskBillable(boolean value) {
        taskBillable.set(value);
    }

    public BooleanProperty taskBillableProperty() {
        return taskBillable;
    }
    

    public String getTaskTime() {
        return taskTime.get();
    }

    public void setTaskTime(String value) {
        taskTime.set(value);
    }

    public StringProperty taskTimeProperty() {
        return taskTime;
    }
    

    public String getTaskName() {
        return taskName.get();
    }

    public void setTaskName(String value) {
        taskName.set(value);
    }

    public StringProperty taskNameProperty() {
        return taskName;
    }
    

    public int getProjectBillableTime() {
        return projectBillableTime.get();
    }

    public void setProjectBillableTime(int value) {
        projectBillableTime.set(value);
    }

    public IntegerProperty projectBillableTimeProperty() {
        return projectBillableTime;
    }
    

    public String getProjectTotalTime() {
        return projectTotalTime.get();
    }

    public void setProjectTotalTime(String value) {
        projectTotalTime.set(value);
    }

    public StringProperty projectTotalTimeProperty() {
        return projectTotalTime;
    }
    

    public String getprojectName() {
        return projectName.get();
    }

    public void setprojectName(String value) {
        projectName.set(value);
    }

    public StringProperty projectNameProperty() {
        return projectName;
    }
    
    
}
