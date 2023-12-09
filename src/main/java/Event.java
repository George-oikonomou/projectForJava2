import java.util.Date;
import java.util.Random;

public abstract class Event {
    private final OurDateTime startDate;
    private final String title,description;
    private String uuid;

    public Event(OurDateTime startDate, String title, String description) {
        this.startDate = startDate;
        this.title = title;
        this.description = description;
        generateUUID();

    }

    public OurDateTime getStartDate() { return startDate; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public String getUuid() {
        return uuid;
    }
    private void generateUUID(){
        long timestamp = new Date().getTime();
        Random random = new Random();
        int randomPart = 1000 + random.nextInt(9000);
        this.uuid = timestamp + "_" + randomPart + "_" + "@javateamGGS";
    }
}

/*
    protected void setTitlePrompt() {
        Validate.print("\nType the new title:\t");
        ourCalendar = getOurCalendar();

        setTitle(Validate.Title(ourCalendar,  (this instanceof Appointment) ? 1 : 2));
    }
    protected void setDescriptionPrompt() {
        Validate.print("\nType the new description:\t");
        setDescription(Validate.strInput());
    }
    protected void setStartDatePrompt() {
        Validate.print("\nType the new date & time:\t");
        setStartDate(OurDateTime.Functionality.dateAndTime());
    }
    public abstract void editEvent();
        private OurCalendar ourCalendar;

    public OurCalendar getOurCalendar() { return ourCalendar; }
    public void setOurCalendar(OurCalendar ourCalendar) { this.ourCalendar = ourCalendar; }
        public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStartDate(OurDateTime startDate) { this.startDate = startDate; }

 */