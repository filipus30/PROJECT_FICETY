/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ficety.be.Project;
import ficety.be.Session;
import ficety.be.Task;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class TaskDBDAO {
    private DBConnection dbc;
 //   private SessionDBDAO sessionDBDao;
    private boolean debug = false;
    
    
    public TaskDBDAO() {
            dbc = new DBConnection();
  //          sessionDBDao = new SessionDBDAO();
    }        

    public Task addNewTaskToDB(String taskName, Project associatedProject){ 
    //  Adds a new Task to the DB, and returns the updated Project to the GUI
        debug("Trying to create task name " + taskName);
        String sql = "INSERT INTO Tasks (Name, Description, AssociatedProject) VALUES (?,?,?)";
        int associatedProjectID = associatedProject.getId();
        String associatedProjectName = associatedProject.getProjectName();
        Task newTask = new Task(0, taskName, " ", associatedProjectID, associatedProjectName, "");
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, taskName);
            String description = " ";
            pstmt.setString(2, description);
            pstmt.setInt(3, associatedProjectID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Task failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newTask.setTaskID((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating Task failed, no ID obtained.");
                } 
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newTask;
    }
    
        public Task addNewTaskToDB(String taskName, String taskDesc, Project associatedProject){ 
    //  Adds a new Task to the DB, and returns the updated Project to the GUI
        debug("Trying to create task name " + taskName);
        String sql = "INSERT INTO Tasks (Name, Description, AssociatedProject) VALUES (?,?,?)";
        int associatedProjectID = associatedProject.getId();
        String associatedProjectName = associatedProject.getProjectName();
        Task newTask = new Task(0, taskName, taskDesc, associatedProjectID, associatedProjectName, "");
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, taskName);
            pstmt.setString(2, taskDesc);
            pstmt.setInt(3, associatedProjectID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Task failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newTask.setTaskID((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating Task failed, no ID obtained.");
                } 
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newTask;
    }
  
    public Task editTask (Task editedTask, String taskName, String description, int associatedProjectID) { 
    //  Edits a Task in the Task table of the database given the Projects new details.  
        int taskID = editedTask.getTaskID();
        String sql = "UPDATE Tasks SET Name = ?, Description = ?, AssociatedProject = ? WHERE Id = ?";
        try ( Connection con = dbc.getConnection()) {
            //Create a prepared statement.
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, taskName);
            pstmt.setString(2, description);
            pstmt.setInt(3, associatedProjectID);
            pstmt.setInt(4, taskID);
            pstmt.executeUpdate();  //Execute SQL query.
            editedTask.setTaskName(taskName);
            editedTask.setDescription(description);
            editedTask.setAssociatedProjectID(associatedProjectID);
            return editedTask;
        } catch (SQLServerException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
      
        
    public void removeTaskFromDB(Task taskToDelete) {
    //  Removes a user from the User table of the database given a User data object
        int idToDelete = taskToDelete.getTaskID();
        String sql = "DELETE FROM Tasks WHERE id = ?";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,idToDelete);
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }

    public List<Task> getTasksForUserInfo(int user){
              List<Task> alltasks = new ArrayList();
        try(Connection con = dbc.getConnection()) {
           // String sql = "Select Tasks.Name, Tasks.AssociatedProject, Tasks.Description, SUM(Datediff(MINUTE, S.StartTime, S.FinishTime)) AS Total from Tasks JOIN Sessions S ON Tasks.Id=S.AssociatedTask where Tasks.Id= '3' AND S.AssociatedUser = '?' GROUP BY Tasks.Name, Tasks.AssociatedProject, Tasks.Description;";
           String sql = "Select Tasks.id ,Tasks.Name, Tasks.AssociatedProject, Tasks.Description, P.Name AS PName, SUM(Datediff(SECOND, S.StartTime, S.FinishTime)) AS Total " + 
                        "FROM Tasks " + 
                            "JOIN Sessions S ON Tasks.Id=S.AssociatedTask " + 
                            "JOIN Projects P ON Tasks.AssociatedProject=P.Id " + 
                        "WHERE S.AssociatedUser = ? " + 
                        "GROUP BY Tasks.Name, Tasks.AssociatedProject, Tasks.Description, Tasks.id, P.Name";
           PreparedStatement pstmt = con.prepareStatement(sql);   
            pstmt.setInt(1,user);
             pstmt.execute();
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) //While you have something in the results
            {
                int taskId = rs.getInt("id");
                String taskName =  rs.getString("Name");
                String description =  rs.getString("Description");   
                int associatedProjectID = rs.getInt("associatedProject");
                String associatedProjectName = rs.getString("PName");
                int time = rs.getInt("Total");
               // String timee = String.format("%02d:%02d:%02d",time/3600 ,time / 60, time % 60);
              String timee = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60);
                alltasks.add(new Task(taskId, taskName, description, associatedProjectID, associatedProjectName, timee));
               
            }    
        } catch (SQLException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return alltasks;
    }
    

    
    public void addTasksToProject(Project p)
    {
        ArrayList<Task> pTasks = new ArrayList();
        String sql = "SELECT T.Id, T.Name, T.Description, P.Name as PName FROM Tasks T JOIN Projects P ON T.AssociatedProject=P.Id WHERE AssociatedProject = ?";
        try(Connection con = dbc.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            int projectId = p.getId();
            
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
               int taskId = rs.getInt("Id");
               String taskName = rs.getString("Name");
               String taskDesc = rs.getString("Description");
               String associatedProjectName = rs.getString("PName");
               
               Task newTask = new Task(taskId, taskName, taskDesc, projectId, associatedProjectName, "");
               pTasks.add(newTask);
            }
            p.setTaskList(pTasks);
        } catch (SQLException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void debug(String msg)
    {
        if(debug == true)
        {
            System.out.println(msg);
        }
    } 
    
}
