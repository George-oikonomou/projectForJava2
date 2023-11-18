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
        int option, choice;

        do {
            //Picking one option to change a field
            System.out.println("Do you want to Change:\n1) Title\n2) Description\n3) Date & Time or\n4)Exit");
            option = Validate.checkAndReturnIntBetween(1, 4);
            switch (option) {
                case 1: {   //Changing title:
                    System.out.print("\nType the new title:\t");
                    setTitle(Validate.strInput());
                    break;
                }
                case 2: {   //Changing description:
                    System.out.print("\nType the new description:\t");
                    setDescription(Validate.strInput());;
                    break;
                }
                case 3: {   //Changing the date and the time if the user wants to:
                    System.out.print("\nType the new date:\t");
                    if (dateTime.getTime().equals(null)) {  //if the event has no time and only date:
                        System.out.println("\nDo you want to add time?\n1) Yes\n2) No");
                        choice = Validate.checkAndReturnIntBetween(1,2);
                        if (choice == 1)
                            setDateTime(OurDateTime.Functionality.dateAndTime(true));
                        else
                            setDateTime(OurDateTime.Functionality.dateAndTime(false));
                    } else {
                        System.out.println("\nDo you want to remove time?\n1) Yes\n2) No");
                        choice = Validate.checkAndReturnIntBetween(1,2);
                        if (choice == 1)
                            setDateTime(OurDateTime.Functionality.dateAndTime(false));
                        else
                            setDateTime(OurDateTime.Functionality.dateAndTime(true));
                    }
                    break;
                }
            }
        } while (option != 4);
    }

    @Override
    public String toString() {
        return "Event{" +
                "dateTime:" + dateTime +
                "title:" + title +
                "description:" + description +
                '}';
    }
}
