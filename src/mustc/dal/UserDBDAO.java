/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mustc.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mustc.be.LoggedInUser;
import mustc.be.User;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class UserDBDAO {
    private DBConnection dbc;

    
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
     
    
    public User getUser(int userID) throws SQLException {
    //  Returns a spacific user data object given their user id
        User user = null;
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT userName, email, password, salary, isAdmin FROM Users WHERE id ='" + userID + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
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
            }    
        }
        return user;
    }   
 
         
    public User editUser (User userToEdit, String userName, String email, String password, Float salary, boolean isAdmin) { 
    //  Edits a user in the User table of the database given the users new details.  
        try (  //Get a connection to the database.
            Connection con = dbc.getConnection()) {  
            //Create a prepared statement.
            String sql = "UPDATE Users SET userName = ?, email = ?, password = ?, salary = ? , Admin = ? WHERE email = ?";
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
        String stat = "DELETE FROM Users WHERE id =?";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(stat);
            stmt.setInt(1,userToDelete.getUserId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }
    
    public int checkUserLogin(String email, String password) throws SQLException {  
        User tempLogin = null;
        try(Connection con = dbc.getConnection()){
            String SQLStmt = "SELECT * FROM USERS WHERE email = ? AND password = ?";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQLStmt);
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
                    tempLogin = new User(userId, userName, userEmail, userPassword, userSalary, isAdmin);

                    LoggedInUser lUser = LoggedInUser.getInstance();
                    lUser.setId(userId);
                    lUser.setEmail(userEmail);
                    lUser.setName(userName);
                    lUser.setPassword(password);
                    lUser.setSalary((int) userSalary);
                    if(tempLogin.getIsAdmin()) {
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
}
