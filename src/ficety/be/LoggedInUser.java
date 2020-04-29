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
    private int salary;  // do we really need this?
    private boolean admin;  // or 0 = developer, 1 = admin, 2 = project owner.
    private Task currentTask;

    
    public LoggedInUser(int LoggedInUserID, String LoggedInUserName, String LoggedInUserEmail, String password, int salary, boolean admin, Task currentTask) {
        this.loggedInUserID = LoggedInUserID;
        this.loggedInUserName = LoggedInUserName;
        this.loggedInUserEmail = loggedInUserEmail;
        this.password = password;
        this.salary = salary;
        this.admin = admin;
        this.currentTask = currentTask;
    }

    
    public static LoggedInUser getInstance() {
        return instance;
    }

    public static void setInstance(LoggedInUser instance) {
        LoggedInUser.instance = instance;
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
    
    
}
