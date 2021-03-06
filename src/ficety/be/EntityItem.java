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
    private final StringProperty projectBillableTime = new SimpleStringProperty(this, "projectBillableTimeNice","");
    //Task
    private final StringProperty taskName = new SimpleStringProperty(this, "taskName", "");
    private final StringProperty taskTime = new SimpleStringProperty(this, "hours", "");
    private final StringProperty taskBillable = new SimpleStringProperty(this, "billable");
    
    //User
    private final StringProperty userName = new SimpleStringProperty(this, "userName", "");
    private final StringProperty userTotalTime = new SimpleStringProperty(this, "niceTime", "");
    private final StringProperty userBillableTime = new SimpleStringProperty(this, "userBillableTime", "");
    private final StringProperty salary = new SimpleStringProperty(this, "income", "");

    public String getSalary() {
        return salary.get();
    }

    public void setSalary(String value) {
        salary.set(value);
    }

    public StringProperty salaryProperty() {
        return salary;
    }

    public EntityItem(){
        
    }
    
    public EntityItem(User u) {
        salary.bindBidirectional(u.incomeProperty());
        userName.bindBidirectional(u.userNameProperty());
        userTotalTime.bindBidirectional(u.niceTimeProperty());
        userBillableTime.bindBidirectional(u.userBillableTimeProperty());
    }
    
    public EntityItem(Project p){
        projectName.bindBidirectional(p.ProjectNameProperty());
        projectTotalTime.bindBidirectional(p.secondsProperty());
        projectBillableTime.bindBidirectional(new SimpleStringProperty(String.valueOf(p.getProjectBillableTimeNice())));
    }
    
    public EntityItem(Task t){
        taskName.bindBidirectional(t.taskNameProperty());
        taskTime.bindBidirectional(t.hoursProperty());
        taskBillable.bindBidirectional(new SimpleStringProperty(String.valueOf(t.getBillable())));
        if(t.getBillable())
            taskBillable.set("Yes");
        else
            taskBillable.set("No");
    }

    public String isTaskBillable() {
        return taskBillable.get();
    }

    public void setTaskBillable(String value) {
        taskBillable.set(value);
    }

    public StringProperty taskBillableProperty() {
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
    

    public String getProjectBillableTime() {
        return projectBillableTime.get();
    }

    public void setProjectBillableTime(String value) {
        projectBillableTime.set(value);
    }

    public StringProperty projectBillableTimeProperty() {
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
    
    public String getUserBillableTime() {
        return userBillableTime.get();
    }

    public void setUserBillableTime(String value) {
        userBillableTime.set(value);
    }

    public StringProperty userBillableTimeProperty() {
        return userBillableTime;
    }
    

    public String getUserTotalTime() {
        return userTotalTime.get();
    }

    public void setUserTotalTime(String value) {
        userTotalTime.set(value);
    }

    public StringProperty userTotalTimeProperty() {
        return userTotalTime;
    }
    

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String value) {
        userName.set(value);
    }

    public StringProperty userNameProperty() {
        return userName;
    }
}
