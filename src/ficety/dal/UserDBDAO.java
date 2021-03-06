/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficety.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import ficety.be.Coordinates;
import ficety.be.LoggedInUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import ficety.be.User;
import java.util.ArrayList;


/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class UserDBDAO {
    private DBConnection dbc;
    private LoggedInUser lUser;
    private boolean debug = false;
    
    public UserDBDAO() {
        dbc = new DBConnection();
    } 
    
    
    public User addNewUserToDB(String userName, String email, String password, float salary, boolean isAdmin) { 
    //  Adds a new user to the User table of the database given the users details. Generated an id key    
        String sql = "INSERT INTO Users(name, email, password, salary, admin) VALUES (?,?,?,?,?)";
        User newUser = new User(0, userName, email, password, salary, isAdmin);
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, userName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setFloat(4, salary);
            int admin = 0;
            if(isAdmin == true)
                admin = 1;
            pstmt.setInt(5, admin);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newUser.setUserId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                } 
                return newUser;
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    
    public User getUser(int userID) {
    //  Returns a spacific user data object given their user id
        User user = null;
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT userName, email, password, salary, isAdmin FROM Users WHERE id =?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) //While you have something in the results
            {
                String userName = rs.getString("userName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Float salary = rs.getFloat("salary");
                int admin = rs.getInt("admin");
                boolean isAdmin = false;
                if(admin == 1)
                    isAdmin = true;
               user = new User(userID, userName, email, password, salary, isAdmin); 
               return user;
            }    
        } catch (SQLException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }   
 
         
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin) { 
    //  Edits a user in the User table of the database given the users new details.  
        int userId = userToEdit.getUserId();
        try (  //Get a connection to the database.
            Connection con = dbc.getConnection()) {  
            //Create a prepared statement.
            String sql = "UPDATE Users SET Name = ?, Email = ?, Password = ?, Salary = ? , Admin = ? WHERE Id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setString(1, userName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setFloat(4, salary);
            int admin = 0;
            if(isAdmin == true)
                admin = 1;
            pstmt.setInt(5, admin);
            pstmt.setInt(6, userId);
            //Execute SQL query.
            pstmt.executeUpdate();
            userToEdit.setUserName(userName);
            userToEdit.setEmail(email);
            userToEdit.setPassword(password);   
            userToEdit.setSalary(salary);
            userToEdit.setIsAdmin(isAdmin);
            return userToEdit;
        } catch (SQLServerException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

         
    public void removeUserFromDB(User userToDelete) {
    //  Removes a user from the Users table of the database given a User data object
        String stat = "DELETE FROM Sessions WHERE AssociatedUser = ? ; DELETE FROM Users WHERE Id = ?";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setInt(1,userToDelete.getUserId());
            stmt.setInt(2,userToDelete.getUserId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }
    
    public int checkUserLogin(String email, String password) throws SQLException {  
        
        try(Connection con = dbc.getConnection()){
            String SQLStmt = "SELECT * FROM USERS WHERE email = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(SQLStmt);   
            pstmt.setString(1,email);
            pstmt.setString(2,password);
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();
            if(rs != null) //If there is an entry
            {
                while(rs.next())
                {
                    int userId = rs.getInt("id");
                    String userEmail = rs.getString("email");
                    String userName = rs.getString("name");
                    String userPassword = rs.getString("password");
                    boolean isAdmin = rs.getBoolean("admin");
                    float userSalary = rs.getFloat("salary");
                    User tempLogin = new User(userId, userName, userEmail, userPassword, userSalary, isAdmin);

                    lUser = LoggedInUser.getInstance();
                    lUser.setId(userId);
                    lUser.setEmail(userEmail);
                    lUser.setName(userName);
                    lUser.setPassword(password);
                    lUser.setSalary((int) userSalary);
                    lUser.setAdmin(isAdmin);
                    debug("" + tempLogin.getIsAdmin());
                    if(tempLogin.getIsAdmin() == true) {
                        return 1; //user and password match = true
                    }
                    else if(tempLogin.getIsAdmin() == false) {
                        return 2;
                    }
                }
            } else {
                return 0;
            }
        }
        return 4; //this should never happen.
    }
    
    public ArrayList<User> getAllUsers() {
    //  Returns a list of all Users, with their respective totalTime and Billable time.
        ArrayList<User> allUsers= new ArrayList();
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT Part.* FROM " +
                                        "(SELECT U.Id, U.Name AS UName, U.Email, U.Password, U.Salary, U.Admin, " +
                                            "temp.TotalTime, temp2.BillableTime, " +
                                            "ROW_NUMBER() OVER(PARTITION BY U.Id ORDER BY U.Name) AS Corr " +
                                        "FROM Users U " +
                                        "LEFT JOIN Sessions ON U.Id = Sessions.AssociatedUser " +
                                        "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY Users.Id) AS TotalTime, " +
                                                            "Users.Name " +
                                                    "FROM Sessions " +
                                                    "JOIN Tasks ON  Sessions.AssociatedTask = Tasks.Id " +
                                                    "JOIN Projects ON Projects.Id = Tasks.AssociatedProject " +
                                                    "JOIN Users ON Users.Id = Sessions.AssociatedUser " +
                                            ") temp ON temp.Name = U.Name " +
                                        "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY U.Id) AS BillableTime, " +
                                                            "U.Name " +
                                                    "FROM Sessions " +
                                                    "JOIN Tasks ON Tasks.Id = Sessions.AssociatedTask " +
                                                    "JOIN Users U ON U.Id = Sessions.AssociatedUser " +
                                                    "WHERE Tasks.Billable = 1 " +
                                            ")temp2 ON temp2.Name = U.Name " +
                                ")Part " +
                        "WHERE part.Corr=1;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) //While you have something in the results
            {
                int userID = rs.getInt("Id");
                String userName = rs.getString("UName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                Float salary = rs.getFloat("Salary");
                int admin = rs.getInt("Admin");
                boolean isAdmin = false;
                if(admin == 1)
                    isAdmin = true;
               User tempUser = new User(userID, userName, email, password, salary, isAdmin);
               long time = rs.getLong("TotalTime");
               tempUser.setTotalTime(time);
               String niceTime = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60);
               tempUser.setNiceTime(niceTime);
               long billableTime = rs.getInt("BillableTime");
               String niceBillable = String.format("%02d:%02d:%02d", billableTime / 3600, (billableTime % 3600) / 60, billableTime % 60);
               tempUser.setUserBillableTime(niceBillable);
               allUsers.add(tempUser);
            }    
        } catch (SQLException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUsers;
    }   
        
    private void debug(String msg)
    {
        if(debug == true)
        {
            System.out.println(msg);
        }
    }
    
    public ArrayList<Coordinates> getAllUsersForAdmBar(String startTime, String finishTime) //For simple barchart, bar is usertime.
      {
          ArrayList<Coordinates> usrCol = new ArrayList();
          String sql = "Select Part.*\n" +
                        "FROM (SELECT U.Name as UserName, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY U.Id) AS UserTime,\n" +
                                    "ROW_NUMBER() OVER(PARTITION BY U.Id ORDER BY U.Name) AS Corr\n" +
                                "FROM Users U\n" +
                                "LEFT JOIN Sessions S ON U.Id = S.AssociatedUser\n" +
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
                String subUser = rs.getString("UserName");
                String user = "N/A";
                long userTime = rs.getLong("UserTime");
                Coordinates temp = new Coordinates(user, subUser, userTime);
                usrCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return usrCol;
    }
    
    public ArrayList<Coordinates> getOneUserForAdmBar(int userId, String startTime, String finishTime) //For complex barchart, topBar is user subBar is taskName.
      {
          ArrayList<Coordinates> usrCol = new ArrayList();
          String sql = "Select Part.*\n" +
                        "FROM (SELECT T.Name AS TaskName, U.Name as UserName, SUM(DateDiff(SECOND, S.StartTime, S.FinishTime)) OVER(Partition BY T.Id) AS TaskTime,\n" +
                                    "ROW_NUMBER() OVER(PARTITION BY T.Id ORDER BY U.Name) AS Corr\n" +
                                "FROM Users U\n" +
                                "LEFT JOIN Sessions S ON U.Id = S.AssociatedUser\n" +
                                "JOIN Tasks T ON T.Id = S.AssociatedTask\n" +
                                "WHERE S.StartTime >= Convert(datetime2(7), ?)\n" +
                                "AND S.StartTime <= Convert(datetime2(7), ?)\n" +
                                "AND U.Id = ?\n" +
                        ") Part\n" +
                        "WHERE Corr = 1;";
          try(Connection con = dbc.getConnection())
          {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, startTime);
            pstmt.setString(2, finishTime);
            pstmt.setInt(3, userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                String user = rs.getString("UserName");
                String task = rs.getString("TaskName");
                long taskTime = rs.getLong("TaskTime");
                Coordinates temp = new Coordinates(user, task, taskTime);
                usrCol.add(temp);
            }
          } catch (SQLException ex) {
            Logger.getLogger(ProjectDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          return usrCol;
    }    
    public ArrayList<User> getAllUsersForOverview(String startTime, String finishTime) {
    //  Returns a list of all Users, with their respective totalTime and Billable time, within a timespan.
        ArrayList<User> allUsers= new ArrayList();
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT Part.* FROM " +
                                        "(SELECT U.Id, U.Name AS UName, U.Email, U.Password, U.Salary, U.Admin, " +
                                            "temp.TotalTime, temp2.BillableTime, " +
                                            "ROW_NUMBER() OVER(PARTITION BY U.Id ORDER BY U.Name) AS Corr\n" +
                                        "FROM Users U " +
                                        "LEFT JOIN Sessions ON U.Id = Sessions.AssociatedUser " +
                                        "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY Users.Id) AS TotalTime, " +
                                                            "Users.Name " +
                                                    "FROM Sessions " +
                                                    "JOIN Tasks ON  Sessions.AssociatedTask = Tasks.Id " +
                                                    "JOIN Projects ON Projects.Id = Tasks.AssociatedProject " +
                                                    "JOIN Users ON Users.Id = Sessions.AssociatedUser " +
                                                    "WHERE Sessions.StartTime >= Convert(datetime2(7), ?) " +
                                                    "AND Sessions.StartTime <= Convert(datetime2(7), ?) " +
                                            ") temp ON temp.Name = U.Name " +
                                        "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY U.Id) AS BillableTime, " +
                                                            "U.Name " +
                                                    "FROM Sessions " +
                                                    "JOIN Tasks ON Tasks.Id = Sessions.AssociatedTask " +
                                                    "JOIN Users U ON U.Id = Sessions.AssociatedUser " +
                                                    "WHERE Tasks.Billable = 1 " +
                                                    "AND Sessions.StartTime >= Convert(datetime2(7), ?) " +
                                                    "AND Sessions.StartTime <= Convert(datetime2(7), ?) " +
                                            ")temp2 ON temp2.Name = U.Name " +
                                ")Part " +
                        "WHERE part.Corr=1;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, startTime);
            pstmt.setString(2, finishTime);
            pstmt.setString(3, startTime);
            pstmt.setString(4, finishTime);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) //While you have something in the results
            {
                int userID = rs.getInt("Id");
                String userName = rs.getString("UName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                Float salary = rs.getFloat("Salary");
                int admin = rs.getInt("Admin");
                boolean isAdmin = false;
                if(admin == 1)
                    isAdmin = true;
               User tempUser = new User(userID, userName, email, password, salary, isAdmin);
               long time = rs.getLong("TotalTime");
               tempUser.setTotalTime(time);
               String niceTime = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60);
               tempUser.setNiceTime(niceTime);
               long billableTime = rs.getInt("BillableTime");
               String niceBillable = String.format("%02d:%02d:%02d", billableTime / 3600, (billableTime % 3600) / 60, billableTime % 60);
               tempUser.setUserBillableTime(niceBillable);
               allUsers.add(tempUser);
            }    
        } catch (SQLException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allUsers;
    }
    public User getOneUserForOverview(int userId, String startTime, String finishTime) {
    //  Returns a single user with totalTime and Billable time, within a timespan.
        User theUser = null;
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT U.Id, U.Name AS UName, U.Email, U.Password, U.Salary, U.Admin, " +
                                    "temp.TotalTime, temp2.BillableTime " +
                        "FROM Users U " +
                        "LEFT JOIN Sessions ON U.Id = Sessions.AssociatedUser " +
                        "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY Users.Id) AS TotalTime, " +
                                        "Users.Name " +
                                    "FROM Sessions " +
                                    "JOIN Tasks ON  Sessions.AssociatedTask = Tasks.Id " +
                                    "JOIN Projects ON Projects.Id = Tasks.AssociatedProject " +
                                    "JOIN Users ON Users.Id = Sessions.AssociatedUser " +
                                    "WHERE Sessions.StartTime >= Convert(datetime2(7), ?) " +
                                    "AND Sessions.StartTime <= Convert(datetime2(7), ?) " +
                            ") temp ON temp.Name = U.Name " +
                        "LEFT JOIN (Select Sessions.Id , Sum(DateDiff(SECOND, StartTime, FinishTime)) OVER(Partition BY U.Id) AS BillableTime, " +
                                            "U.Name " +
                                    "FROM Sessions " +
                                    "JOIN Tasks ON Tasks.Id = Sessions.AssociatedTask " +
                                    "JOIN Users U ON U.Id = Sessions.AssociatedUser " +
                                    "WHERE Tasks.Billable = 1 " +
                                    "AND Sessions.StartTime >= Convert(datetime2(7), ?) " +
                                    "AND Sessions.StartTime <= Convert(datetime2(7), ?) " +
                            ")temp2 ON temp2.Name = U.Name " +
                        "WHERE U.Id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, startTime);
            pstmt.setString(2, finishTime);
            pstmt.setString(3, startTime);
            pstmt.setString(4, finishTime);
            pstmt.setInt(5, userId);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) //While you have something in the results
            {
                int userID = rs.getInt("Id");
                String userName = rs.getString("UName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                Float salary = rs.getFloat("Salary");
                int admin = rs.getInt("Admin");
                boolean isAdmin = false;
                if(admin == 1)
                    isAdmin = true;
               theUser = new User(userID, userName, email, password, salary, isAdmin);
               long time = rs.getLong("TotalTime");
               theUser.setTotalTime(time);
               String niceTime = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60);
               theUser.setNiceTime(niceTime);
               long billableTime = rs.getInt("BillableTime");
               String niceBillable = String.format("%02d:%02d:%02d", billableTime / 3600, (billableTime % 3600) / 60, billableTime % 60);
               theUser.setUserBillableTime(niceBillable);
               
            }    
        } catch (SQLException ex) {
            Logger.getLogger(UserDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return theUser;
    } 
}
