package Models;

import javax.swing.*;
import java.util.Date;
import java.util.Random;

public abstract class Event {
    private OurDateTime startDate;
    private String title,description;
    private String uuid;
    private Boolean isNotified;
    private final String fileName;


    public String getFileName() {
        return fileName;
    }

    public Event(OurDateTime startDate, String title, String description, String fileName) {
        this.startDate = startDate;
        this.title = title;
        this.description = description;
        this.fileName = fileName;
        this.isNotified = false;
        generateUUID();
    }

    public OurDateTime getStartDate() { return startDate; }
    public void setStartDate(OurDateTime startDate) { this.startDate = startDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUuid() { return uuid; }

    /**
     * description: generates a unique id for the event which is used to identify the event
     */
    private void generateUUID(){
        long timestamp = new Date().getTime();
        int randomPart = 1000 + new Random().nextInt(9000);
        this.uuid      = timestamp + "_" + randomPart + "_" + "@javaTeamGGS";
    }


    /*
     * description: returns true if the event is notified meaning that the user has been notified about the event (reminder)
     */
    public Boolean isNotified() {
        return isNotified;
    }

    /*
     * description: sets the notified status of the event
     */
    public void setNotified(Boolean notified) {
        isNotified = notified;
    }

    /*
     * description: sets the panel of the event
     */
    public abstract void setPanel();

    /*
     * description: returns the panel of the event
     */
    public abstract JPanel getPanel();
}