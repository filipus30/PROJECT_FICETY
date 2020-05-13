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
import ficety.be.Client;
import ficety.be.Coordinates;

/**
 *
 * @author macos
 */
public class ClientDBDAO {
    private DBConnection dbc;
    
    
    public ClientDBDAO() {
        dbc = new DBConnection();
    }
    
    public Client addNewClientToDB(String clientName,float standardRate,String logoImgLocation,String email) { 
    //  Adds a new Client to the DB, and returns the updated Client to the GUI
        String sql = "INSERT INTO Clients(Name, logoImgLocation, standardRate, email) VALUES (?,?,?,?)";
        Client newClient = new Client(0,clientName,logoImgLocation,standardRate,email);
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, clientName);
            pstmt.setString(2, logoImgLocation);
            pstmt.setFloat(3, standardRate);
            pstmt.setString(4, email);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Client failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newClient.setId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating Client failed, no ID obtained.");
                } 
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newClient;
    }
    
     public ArrayList<Client> getAllClients() {
    //  Returns all Clients  
        ArrayList<Client> allClients = new ArrayList<>();
        try(Connection con = dbc.getConnection()){
            String sql = "SELECT part.* " +
                            "FROM ( " +
                                    "SELECT C.Id, C.Name, C.Email, C.StandardRate, C.LogoImgLocation, Count(P.Id) OVER (PARTITION BY C.Id) AS PNr, "+ 
                                                "ROW_NUMBER() OVER (PARTITION BY C.Id ORDER BY C.Name) AS Corr " +
                                        "FROM Clients C " +
                                        "LEFT JOIN Projects P ON C.Id = P.AssociatedClient) part " +
                                    "WHERE part.Corr=1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) //While you have something in the results
            {
                int clientID =  rs.getInt("id");
                String clientName = rs.getString("name");
                String logoImgLocation = rs.getString("logoImgLocation");
                float standardRate = rs.getFloat("standardRate");
                String email = rs.getString("email");
                int projectNr = rs.getInt("PNr");
                Client tempClient = new Client(clientID,clientName,logoImgLocation,standardRate,email);
                tempClient.setProjectNr(projectNr);
                allClients.add(tempClient); 
            }    
        } catch (SQLException ex) {
            Logger.getLogger(ClientDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allClients;
    }
    
    public Client editClient (Client editedClient,String name,float standardRate,String logoImgLocation, String email) { 
    //  Edits a client  
        String sql = "UPDATE Clients SET Name = ?, StandardRate = ?, LogoImgLocation = ?, Email = ? WHERE Id = ?";
        try ( Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setFloat(2, standardRate);
            pstmt.setString(3, logoImgLocation);
            pstmt.setString(4, email);
            pstmt.setInt(5, editedClient.getId());
            pstmt.executeUpdate();  //Execute SQL query.
            editedClient.setClientName(name);
            editedClient.setImgLocation(logoImgLocation);
            editedClient.setStandardRate(standardRate);
            editedClient.setEmail(email);
            return editedClient;
        } catch (SQLServerException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
    public void deleteClient(Client clientToDelete){
    //  Delete specific Client
        try(Connection con = dbc.getConnection()){
            String sql = "DELETE FROM Clients WHERE Id = ?";
             PreparedStatement pstmt = con.prepareStatement(sql);   
             pstmt.setInt(1,clientToDelete.getId());
             pstmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
     public ArrayList<Coordinates> getSingleClientForAdminGraph(String startTime,String finishTime,int clientId)
      {
          ArrayList<Coordinates> list = new ArrayList();
       String sql = "Select Part.*\n" +
"    FROM (SELECT Temp.DayTime, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY Temp.dayTime) AS UserTime,\n" +
"   			 ROW_NUMBER() OVER(PARTITION BY Temp.dayTime ORDER BY Temp.dayTime) AS Corr\n" +
"   		 FROM Sessions S\n" +
"   		 JOIN (Select Id, DAY(StartTime) AS dayTime FROM Sessions) Temp ON Temp.Id = S.Id\n" +
"   		 JOIN Tasks T ON T.Id = S.AssociatedTask\n" +
"   		 JOIN Projects P ON P.Id = T.AssociatedProject\n" +
"   		 JOIN Clients C ON C.Id = P.AssociatedClient\n" +
"   		 WHERE S.StartTime >= Convert(datetime2(7), ?)\n" +
"   		 AND S.FinishTime <= Convert(datetime2(7), ?)\n" +
"   		 AND C.Id = ?\n" +
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
        pstmt.setInt(3, clientId);
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
    
      public ArrayList<Coordinates> getAllClientsForAdminGraph(String startTime,String finishTime)
      {
          ArrayList<Coordinates> list = new ArrayList();
       String sql = "Select Part.*\n" +
"    FROM (SELECT Temp.DayTime, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY Temp.dayTime) AS UserTime,\n" +
"   			 ROW_NUMBER() OVER(PARTITION BY Temp.dayTime ORDER BY Temp.dayTime) AS Corr\n" +
"   		 FROM Sessions S\n" +
"   		 JOIN (Select Id, DAY(StartTime) AS dayTime FROM Sessions) Temp ON Temp.Id = S.Id\n" +
"   		 WHERE S.StartTime >= Convert(datetime2(7), ?)\n" +
"   		 AND S.FinishTime <= Convert(datetime2(7), ?)\n" +
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
      
    public ArrayList<Coordinates> getAllClientsForAdmBar(String startTime, String finishTime) //For complex barchart top is user sub is project
      {
          ArrayList<Coordinates> cliCol = new ArrayList();
          String sql = "Select Part.*\n" +
                        "FROM (SELECT P.Name AS ProjectName, C.Name as ClientName, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY P.Id) AS ProjectTime,\n" +
                                        "ROW_NUMBER() OVER(PARTITION BY P.Id ORDER BY C.Name) AS Corr\n" +
                                "FROM Clients C\n" +
                                "LEFT JOIN Projects P ON C.Id = P.AssociatedClient\n" +
                                "LEFT JOIN Tasks T ON P.Id = T.AssociatedProject\n" +
                                "LEFT JOIN Sessions S ON T.Id = S.AssociatedTask\n" +
                                "WHERE S.StartTime >= Convert(datetime2(7), ?)\n" +
                                "AND S.StartTime <= Convert(datetime2(7), ?)\n" +
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
                String client = rs.getString("ClientName");
                String project = rs.getString("ProjectName");
                long taskTime = rs.getLong("ProjectTime");
                Coordinates temp = new Coordinates(client, project, taskTime);
                cliCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return cliCol;
    }
    
    public ArrayList<Coordinates> getOneClientForAdmBar(int clientId, String startTime, String finishTime) //For complex barchart top is project sub is user.
      {
          ArrayList<Coordinates> cliCol = new ArrayList();
          String sql = "Select Part.*\n" +
                        "FROM (SELECT P.Name AS ProjectName, C.Name as ClientName, S.AssociatedUser, U.Name as UserName, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY S.AssociatedUser) AS UserTime,\n" +
                                    "ROW_NUMBER() OVER(PARTITION BY S.AssociatedUser ORDER BY P.Name) AS Corr\n" +
                                "FROM Clients C\n" +
                                "LEFT JOIN Projects P ON C.Id = P.AssociatedClient\n" +
                                "LEFT JOIN Tasks T ON P.Id = T.AssociatedProject\n" +
                                "LEFT JOIN Sessions S ON T.Id = S.AssociatedTask\n" +
                                "Join Users U ON S.AssociatedUser = U.Id\n" +
                                "WHERE S.StartTime >= Convert(datetime2(7), ?)\n" +
                                "WHERE S.StartTime <= Convert(datetime2(7), ?)\n" +
                                "AND C.Id = 54\n" +
                        ") Part\n" +
                        "WHERE Corr = 1;";
          try(Connection con = dbc.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, startTime);
            pstmt.setString(2, finishTime);
            pstmt.setInt(3, clientId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                String project = rs.getString("ProjectName");
                String user = rs.getString("UserName");
                long taskTime = rs.getLong("UserTime");
                Coordinates temp = new Coordinates(project, user, taskTime);
                cliCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return cliCol;
    }
      
}

