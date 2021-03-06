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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Trigger, Filip, Cecillia and Alan
 */
public class SessionDBDAO {
    private DBConnection dbc;
   // private UserDBDAO userDBDao;
    private boolean debug = false;
//    private TaskDBDAO taskDBDao;

    
    
    public SessionDBDAO() {
        dbc = new DBConnection();
//        taskDBDao = new TaskDBDAO();
//        userDBDao = new UserDBDAO();
    }

    
    
     public Session addNewSessionToDB(int associatedUserID, int associatedTaskID, String taskName, LocalDateTime startTime) { 
    //  Adds a new session to the Session table of the database given the sessions details. Generates an id key
        String sql = "INSERT INTO Sessions(associatedUser, associatedTask, startTime, finishTime) VALUES (?,?,?,NULL)";
        Timestamp startTimeStamp = Timestamp.valueOf(startTime);
        Session newSession = new Session(0, associatedUserID, associatedTaskID,startTimeStamp,null,"",taskName);
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, associatedUserID);
            pstmt.setInt(2, associatedTaskID);
            pstmt.setTimestamp(3, startTimeStamp);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating Session failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int key = (int) generatedKeys.getLong(1);
                    newSession.setSessionID(key);
                    debug("Key is:" + key);
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
     
    public List<Session> getAllSessionsOfAUser(int userid){
        List<Session> allSessionsOfATask = new ArrayList<>();
        try(Connection con = dbc.getConnection()) {
            String sql = "SELECT  Sessions.Id, Sessions.AssociatedUser, Sessions.AssociatedTask, Sessions.StartTime, Sessions.FinishTime , SUM(Datediff(SECOND, StartTime, FinishTime)) AS Total, Tasks.Name from Sessions JOIN TASKS ON Sessions.AssociatedTask = Tasks.Id where Sessions.AssociatedUser = ? GROUP BY AssociatedUser,AssociatedTask ,FinishTime ,StartTime ,tasks.name,Sessions.Id ;";
            PreparedStatement pstmt = con.prepareStatement(sql/*, PreparedStatement.RETURN_GENERATED_KEYS*/);
            pstmt.setInt(1, userid);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) // While you have something in the results
            {
                int sessionID = rs.getInt("id");
                int AssociatedUserID =  rs.getInt("AssociatedUser");
                int associatedTaskID =  rs.getInt("AssociatedTask");
                Timestamp startTime = rs.getTimestamp("StartTime");
                Timestamp finishTime = rs.getTimestamp("FinishTime");    
                int time = rs.getInt("Total");
                String taskName = rs.getString("Name");
                //String timee = String.format("%02d:%02d:%02d",time/3600 ,time / 60, time % 60);
                 String timee = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60);
                Session sessionInTask = new Session(sessionID, AssociatedUserID, associatedTaskID, startTime, finishTime,timee,taskName);
                allSessionsOfATask.add(sessionInTask); 
            }    
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allSessionsOfATask ;
    }
     
    public Session addFinishTimeToSession(Session currentSession, LocalDateTime finishTime) throws SQLException { 
    //  Adds a finishTime to a given Session   
        String sql ="UPDATE Sessions SET FinishTime = ? WHERE Id = ?;";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            Timestamp finishTimeStamp = Timestamp.valueOf(finishTime);
            debug("Time is now: " + finishTimeStamp);
            pstmt.setTimestamp(1, finishTimeStamp);
            int sessionId = currentSession.getSessionID();
            pstmt.setInt(2, sessionId);
            //pstmt.execute();
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating Session failed, no rows affected.");
            }
            else
            {
                debug("Session Finishtime set correctly.");
                Timestamp endtime = Timestamp.valueOf(finishTime);
                String finish = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endtime);
                currentSession.setFinishTime(finish);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(currentSession.getStartTime());
                Timestamp starttime = new java.sql.Timestamp(parsedDate.getTime());
                
                long millis = endtime.getTime() - starttime.getTime();
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                currentSession.setHours(hms);
            }
        } catch (ParseException ex) {
            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentSession;
    }
    
//        public Session editSession(Session sessionToEdit, LocalDateTime startTime, LocalDateTime finishTime){ 
//    //  Adds a finishTime to a given Session   
//        String sql ="UPDATE Sessions SET StartTime = ?, FinishTime = ? WHERE Id = ?;";
//        try (Connection con = dbc.getConnection()) {
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            
//            Timestamp startTimeStamp = Timestamp.valueOf(startTime);
//            debug("Time for start: " + startTimeStamp);
//            pstmt.setTimestamp(1, startTimeStamp);
//            
//            Timestamp finishTimeStamp = Timestamp.valueOf(finishTime);
//            debug("Time for finish: " + finishTimeStamp);
//            pstmt.setTimestamp(2, finishTimeStamp);
//            
//            int sessionId = sessionToEdit.getSessionID();
//            pstmt.setInt(3, sessionId);
//            //pstmt.execute();
//            int affectedRows = pstmt.executeUpdate();
//            if (affectedRows == 0) {
//                throw new SQLException("Updating Session failed, no rows affected.");
//            }
//            else
//            {
//                debug("Session was edited correctly.");
//                sessionToEdit.setStartTime(startTimeStamp);
//                sessionToEdit.setFinishTime(finishTimeStamp);
//                return sessionToEdit;
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(SessionDBDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
public Session editSession (Session sessionToEdit, String startTime, String finishTime,int id) throws ParseException { 
    //  Edits a Task in the Task table of the database given the Projects new details.  
        String sql = "UPDATE Sessions SET startTime = ?, finishTime = ? WHERE id = ?";
        try ( Connection con = dbc.getConnection()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = dateFormat.parse(startTime);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate2 = dateFormat2.parse(finishTime);
            Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());
            long millis = timestamp2.getTime() - timestamp.getTime();
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            PreparedStatement pstmt = con.prepareStatement(sql);
            //Set parameter values.
            pstmt.setTimestamp(1, timestamp);
            pstmt.setTimestamp(2, timestamp2);
            pstmt.setInt(3, sessionToEdit.getSessionID());
            pstmt.executeUpdate();  //Execute SQL query.
            sessionToEdit.setStartTime(startTime);
            sessionToEdit.setFinishTime(finishTime);
            sessionToEdit.setHours(hms);
            return sessionToEdit;
        } catch (SQLServerException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TaskDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
    public void removeSessionFromDB(Session sessionToDelete) {
    //  Removes a session from the Session table of the database given a Session data object
        int deletableID = sessionToDelete.getSessionID();
        String sql = "DELETE FROM Sessions WHERE id = ?";
        try (Connection con = dbc.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, deletableID);
            pstmt.execute();
            debug("Session was deleted");
        } catch (SQLException ex) {
            System.out.println("Exception " + ex);
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
