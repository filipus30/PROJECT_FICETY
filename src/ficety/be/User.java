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
    
    private IntegerProperty userID = new SimpleIntegerProperty();
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private FloatProperty salary = new SimpleFloatProperty();  
    private BooleanProperty isAdmin = new SimpleBooleanProperty(); 
    private LongProperty totalTime = new SimpleLongProperty();
    private StringProperty niceTime = new SimpleStringProperty();


    public User(int userID, String userName, String email, String password, float salary, boolean isAdmin) {
        this.userID.set(userID);
        this.userName.set(userName);
        this.email.set(email);
        this.password.set(password);
        this.salary.set(salary);
        this.isAdmin.set(isAdmin);
    }

    
    public int getUserId() {
        return this.userID.get();
    }

    public void setUserId(int id) {
        this.userID.set(id);
    }

    public String getUserName() {
        return this.userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public float getSalary() {
        return this.salary.get();
    }

    public void setSalary(float salary) {
        this.salary.set(salary);
    }

    public boolean getIsAdmin() {
        return this.isAdmin.get();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }
    
    public long getTotalTime() {
        return this.totalTime.get();
    }

    public void setTotalTime(long totalTime) {
        this.totalTime.set(totalTime);
    }
    
    public String getNiceTime() {
        return this.niceTime.get();
    }

    public void setNiceTime(String niceTime) {
        this.niceTime.set(niceTime);
    }
}
