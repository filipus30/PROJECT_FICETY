/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class LoggedInUser {
    private static LoggedInUser instance = null;
    private int loggedInUserID;
    private String loggedInUserName;
    private String loggedInUserEmail;
    private String password;
    private int salary;
    private boolean admin; 
    private Task currentTask;
    private Session currentSession = null;

    
    private LoggedInUser() {

    }

    
    public static LoggedInUser getInstance() {
        if(instance == null)
        {
            instance = new LoggedInUser();
        }
            
        return instance;
    }

    public int getId() {
        return loggedInUserID;
    }

    public void setId(int loggedInUserID) {
        this.loggedInUserID = loggedInUserID;
    }

    public String getName() {
        return loggedInUserName;
    }
    
    public String getEmail() {
        return loggedInUserEmail;
    }

    public void setEmail(String loggedInUserEmail) {
        this.loggedInUserEmail = loggedInUserEmail;
    }
    public void setName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }
    
    public Session getCurrentSession() {
        return currentSession;
    }
    
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
    
}
