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
                case 1: {   //Changing title:
                    Validate.print("\nType the new title:\t");
                    setTitle(Validate.strInput());
                    break;
                }
                case 2: {   //Changing description:
                    Validate.print("\nType the new description:\t");
                    setDescription(Validate.strInput());
                    break;
                }
                case 3: {   //Changing the date and the time if the user wants to:
                    Validate.print("\nType the new date:\t");
                    setDateTime(OurDateTime.Functionality.dateAndTime(false));
                    break;
                }
                case 4: {
                    Validate.print("\nType the new date and time:\t");
                    setDateTime(OurDateTime.Functionality.dateAndTime(true));
                    break;
                }
                
            }
        } while (option != 5);
    }

    @Override
    public String toString() {
        return "Event:\n" +
                "\tdateTime:" + dateTime + "\n" +
                "\ttitle:" + title + "\n" +
                "\tdescription:" + description + "\n";
    }
}
