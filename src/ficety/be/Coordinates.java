/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.be;

/**
 *
 * @author macos
 */
public class Coordinates {
    private int x;
    private int y;
    private String topBar;
    private String subBar;
    private long taskSeconds;

    public Coordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Coordinates(String topBar, String subBar, long taskSeconds)
    {
        this.topBar = topBar;
        this.subBar = subBar;
        this.taskSeconds = taskSeconds;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public String getProject() {
        return topBar;
    }

    public void setProject(String project) {
        this.topBar = project;
    }

    public String getTask() {
        return subBar;
    }

    public void setTask(String task) {
        this.subBar = task;
    }

    public long getTaskSeconds() {
        return taskSeconds;
    }

    public void setTaskSeconds(long taskSeconds) {
        this.taskSeconds = taskSeconds;
    }
}
