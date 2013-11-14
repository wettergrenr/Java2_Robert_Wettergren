package org.chat.api;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageObject implements Serializable {
    private static final Logger log = Logger.getLogger(MessageObject.class.getName());

    private String sender;
    private Date sentTime;
    private String message;

    public MessageObject(String sender, String message) {
        this.sender = sender;
        this.message = message;
        sentTime = new Date();
    }
    
    public MessageObject(String sender, String sent, String message) {
        this.sender = sender;
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); //14-Nov-2013 14:43:19
        try {
            this.sentTime = df.parse(sent);
        } catch (ParseException e) {
            log.severe("Illegal date format");
        }
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getSent() {
        return DateFormat.getDateTimeInstance().format(sentTime);
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return String.format("Message from: %s ### Sent at: %s ### %s", sender, sentTime, message);
    }
}
