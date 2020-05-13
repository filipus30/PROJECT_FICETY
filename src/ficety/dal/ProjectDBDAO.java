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
import ficety.be.Coordinates;
import ficety.be.Project;
import ficety.be.Task;
import java.security.Timestamp;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class ProjectDBDAO {
    private boolean debug = false;
    private DBConnection dbc;
    
    
    public ProjectDBDAO() {
        dbc = new DBConnection();
    }
    
    
    public Project addNewProjectToDB(String projectName, Client client, String phoneNr, float projectRate, int hoursAllocated, boolean isClosed){ 
    //  Adds a new Project to the DB, and returns the updated Project to the GUI
        String sql = "INSERT INTO PROJECTS(projectName, associatedClientID,PhoneNr,projectRate, hoursAllocated, closed) VALUES (?,?,?,?,?,?)";
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
            editedProject.setProjectName(projectName);
            editedProject.setAssociatedClientID(associatedClientID);  
            editedProject.setProjectRate(projectRate);
            editedProject.setAllocatedHours(allocatedHours);
            editedProject.setIsClosed(isClosed);
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
        String sql = "Select TOP(3) part.* " + 
                "FROM (Select Projects.Id, Projects.Name, Projects.ProjectRate, Projects.AssociatedClient, Projects.Closed, Projects.Phonenr, Projects.AllocatedHours, " +
                        "Clients.LogoImgLocation, Sessions.FinishTime, ROW_NUMBER() OVER(Partition BY Projects.Id ORDER BY Sessions.FinishTime) Corr " + 
                    "FROM Projects " +
                    "JOIN Tasks ON Projects.Id = Tasks.AssociatedProject  " + 
                    "JOIN Sessions ON Tasks.Id = Sessions.AssociatedTask " + 
                    "JOIN Clients On Projects.AssociatedClient = Clients.Id " + 
                    "WHERE Sessions.AssociatedUser = ? AND Closed = 0) part " + 
                "WHERE part.Corr=1";
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
      
      public ArrayList<Project> getAllProjectsForUserTab(int userID)
      {
          ArrayList<Project> allProjectsForUser = new ArrayList();
          String sql ="SELECT Part.* " + 
                        "FROM (SELECT Projects.Id, Projects.Name AS PName, Projects.AssociatedClient, Projects.ProjectRate, Projects.PhoneNr, Projects.AllocatedHours, Projects.Closed, "+ 
                                "Clients.Name AS CName, Clients.LogoImgLocation, Tasks.Id AS TId, " + 
                                "SUM(Datediff(SECOND, Sessions.StartTime, Sessions.FinishTime)) OVER(PARTITION BY Tasks.Id) AS TotalTime, " +
                                "ROW_NUMBER() OVER(PARTITION BY Projects.Id ORDER BY Projects.Name) AS Corr " +
                            "FROM Projects " + 
                                "JOIN Clients ON Projects.AssociatedClient=Clients.Id " + 
                                "JOIN Tasks ON Projects.Id=Tasks.AssociatedProject " + 
                                "JOIN Sessions ON Tasks.Id = Sessions.AssociatedTask " + 
                            "WHERE Sessions.AssociatedUser = ?)Part " + 
                        "WHERE part.Corr=1;";
      
          try ( Connection con = dbc.getConnection()) {
        //Create a prepared statement.
        PreparedStatement pstmt = con.prepareStatement(sql);
        //Set parameter values.
        pstmt.setInt(1, userID);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            int projectId = rs.getInt("Id");
            String projectName = rs.getString("PName");
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
            String ClientName = rs.getString("CName");
            project.setClientName(ClientName);
            int time = rs.getInt("TotalTime");
             String timee = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60);
            project.setSeconds(timee);
            allProjectsForUser.add(project);
        }
        return allProjectsForUser;  
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allProjectsForUser;
      
      }
      
      public ArrayList<Project> getAllProjects()
      {
          ArrayList<Project> allProjects = new ArrayList();
          String sql ="SELECT Part.*  " +
                        "FROM (SELECT Projects.Id, Projects.Name AS PName, Projects.AssociatedClient, Projects.ProjectRate, " +
                        "Projects.PhoneNr, Projects.AllocatedHours, Projects.Closed, " +
                        "Clients.Name AS CName, Clients.LogoImgLocation, Tasks.Id AS TId, " +
                        "Temp.BillableTime, " +
                        "Temp2.TotalTime, " +
                        "ROW_NUMBER() OVER(PARTITION BY Projects.Id ORDER BY Projects.Name) AS Corr " +
                        "FROM Projects " +
                            "JOIN Clients ON Projects.AssociatedClient=Clients.Id  " +
                            "LEFT JOIN Tasks ON Projects.Id=Tasks.AssociatedProject " +
                            "LEFT JOIN Sessions ON Tasks.Id = Sessions.AssociatedTask " +
                            "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY Projects.Id) AS BillableTime , " +
                                    "Projects.Name " +
                                "FROM Sessions " +
                                "JOIN Tasks ON  Sessions.AssociatedTask = Tasks.Id " +
                                "JOIN Projects ON Projects.Id = Tasks.AssociatedProject " +
                                "WHERE Tasks.Billable = 1 " + 
                            ") Temp ON Temp.Name = Projects.Name " +
                            "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY Projects.Id) AS TotalTime , " +
                                    "Projects.Name " +
                                "FROM Sessions " +
                                "JOIN Tasks ON  Sessions.AssociatedTask = Tasks.Id " +
                                "JOIN Projects ON Projects.Id = Tasks.AssociatedProject " +
                            ") Temp2 ON Temp2.Name = Projects.Name " +
                        ") Part " +
                        "WHERE part.Corr=1;";
      
          try ( Connection con = dbc.getConnection()) {
        //Create a prepared statement.
        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            int projectId = rs.getInt("Id");
            String projectName = rs.getString("PName");
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
            String ClientName = rs.getString("CName");
            project.setClientName(ClientName);
            int totalTime = rs.getInt("TotalTime");
            String timee = String.format("%02d:%02d:%02d", totalTime / 3600, (totalTime % 3600) / 60, totalTime % 60);
            project.setSeconds(timee);
            int time = rs.getInt("BillableTime");
            if(allocatedHours > 0)
            {
                double hours = time/3600;
                double quarterly = (Math.round(Math.round((time%3600)*60) / 15.0) * .25);
                double payment = (hours + quarterly)*projectRate;
                String total = String.valueOf(payment);
                project.setCalPayment(total + " calculated");
            }
            else
            {
                String payment = String.valueOf(projectRate);
                project.setCalPayment(payment + "fixed rate");
            }
            
            allProjects.add(project);
        }
        
        return allProjects;  
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allProjects;
      
      }
      
      public void deleteProject(Project projectToDelete)
      {
    //  Delete specific Project
        int idToDelete = projectToDelete.getId();
        try(Connection con = dbc.getConnection()){
            String sql = "DELETE FROM Projects WHERE Id = ?";
             PreparedStatement pstmt = con.prepareStatement(sql);   
             pstmt.setInt(1,idToDelete);
             pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }          
      }
      
      private void debug(String msg)
      {
          if(debug == true)
          {
              System.out.println(msg);
          }
      }
      
    public ArrayList<Coordinates> getAllProjectsForUserGraph(int userId,String startTime,String finishTime)
      {
          ArrayList<Coordinates> list = new ArrayList();
       String sql = "Select Part.*\n" +
"    FROM (SELECT U.Name as UserName, Temp.DayTime, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY Temp.dayTime) AS UserTime,\n" +
"   			 ROW_NUMBER() OVER(PARTITION BY Temp.dayTime ORDER BY U.Name) AS Corr\n" +
"   		 FROM Users U\n" +
"   		 LEFT JOIN Sessions S ON U.Id = S.AssociatedUser\n" +
"   		 JOIN Tasks T ON T.Id = S.AssociatedTask\n" +
"   		 JOIN (Select Id, DAY(StartTime) AS dayTime FROM Sessions) Temp ON Temp.Id = S.Id\n" +
"   		 WHERE U.Id = ?\n" +
"   		 AND S.StartTime >= Convert(datetime2(7), ?)\n" +
"   		 AND S.FinishTime <= Convert(datetime2(7), ?)\n" +
"   		 ) Part\n" +
"\n" +
"   		 WHERE\n" +
"   			 Corr = 1;";
       try ( Connection con = dbc.getConnection()) {
        //Create a prepared statement.
        PreparedStatement pstmt = con.prepareStatement(sql);
        //Set parameter values.
        pstmt.setInt(1, userId);
        pstmt.setString(2, startTime);
        pstmt.setString(3, finishTime);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            int x = rs.getInt("DayTime");
            int y = rs.getInt("UserTime");
            Coordinates c = new Coordinates(x,y);
            list.add(c);
            
        }
     
      
      } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list;
      }
      
    public ArrayList<Coordinates> getSingleProjectForUserGraph(int userId,String startTime,String finishTime,int projectId)
      {
          ArrayList<Coordinates> list = new ArrayList();
       String sql = "Select Part.*\n" +
"    FROM (SELECT P.Name as ProjectName,  Temp.DayTime, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY Temp.dayTime) AS UserTime,\n" +
"   			 ROW_NUMBER() OVER(PARTITION BY Temp.dayTime ORDER BY U.Name) AS Corr\n" +
"   		 FROM Users U\n" +
"   		 LEFT JOIN Sessions S ON U.Id = S.AssociatedUser\n" +
"   		 JOIN Tasks T ON T.Id = S.AssociatedTask\n" +
"   		 JOIN Projects P ON T.AssociatedProject = P.Id\n" +
"   		 JOIN (Select Id, DAY(StartTime) AS dayTime FROM Sessions) Temp ON Temp.Id = S.Id\n" +
"   		 WHERE U.Id = ?\n" +
"   		 AND S.StartTime >= Convert(datetime2(7), ?)\n" +
"   		 AND S.FinishTime <= Convert(datetime2(7), ?)\n" +
"   		 AND P.Id = ?\n" +
"   		 ) Part\n" +
"\n" +
"   		 WHERE\n" +
"   			 Corr = 1;";
       try ( Connection con = dbc.getConnection()) {
        //Create a prepared statement.
        PreparedStatement pstmt = con.prepareStatement(sql);
        //Set parameter values.
        pstmt.setInt(1, userId);
        pstmt.setString(2, startTime);
        pstmt.setString(3, finishTime);
        pstmt.setInt(4, projectId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            int x = rs.getInt("DayTime");
            int y = rs.getInt("UserTime");
            Coordinates c = new Coordinates(x,y);
            list.add(c);
            
        }
     
      
      } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list;
      }
      
    public ArrayList<Coordinates> getSingleProjectForAdmGraph(String startTime,String finishTime,int projectId)
      {
          ArrayList<Coordinates> list = new ArrayList();
       String sql = "Select Part.*\n" +
"    FROM (SELECT Temp.DayTime, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY Temp.dayTime) AS UserTime,\n" +
"   			 ROW_NUMBER() OVER(PARTITION BY Temp.dayTime ORDER BY Temp.dayTime) AS Corr\n" +
"   		 FROM Sessions S\n" +
"   		 JOIN (Select Id, DAY(StartTime) AS dayTime FROM Sessions) Temp ON Temp.Id = S.Id\n" +
"   		 JOIN Tasks T ON T.Id = S.AssociatedTask\n" +
"   		 JOIN Projects P ON P.Id = T.AssociatedProject\n" +
"   		 WHERE S.StartTime >= Convert(datetime2(7), ?)\n" +
"   		 AND S.StartTime <= Convert(datetime2(7), ?)\n" +
"   		 AND P.Id = ?\n" +
"   		 ) Part\n" +
"\n" +
"   		 WHERE\n" +
"   			 Corr = 1;";
       try ( Connection con = dbc.getConnection()) {
        //Create a prepared statement.
        PreparedStatement pstmt = con.prepareStatement(sql);
        //Set parameter values.
        pstmt.setString(1, startTime);
        pstmt.setString(2, finishTime);
        pstmt.setInt(3, projectId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            int x = rs.getInt("DayTime");
            int y = rs.getInt("UserTime");
            Coordinates c = new Coordinates(x,y);
            list.add(c);
            
        }
     
      
      } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list;
      }
      
    public ArrayList<Coordinates> getAllProjectsForAdmBar(String startTime, String finishTime) //For complex barchart
      {
          ArrayList<Coordinates> projCol = new ArrayList();
          String sql = "Select Part.*\n" +
                            "FROM (SELECT P.Name AS ProjectName, T.Name as TaskName, T.Id, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY T.Id) AS TaskTime,\n" +
                                        "ROW_NUMBER() OVER(PARTITION BY T.Id ORDER BY P.Name) AS Corr\n" +
                                    "FROM Projects P\n" +
                                    "LEFT JOIN Tasks T ON P.Id = T.AssociatedProject\n" +
                                    "LEFT JOIN Sessions S ON T.Id = S.AssociatedTask\n" +
                                    "WHERE S.StartTime >= CONVERT(datetime2(7), ?)\n" +
                                    "AND S.StartTime <= CONVERT(datetime2(7), ?) \n" +
                            ") Part\n" +
                        "WHERE Corr = 1;";
          try(Connection con = dbc.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, startTime);
            pstmt.setString(2, finishTime);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                String project = rs.getString("ProjectName");
                String task = rs.getString("TaskName");
                long taskTime = rs.getLong("TaskTime");
                Coordinates temp = new Coordinates(project, task, taskTime);
                projCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return projCol;
    }
      
    public ArrayList<Coordinates> getOneProjectForAdmBar(int projectId, String startTime, String finishTime) //For simple barChart
      {
          ArrayList<Coordinates> projCol = new ArrayList();
          String sql = "Select Part.*\n" +
                            "FROM (SELECT P.Name AS ProjectName, T.Name as TaskName, T.Id, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY T.Id) AS TaskTime,\n" +
                                        "ROW_NUMBER() OVER(PARTITION BY T.Id ORDER BY P.Name) AS Corr\n" +
                                    "FROM Projects P\n" +
                                    "LEFT JOIN Tasks T ON P.Id = T.AssociatedProject\n" +
                                    "LEFT JOIN Sessions S ON T.Id = S.AssociatedTask\n" +
                                    "WHERE P.Id = ?\n" +
                                    "AND S.StartTime >= Convert(datetime2(7), ?)\n" +
                                    "AND S.StartTime <= Convert(datetime2(7), ?)\n" +
                            ") Part\n" +
                            "WHERE Corr = 1;";
          try(Connection con = dbc.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, projectId);
            pstmt.setString(2, startTime);
            pstmt.setString(3, finishTime);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                String project = rs.getString("ProjectName");
                String task = rs.getString("TaskName");
                long taskTime = rs.getLong("TaskTime");
                Coordinates temp = new Coordinates(project, task, taskTime);
                projCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return projCol;
      }
            
    public ArrayList<Coordinates> getAllProjectsForUsrBar(int currentUser, String startTime, String finishTime) //For complex barchart
      {
          ArrayList<Coordinates> projCol = new ArrayList();
          String sql = "Select Part.*\n" +
                            "FROM (SELECT P.Name AS ProjectName, T.Name as TaskName, T.Id, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY T.Id) AS TaskTime,\n" +
                                        "ROW_NUMBER() OVER(PARTITION BY T.Id ORDER BY P.Name) AS Corr\n" +
                                    "FROM Projects P\n" +
                                    "JOIN Tasks T ON P.Id = T.AssociatedProject\n" +
                                    "JOIN Sessions S ON T.Id = S.AssociatedTask\n" +
                                    "WHERE S.AssociatedUser = ?\n" +
                                    "AND S.StartTime >= CONVERT(datetime2(7), ?)\n" +
                                    "AND S.StartTime <= CONVERT(datetime2(7), ?) \n" +
                            ") Part\n" +
                        "WHERE Corr = 1;";
          try(Connection con = dbc.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, currentUser);
            pstmt.setString(2, startTime);
            pstmt.setString(3, finishTime);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                String project = rs.getString("ProjectName");
                String task = rs.getString("TaskName");
                long taskTime = rs.getLong("TaskTime");
                Coordinates temp = new Coordinates(project, task, taskTime);
                projCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return projCol;
    }
    
    public ArrayList<Coordinates> getOneProjectForUsrBar(int currentUser, int projectId, String startTime, String finishTime) //For simple barChart
      {
          ArrayList<Coordinates> projCol = new ArrayList();
          String sql = "Select Part.*\n" +
                            "FROM (SELECT P.Name AS ProjectName, T.Name as TaskName, T.Id, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY T.Id) AS TaskTime,\n" +
                                        "ROW_NUMBER() OVER(PARTITION BY T.Id ORDER BY P.Name) AS Corr\n" +
                                    "FROM Projects P\n" +
                                    "JOIN Tasks T ON P.Id = T.AssociatedProject\n" +
                                    "JOIN Sessions S ON T.Id = S.AssociatedTask\n" +
                                    "WHERE S.AssociatedUser = ?\n" +
                                    "AND P.Id = ?\n" +
                                    "AND S.StartTime >= Convert(datetime2(7), ?)\n" +
                                    "AND S.StartTime <= Convert(datetime2(7), ?)\n" +
                            ") Part\n" +
                            "WHERE Corr = 1;";
          try(Connection con = dbc.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, currentUser);
            pstmt.setInt(2, projectId);
            pstmt.setString(3, startTime);
            pstmt.setString(4, finishTime);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                String project = rs.getString("ProjectName");
                String task = rs.getString("TaskName");
                long taskTime = rs.getLong("TaskTime");
                Coordinates temp = new Coordinates(project, task, taskTime);
                projCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return projCol;
      }
     
}
