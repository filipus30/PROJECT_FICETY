/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

import java.util.List;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class User {
    
    private int userID;
    private String userName;
    private String email;
    private String password;
    private float salary;  
    private boolean isAdmin; 
    private long totalTime;
    private String niceTime;


    public User(int userID, String userName, String email, String password, float salary, boolean isAdmin) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.isAdmin = isAdmin;
    }

    
    public int getUserId() {
        return userID;
    }

    public void setUserId(int id) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    
    public String getNiceTime() {
        return niceTime;
    }

    public void setNiceTime(String niceTime) {
        this.niceTime = niceTime;
    }
}
