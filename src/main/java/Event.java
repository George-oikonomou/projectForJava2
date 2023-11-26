public class Event {
    private OurCalendar ourCalendar;
    private OurDateTime dateTime;
    private String title;
    private String description;

    public Event(OurDateTime datetime, String title, String description) {
        this.dateTime = datetime;
        this.title = title;
        this.description = description;
    }

    public OurCalendar getOurCalendar() {
        return ourCalendar;
    }
    public void setOurCalendar(OurCalendar ourCalendar) {this.ourCalendar = ourCalendar;}

    public OurDateTime getDateTime() {return dateTime;}
    public void setDateTime(OurDateTime dateTime) {this.dateTime = dateTime;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    protected void setTitlePrompt() {
        Validate.print("\nType the new title:\t");
        ourCalendar = getOurCalendar();

        int objectType = (this instanceof Appointment)
                   ? 2 : (this instanceof Project)
                   ? 3 : 1;

        setTitle(Validate.Title(ourCalendar, objectType));
    }
    protected void setDescriptionPrompt() {
        Validate.print("\nType the new description:\t");
        setDescription(Validate.strInput());
    }
    protected void setDateTimePrompt(boolean withTime) {
        Validate.print("\nType the new date & time:\t");
        setDateTime(OurDateTime.Functionality.dateAndTime(withTime));
    }

    public void editEvent() {
        int option;

        do {
            //Picking one option to change a field
            Validate.println("""
                    Do you want to Change:
                    1) Title
                    2) Description
                    3) Date
                    4) Date & Time
                    5) Or Exit""");
            option = Validate.checkAndReturnIntBetween(1, 5);
            switch (option) {
                case 1 -> setTitlePrompt();
                case 2 -> setDescriptionPrompt();
                case 3 -> setDateTimePrompt(false);
                case 4 -> setDateTimePrompt(true);
            }
        } while (option != 5);
    }

    @Override
    public String toString() {
        return """
            Event:
                dateTime: %s
                title: %s
                description: %s
            """.formatted(dateTime, title, description);
    }
}