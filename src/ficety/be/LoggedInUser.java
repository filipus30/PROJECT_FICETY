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
    private boolean selected = false;
    private String startTime;
    private String finishTime;

        
    private LoggedInUser() {

       
        this.password = password;
        this.salary = salary;
        this.admin = admin;
    
    }

    
    

    
    public static LoggedInUser getInstance() {

        if(instance == null)
        {
            instance = new LoggedInUser();
        }
        return instance;
    }

   

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
    
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String adminStartTime) {
        this.startTime = adminStartTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String adminFinishTime) {
        this.finishTime = adminFinishTime;
    }
    

}
