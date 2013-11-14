package org.chat.api.database;

import org.chat.api.MessageObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MessageStore {

    private static final Logger log = Logger.getLogger(MessageStore.class.getName());

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement stat = null;

    public MessageStore() {

//      Triggers class constructor to initialize driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.severe("Failed to initialize database driver");
        }

//     Create connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/chatapp");
        } catch (SQLException e) {
            log.severe("Failed connect to database");
        }

//      Prepare statement
        try {
            ps = connection.prepareStatement(
                    "insert into messages (Sender, SentTime, message) values (?,?,?);");
        } catch (SQLException e) {
            log.severe("Failed to prepare SQL statement");
        }

        try {
            stat = connection.createStatement();
        } catch (SQLException e) {
            log.severe("Failed to create SQL statement");
        }
    }

    public void close() {
        try {
            log.info("Closing connection");
            connection.close();
        } catch (SQLException e) {
            log.severe("Failed to close database connection");
        }

    }

    public List getMessageHistory() {
        List<List<String>> results = new ArrayList<>();
        ResultSet rs;

        try {
            log.info("Retrieving message history");

//          Execute statement
            rs = stat.executeQuery("select * from messages");
//          Use the ResultSet
            ResultSetMetaData rmd = rs.getMetaData();
            int columns = rmd.getColumnCount();
            while (rs.next()) {
                ArrayList<String> message = new ArrayList<>();
                for (int i = 1; i <= columns; ++i) {
                    message.add(rs.getString("Sender"));
                    message.add(rs.getString("SentTime"));
                    message.add(rs.getString("message"));
                }
                results.add(message);
            }
        } catch (SQLException e) {
            log.warning("Failed to retrieve message history");
        }
        return results;
    }

    public int storeMessage(MessageObject message) {
        log.info("Storing message:\n" + message.toString());
        try {
            ps.setString(1, message.getSender());
            ps.setString(2, message.getSent());
            ps.setString(3, message.getMessage());

            return ps.executeUpdate();
        } catch (SQLException e) {
            log.severe("Failed to store message!");
        }
        return 0;
    }
}
