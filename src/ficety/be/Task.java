/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import java.util.List;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class Task {
    private int taskID;
    private final StringProperty taskName = new SimpleStringProperty();
    private SimpleStringProperty desc = new SimpleStringProperty();
    private IntegerProperty associatedProjectID = new SimpleIntegerProperty();  // the project that the task is assigned to.
    private List<Session> sessions;  //time??
    private final StringProperty hours = new SimpleStringProperty();
    private StringProperty users = new SimpleStringProperty();
    private StringProperty associatedProjectName = new SimpleStringProperty();
    private FloatProperty salary = new SimpleFloatProperty();
    private StringProperty projectPayment = new SimpleStringProperty();


    
    

    
    public Task(int taskID, String name, String description, int associatedProject, String associatedProjectName, String hours) {
        this.taskID = taskID;
        this.taskName.set(name);
        this.desc.set(description);
        this.associatedProjectID.set(associatedProject);
        this.hours.set(hours);
        this.associatedProjectName.set(associatedProjectName);
    }
    

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
    
    public IntegerProperty associatedProjectIDProperty()
    {
        return associatedProjectID;
    }

    public int getAssociatedProjectID() {
        return this.associatedProjectID.get();
    }

    public void setAssociatedProjectID(int associatedProjectID) {
        this.associatedProjectID.set(associatedProjectID);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
    
    public StringProperty usersProperty()
    {
        return users;
    }

    public String getUsers() {
        return this.users.get();
    }

    public void setUsers(String users) {
        this.users.set(users);
    }
   
    public StringProperty associatedProjectName()
    {
        return associatedProjectName;
    }
    
    public String getAssociatedProjectName() {
        return this.associatedProjectName.get();
    }

    public void setAssociatedProjectName(String associatedProjectName) {
        this.associatedProjectName.set(associatedProjectName);
    }
    public void setDesc(String desc)
    {
        this.desc.set(desc);
    }
    public String getDesc()
    {
        return this.desc.get();
    }
    
    public StringProperty descProperty() 
    {
    return desc;
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

    @Override
    public String toString() {
        return this.associatedProjectName.get() + " : " + this.taskName.get();
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
    
    
    public FloatProperty salaryProperty()
    {
        return salary;
    }
    
    public float getSalary() {
        return this.salary.get();
    }

    public void setSalary(float salary) {
        this.salary.set(salary);
    }
    
    public StringProperty projectPayment()
    {
        return projectPayment;
    }
    
    public String getProjectPayment() {
        return this.projectPayment.get();
    }

    public void setProjectPayment(String projectPayment) {
        this.projectPayment.set(projectPayment);
    }




}
