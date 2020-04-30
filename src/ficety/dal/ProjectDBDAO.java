/*
 * To change this license header, choose License Headers in ProjectDBDAO Properties.
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
import ficety.be.Client;
import ficety.be.Project;
import ficety.be.Task;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class ProjectDBDAO {
    private DBConnection dbc;
    
    
    public ProjectDBDAO() {
        dbc = new DBConnection();
    }
    
    
    public Project addNewProjectToDB(String projectName, Client client, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed) throws SQLException { 
    //  Adds a new Project to the DB, and returns the updated Project to the GUI
        String sql = "INSERT INTO PROJECTS(projectName, associatedClientID, projectRate, hoursAllocated, closed) VALUES (?,?,?,?,?)";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, projectName);
            int associatedClientID = client.getId();
            pstmt.setInt(2, associatedClientID);
            pstmt.setString(3, phoneNr);
            pstmt.setFloat(4, projectRate);
            pstmt.setInt(5, hoursAllocated);
            int closed = 0;
            if(isClosed == true)
                closed = 1;
            pstmt.setInt(6, closed);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Project failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String clientIMG = client.getImgLocation();
                    Project newProject = new Project(0, projectName, associatedClientID, phoneNr, projectRate, hoursAllocated, isClosed, clientIMG);
                    newProject.setId((int) generatedKeys.getLong(1));
                    return newProject;
                } else {
                    throw new SQLException("Creating Project failed, no ID obtained.");
                } 
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
     
    public Project getProject(int projectID) throws SQLException {
    //  Returns a spacific Project data object given the Project id
        Project project = null;
        String SQLStmt = "SELECT Projects.Name, Projects.AssociatedClient, Projects.ProjectRate, Projects.AllocatedHours, Projects.Closed, Clients.LogoImgLocation FROM Projects JOIN Clients ON Projeects.AssociatedClient = Clients.Id WHERE Projects.Id = ?";
        try(Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(SQLStmt);
            pstmt.setInt(1, projectID);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) //While you have something in the results
            {
                String projectName = rs.getString("Name");
                int associatedClientID = rs.getInt("AssociatedClient");
                Float projectRate = rs.getFloat("ProjectRate");
                String phoneNr = rs.getString("PhoneNr");
                int allocatedHours = rs.getInt("AllocatedHours");
                int closed = rs.getInt("Closed");
                boolean isClosed = false;
                if(closed == 1)
                isClosed = true;
                String clientIMG = rs.getString("LogoImgLocation");
                project = new Project(projectID, projectName, associatedClientID, phoneNr, projectRate, allocatedHours, isClosed, clientIMG); 
            }    
        }
        return project;
    }   
    

    public Project editProject (Project editedProject, String projectName, int associatedClientID, float projectRate, int allocatedHours, boolean isClosed, String phoneNr) { 
    //  Edits a Project in the Projects table of the database given the Projects new details.  
        String sql = "UPDATE Projects SET Name = ?, ProjectRate = ?, AllocatedHours = ?, Closed = ? , PhoneNr = ?, AssociatedClient = ?  WHERE Id = ?";
        try ( Connection con = dbc.getConnection()) {
            //Create a prepared statement.
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, projectName);
            pstmt.setFloat(2, projectRate);
            pstmt.setInt(3, allocatedHours);
            int closed = 0;
            if(isClosed == true)
                closed = 1;
            pstmt.setInt(4, closed);
            pstmt.setString(5, phoneNr);
            pstmt.setInt(6, associatedClientID);
            int projectId = editedProject.getId();
            pstmt.setInt(6, projectId);
            
            
            pstmt.executeUpdate();  //Execute SQL query.
            editedProject.setName(projectName);
            editedProject.setAssociatedClient(associatedClientID);  
            editedProject.setProjectRate(projectRate);
            editedProject.setAllocatedHours(allocatedHours);
            editedProject.setClosed(isClosed);
            editedProject.setPhoneNr(phoneNr);
            return editedProject;
        } catch (SQLServerException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Project not updated.");
        return null;  //  editedProject?
    }
      
      public ArrayList<Project> get3RecentProjectsForUser(int loggedInUserKey)
      {
        ArrayList<Project> recentProjects = new ArrayList();
        String sql = "Select TOP(3) part.* FROM (Select Projects.Id, Projects.Name, Projects.ProjectRate, Projects.AssociatedClient, Projects.Closed, Projects.Phonenr, Projects.AllocatedHours, Clients.LogoImgLocation, Sessions.FinishTime, ROW_NUMBER() OVER(Partition BY Projects.Id ORDER BY Sessions.FinishTime) Corr FROM Projects JOIN Tasks ON Projects.Id = Tasks.AssociatedProject  JOIN Sessions ON Tasks.Id = Sessions.AssociatedTask JOIN Clients On Projects.AssociatedClient = Clients.Id WHERE Sessions.AssociatedUser = ? AND Closed = 0) part WHERE part.Corr=1";
        try ( Connection con = dbc.getConnection()) {
        //Create a prepared statement.
        PreparedStatement pstmt = con.prepareStatement(sql);
        //Set parameter values.
        pstmt.setInt(1, loggedInUserKey);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            int projectId = rs.getInt("Id");
            String projectName = rs.getString("Name");
            int associatedClientID = rs.getInt("AssociatedClient");
            Float projectRate = rs.getFloat("ProjectRate");
            String phoneNr = rs.getString("PhoneNr");
            int allocatedHours = rs.getInt("AllocatedHours");
            int closed = rs.getInt("Closed");
            boolean isClosed = false;
            if(closed == 1)
            isClosed = true;
            String clientIMG = rs.getString("LogoImgLocation");
            Project project; 
            project = new Project(projectId, projectName, associatedClientID, phoneNr, projectRate, allocatedHours, isClosed, clientIMG);
            recentProjects.add(project);
        }
        return recentProjects;  
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recentProjects;
      }
}
