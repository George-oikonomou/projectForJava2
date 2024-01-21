import javax.swing.*;
import java.util.Date;
import java.util.Random;

public abstract class Event {
    private OurDateTime startDate;
    private String title,description;
    private String uuid;


    public Event(OurDateTime startDate, String title, String description) {
        this.startDate = startDate;
        this.title = title;
        this.description = description;
        generateUUID();
    }

    public OurDateTime getStartDate() { return startDate; }
    public void setStartDate(OurDateTime startDate) { this.startDate = startDate; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getUuid() { return uuid; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    private void generateUUID(){
        long timestamp = new Date().getTime();
        int randomPart = 1000 + new Random().nextInt(9000);
        this.uuid      = timestamp + "_" + randomPart + "_" + "@javaTeamGGS";
    }

    public abstract void setPanel();
    public abstract JPanel getPanel();
}