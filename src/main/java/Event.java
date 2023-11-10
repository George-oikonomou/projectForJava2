import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;

public class Event {
    private DateTime dateTime;
    private String title;
    private String description;


    public Event(DateTime dateTime, String title, String description) {
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
    }


    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
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
