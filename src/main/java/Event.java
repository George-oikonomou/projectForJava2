import net.fortuna.ical4j.model.DateTime;

public class Event {
    private Datetime dateTime;
    private String title;
    private String description;

    public Event(Datetime datetime, String title, String description) {
        this.dateTime = datetime;
        this.title = title;
        this.description = description;
    }

    public Datetime getDateTime() {
        return dateTime;
    }

    public void setDateTime(Datetime dateTime) {
        this.dateTime = dateTime;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
