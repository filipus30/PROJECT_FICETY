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

    boolean isTaskBillable() {
        return taskBillable.get();
    }

    void setTaskBillable(boolean value) {
        taskBillable.set(value);
    }

    BooleanProperty taskBillableProperty() {
        return taskBillable;
    }
    

    String getTaskTime() {
        return taskTime.get();
    }

    void setTaskTime(String value) {
        taskTime.set(value);
    }

    StringProperty taskTimeProperty() {
        return taskTime;
    }
    

    String getTaskName() {
        return taskName.get();
    }

    void setTaskName(String value) {
        taskName.set(value);
    }

    StringProperty taskNameProperty() {
        return taskName;
    }
    

    int getProjectBillableTime() {
        return projectBillableTime.get();
    }

    void setProjectBillableTime(int value) {
        projectBillableTime.set(value);
    }

    IntegerProperty projectBillableTimeProperty() {
        return projectBillableTime;
    }
    

    String getProjectTotalTime() {
        return projectTotalTime.get();
    }

    void setProjectTotalTime(String value) {
        projectTotalTime.set(value);
    }

    StringProperty projectTotalTimeProperty() {
        return projectTotalTime;
    }
    

    String getProjectName() {
        return projectName.get();
    }

    void setProjectName(String value) {
        projectName.set(value);
    }

    StringProperty projectNameProperty() {
        return projectName;
    }
    
    
}
