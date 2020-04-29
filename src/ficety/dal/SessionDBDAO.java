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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ficety.be.Session;
import ficety.be.Task;
import ficety.be.User;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class SessionDBDAO {
    private DBConnection dbc;
    private UserDBDAO userDBDao;
//    private TaskDBDAO taskDBDao;

    
    
    public SessionDBDAO() {
        dbc = new DBConnection();
//        taskDBDao = new TaskDBDAO();
        userDBDao = new UserDBDAO();
    }

    
    
     public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, LocalDateTime startTime) { 
    //  Adds a new session to the Session table of the database given the sessions details. Generates an id key
        LocalDateTime finishTime = null;
        String sql = "INSERT INTO Sessions(associatedUser, associatedTask, startTime, finishTime) VALUES (?,?,?,NULL)";
        Session newSession = new Session(0, associatedUserID, associatedTaskID, startTime, finishTime);
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, associatedUserID);
            pstmt.setInt(2, associatedTaskID);
            Timestamp startTimeStamp = Timestamp.valueOf(startTime);
            pstmt.setTimestamp(3, startTimeStamp);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Session failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int key = (int) generatedKeys.getLong(1);
                    newSession.setSessionId(key);
                    System.out.println("Key is:" + key);
                    return newSession;
                } else {
                    throw new SQLException("Creating Session failed, no ID obtained.");
                } 

            }
        } catch (SQLServerException ex) {
            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public List<Session> getAllSessionsOfATask(int taskID) throws SQLException {
        List<Session> allSessionsOfATask = new ArrayList<>();
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT * FROM Sessions WHERE associatedTask = ?";
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, taskID);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) // While you have something in the results
            {
                int sessionID = rs.getInt("id");
                int AssociatedUserID =  rs.getInt("AssociatedUser");
                int associatedTaskID =  rs.getInt("AssociatedTask");
                LocalDateTime startTime = rs.getTimestamp("StartTime").toLocalDateTime();
                LocalDateTime finishTime = rs.getTimestamp("FinishTime").toLocalDateTime();            
                Session sessionInTask = new Session(sessionID, AssociatedUserID, associatedTaskID, startTime, finishTime);
                allSessionsOfATask.add(sessionInTask); 
            }    
        }
        return allSessionsOfATask ;
    }
     
    public void addFinishTimeToSession(Session currentSession, LocalDateTime finishTime) throws SQLException { 
    //  Adds a finishTime to a given Session   
        String sql ="UPDATE Sessions SET FinishTime = ? WHERE Id = ?;";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            Timestamp finishTimeStamp = Timestamp.valueOf(finishTime);
            System.out.println("Time is now: " + finishTimeStamp);
            pstmt.setTimestamp(1, finishTimeStamp);
            int sessionId = currentSession.getSessionId();
            pstmt.setInt(2, sessionId);
            //pstmt.execute();
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating Session failed, no rows affected.");
            }
        }
        
    }

     
    public void removeSessionFromDB(Session sessionToDelete) {
    //  Removes a session from the Session table of the database given a Session data object
        String sql = "DELETE FROM Sessions WHERE id = ?";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,sessionToDelete.getSessionId());
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }
      
    
}
