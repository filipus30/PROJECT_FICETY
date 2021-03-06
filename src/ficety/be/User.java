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
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class User {
    
    private int userID;
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private FloatProperty salary = new SimpleFloatProperty();  
    private BooleanProperty isAdmin = new SimpleBooleanProperty(); 
    private LongProperty totalTime = new SimpleLongProperty();
    private StringProperty niceTime = new SimpleStringProperty();
    private StringProperty userBillableTime = new SimpleStringProperty();
    private final StringProperty income = new SimpleStringProperty();

    public String getIncome() {
        return income.get();
    }

    public void setIncome(String value) {
        income.set(value);
    }

    public StringProperty incomeProperty() {
        return income;
    }
    

    


    public User(int userID, String userName, String email, String password, float salary, boolean isAdmin) {
        this.userID = userID;
        this.userName.set(userName);
        this.email.set(email);
        this.password.set(password);
        this.salary.set(salary);
        this.isAdmin.set(isAdmin);
    }

    
    public int getUserId() {
        return userID;
    }

    public void setUserId(int id) {
        this.userID = id;
    }

    public StringProperty userNameProperty()
    {
        return userName;
    }
    
    public String getUserName() {
        return this.userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public StringProperty emailProperty()
    {
        return email;
    }
    
    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty passwordProperty()
    {
        return password;
    }
    
    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public BooleanProperty isAdminProperty()
    {
        return isAdmin;
    }
    
    public boolean getIsAdmin() {
        return this.isAdmin.get();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public LongProperty totalTimeProperty()
    {
        return totalTime;
    }
    
    public long getTotalTime() {
        return this.totalTime.get();
    }

    public void setTotalTime(long totalTime) {
        this.totalTime.set(totalTime);
    }
    
    public StringProperty niceTimeProperty()
    {
        return niceTime;
    }
    
    public String getNiceTime() {
        return this.niceTime.get();
    }

    public void setNiceTime(String niceTime) {
        this.niceTime.set(niceTime);
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
    
    
    @Override
    public String toString() {
        return userName.get();
    }
    
    
}
