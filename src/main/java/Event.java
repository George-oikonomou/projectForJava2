public abstract class Event {
    private OurCalendar ourCalendar;
    private OurDateTime dateTime;
    private String title,description;

    public Event(OurDateTime datetime, String title, String description) {
        this.dateTime = datetime;
        this.title = title;
        this.description = description;
    }

    public OurCalendar getOurCalendar() { return ourCalendar; }
    public void setOurCalendar(OurCalendar ourCalendar) { this.ourCalendar = ourCalendar; }

    public OurDateTime getDateTime() { return dateTime; }
    public void setDateTime(OurDateTime dateTime) { this.dateTime = dateTime; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    protected void setTitlePrompt() {
        Validate.print("\nType the new title:\t");
        ourCalendar = getOurCalendar();

        int objectType = (this instanceof Appointment) ? 1 : 2;

        setTitle(Validate.Title(ourCalendar, objectType));
    }
    protected void setDescriptionPrompt() {
        Validate.print("\nType the new description:\t");
        setDescription(Validate.strInput());
    }
    protected void setDateTimePrompt() {
        Validate.print("\nType the new date & time:\t");
        setDateTime(OurDateTime.Functionality.dateAndTime());
    }
    public void editEvent() {}
}
