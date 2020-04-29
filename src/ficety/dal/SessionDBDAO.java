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
import java.time.LocalDate;
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

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class SessionDBDAO {
    private DBConnection dbc;
    private UserDBDAO userDBDao;
    private TaskDBDAO taskDBDao;

    
    
    public SessionDBDAO() {
        dbc = new DBConnection();
      //  taskDBDao = new TaskDBDAO();
        userDBDao = new UserDBDAO();
    }

    
    
     public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, String startTime, String finishTime) { 
    //  Adds a new session to the Session table of the database given the sessions details. Generates an id key    
        String sql = "INSERT INTO Sessions(associatedUser, associatedTask, startTime, finishTime) VALUES (?,?,?,?)";
        Session newSession = new Session(0, associatedUserID, associatedTaskID, startTime, finishTime);
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, associatedUserID);
            pstmt.setInt(2, associatedTaskID);
            pstmt.setString(3, startTime);
            pstmt.setString(4, finishTime);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Session failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newSession.setSessionId((int) generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating Session failed, no ID obtained.");
                } 
                return newSession;
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
     
    public Session getSession(int sessionID) throws SQLException {
        Session session; 
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT * FROM Sessions WHERE id = '" + sessionID + "'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) // While you have something in the results
            {
                int associatedUserID = rs.getInt("AssociatedUser");
                int associatedTaskID = rs.getInt("AssociatedTask");
                String startTime = rs.getString("StartTime");
                String finishTime = rs.getString("FinishTime");
    //            LocalDateTime startTime = sqlStartTime.toLocalDate();  Need to work out time
    //            LocalDateTime startTime = sqlStartTime.toLocalDate();
    //            java.sql.Date startTime = rs.getDate("StartTime");
    //            java.sql.Date finishTime = rs.getDate("FinishTime");
                session = new Session(sessionID, associatedUserID, associatedTaskID, startTime, finishTime);
                return session ;
            }    
        }
        return null ;
    }
    
          
    public List<Session> getAllSessionsOfATask(int taskID) throws SQLException {
        List<Session> allSessionsOfATask = new ArrayList<>();
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT * FROM Tasks WHERE associatedTask = '" + taskID + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) // While you have something in the results
            {
                int sessionID = rs.getInt("id");
                int AssociatedUserID =  rs.getInt("associatedUser");
                int associatedTaskID =  rs.getInt("associatedTask");
                String startTime = rs.getString("startTime");
                String finishTime = rs.getString("finishTime");            
                Session sessionInTask = new Session(sessionID, AssociatedUserID, associatedTaskID, startTime, finishTime);
                allSessionsOfATask.add(sessionInTask); 
            }    
        }
        return allSessionsOfATask ;
    }
          
         
    public List<Integer> getAllSessionIDsOfATask(int taskID) throws SQLException {  
// method only needed for getAllUserIDsAndNamesOfATask. May not need
        List<Integer> allSessionIDsOfATask = new ArrayList<>();
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT id FROM Sessions WHERE associatedTask = '" + taskID + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) // While you have something in the results
            {
                int sessionID = rs.getInt("id");
                allSessionIDsOfATask.add(sessionID); 
            }    
        }
        return allSessionIDsOfATask ;
    } 
         
       
    public void addStartTimeToSession(Session currentSession) { 
    //  Adds a strartTime to a given Session   
        LocalDateTime LDTnow = LocalDateTime.now();
        String startTime = LDTnow.toString();
        currentSession.setStartTime(startTime);
    }

     
    public void addFinishTimeToSession(Session currentSession) throws SQLException { 
    //  Adds a strartTime to a given Session   
        LocalDateTime LDTnow = LocalDateTime.now();
        String finishTime = LDTnow.toString();
        currentSession.setFinishTime(finishTime);
        String startTime = currentSession.getStartTime();
        long[] duration = calculateDurationOfASession(startTime, finishTime);
        int currentTaskID = currentSession.getAssociatedTask();
        Task currentTask = taskDBDao.getTask(currentTaskID);
        
    }
    
    
    private long[] calculateDurationOfASession(String startTime, String finishTime) {
        long[] duration = new long[2];
        LocalDateTime startLDT = LocalDateTime.parse(startTime);
        LocalDateTime finishLDT = LocalDateTime.parse(finishTime);
        LocalDateTime tempLDT = LocalDateTime.from(startLDT);

        long hours = tempLDT.until( finishLDT, ChronoUnit.HOURS );
        tempLDT = tempLDT.plusHours(hours);

        long minutes = tempLDT.until( finishLDT, ChronoUnit.MINUTES );
        tempLDT = tempLDT.plusMinutes( minutes );  // probably don't need this line
        duration[0] = hours;
        duration[1] = minutes;
        return duration;
    }
            
            
            
            
/*    public List<User> getAllUserIDsAndNamesOfATask(int taskID) throws SQLException {
        List<Integer> allSessionsIDsOfATask = new ArrayList<>();
        allSessionsIDsOfATask = getAllSessionIDsOfATask(taskID); 
        if (allSessionsIDsOfATask.size() > 0) {
            for (int i = 0; i < allSessionsIDsOfATask.size(); i++) {            
                try(Connection con = dbc.getConnection()) {
                    String sql = "SELECT id, name FROM Users WHERE associatedUser = '" + taskID + "'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()) // While you have something in the results
                    {
                        int sessionID = rs.getInt("id");
                        int AssociatedUserID = rs.getInt("associatedUser");
                        int associatedTaskID = rs.getInt("associatedTask");
                        String startTime = rs.getString("startTime");
                        String finishTime = rs.getString("finishTime");            
                        Session sessionInTask = new Session(sessionID, AssociatedUserID, associatedTaskID, startTime, finishTime);
                        allUserIDsOfATask.add(sessionInTask); 
                    }    
        }
        return allUserIDsOfATask ;
    }
 */         
     
    public void removeSessionFromDB(Session sessionToDelete) {
    //  Removes a session from the Session table of the database given a Session data object
        String sql = "DELETE FROM Tasks WHERE id = ?";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,sessionToDelete.getSessionId());
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }
      
    
}
