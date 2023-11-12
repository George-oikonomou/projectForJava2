public class Event {
    private OurDateTime dateTime;
    private String title;
    private String description;

    public Event(OurDateTime datetime, String title, String description) {
        this.dateTime = datetime;
        this.title = title;
        this.description = description;
    }

    public OurDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OurDateTime dateTime) {
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
