import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Version;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class OurCalendar {

    private ArrayList<Event> events;//has all the events



    //if the file does not exist the new calendar will have these values
    private Version version = new Version("VERSION","2.0");
    private ProdId prodId = new ProdId("-//java project team//java calendar//EN");
    private CalScale calScale = new CalScale(CalScale.VALUE_GREGORIAN);

    public OurCalendar() { this.events = new ArrayList<>(); }

    public ArrayList<Event> getEvents() { return events; }
    public void setEvents(ArrayList<Event> events) { this.events = events; }

    public Version getVersion() { return version;}
    public void setVersion(Version version) {this.version = version;}

    public ProdId getProdId() { return prodId; }
    public void setProdId(ProdId prodId) { this.prodId = prodId; }

    public CalScale getCalScale() { return calScale; }
    public void setCalScale(CalScale calScale) { this.calScale = calScale; }

    public void addEvents() {
        int choice;
        String title, description;
        OurDateTime startDate, endDate, due;

        //NEW EVENT:
        Validate.println("""
                Make a new:
                    1) Appointment
                    2) Project
                """);
        choice = Validate.checkAndReturnIntBetween(1, 2);
        //Title
        Validate.print("\nTitle:\t");
        title = Validate.Title(this, choice);
        //Description
        Validate.print("\nDescription:\t");
        description = Validate.strInput();

        //Adding one event to the arraylist
        System.out.println("Enter the date and time that the event starts");
        startDate = OurDateTime.Functionality.dateAndTime(); //Date & Time
        Validate.println("");
        if (choice == 1) {
            System.out.println("Enter date and time that the event ends");

            endDate = Validate.DateTime(startDate);
            Appointment newAppointment = new Appointment(startDate,endDate, title, description);

          events.add(newAppointment);
        } else {//Date & Time
            Validate.print("Due:\t");
            due = Validate.DateTime(startDate);

            Validate.println("");


            Project newProject = new Project(title, description, due, Status.VTODO_NEEDS_ACTION);
            events.add(newProject);
        }
    }

    public static void sortList(ArrayList<Event> events) {
        int n = events.size();

        for (int i = 1; i < n; ++i) {
            Event keyEvent = events.get(i);

            int j = i - 1;

            while (j >= 0 && compareEvents(events.get(j), keyEvent) > 0) {
                events.set(j + 1, events.get(j));
                j = j - 1;
            }
            events.set(j + 1, keyEvent);
        }
    }

    private static long compareEvents(Event event1, Event event2) {
        OurDateTime startDate1 = (event1 instanceof Project) ? ((Project) event1).getDue() : event1.getStartDate();
        OurDateTime startDate2 = (event2 instanceof Project) ? ((Project) event2).getDue() : event2.getStartDate();

        return startDate1.getCalculationFormat() - startDate2.getCalculationFormat();
    }

    private void timePeriod(long maxTime, long minTime, int code) {     //code 2 is for upcoming events this week, code 3 is for old events this week, code 1 is for the other prints
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        DayOfWeek dayOfWeek = realDateTime.getDayOfWeek();
        int realDay = realDateTime.getDay();
        int eventDay;
        long eventFormat;
        for (Event event : events) {

            if (event instanceof Project) {
                 eventDay = ((Project) event).getDue().getDay();
                 eventFormat = ((Project) event).getDue().getCalculationFormat();
            } else{
                 eventDay = event.getStartDate().getDay();
                 eventFormat = event.getStartDate().getCalculationFormat();
            }

            if (eventFormat >= minTime && eventFormat < maxTime) { //if the event is between minimum and maximum time
                boolean isUpcoming = (code == 2 && (dayOfWeek.getValue() + eventDay - realDay) <= 7);//if the event is upcoming
                boolean isOld = (code == 3 && (1 + eventDay - realDay) <= dayOfWeek.getValue());

                if (isUpcoming || isOld || code == 1) Validate.println(event);
            }
        }
    }

    public void printUpcomingEvents(App.AppChoices choice) {
        OurDateTime realDateTime = new OurDateTime();//current date & time
        long format = realDateTime.getCalculationFormat();
        sortList(events);

        switch (choice) {
            case day -> {
                Validate.println("\nUpcoming Events for today:\n");  //from the realDateTime format we are changing the day from today to tomorrow and the time becomes 00:00
                format = format + 10000 - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                timePeriod(format, realDateTime.getCalculationFormat(), 1);
            }
            case week, month -> {
                Validate.println("\nUpcoming Events " + ((choice == App.AppChoices.week) ? "this week" : "this month") + ":\n");

                format += ((realDateTime.getMonth() == 12) ? 89000000L : 1000000L)
                        - (realDateTime.getDay() - 1) * 10000L
                        - realDateTime.getHour() * 100L
                        - realDateTime.getMinute();

                timePeriod(format, realDateTime.getCalculationFormat(), choice == App.AppChoices.week ? 2 : 1);
            }
        }
    }

    public void printOldEvents(App.AppChoices choice) {
        sortList(events);
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        long format = realDateTime.getCalculationFormat();

        switch (choice) {
            case pastday -> {//from the realDateTime format we are changing the time to 00:00
                Validate.println("\nOld Events from today:\n");
                format = format - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                timePeriod(realDateTime.getCalculationFormat(), format, 1);
            }
            case pastweek -> {//from the realDateTime format the day and time become 01, 00:00
                Validate.println("\nOld Events from this week:\n");
                format = format - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                timePeriod(realDateTime.getCalculationFormat(), format, 3);
            }
            default -> {//from the realDateTime format the day and time become 01, 00:00
                Validate.println("\nOld Events from this month:\n");
                format = format - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                timePeriod(realDateTime.getCalculationFormat(), format, 1);
            }
        }
    }

    public void printUnfinishedProject(App.AppChoices choice) {
        sortList(events);
        OurDateTime realDateTime = new OurDateTime(); // Current date & time
        long format = realDateTime.getCalculationFormat();

        for (Event event : events) {
            if (event instanceof Project && !((Project) event).getIsFinished()) {
                long dueFormat = ((Project) event).getDue().getCalculationFormat();

                if ((choice == App.AppChoices.todo && format < dueFormat) || (choice == App.AppChoices.due && format >= dueFormat))
                    System.out.println(event);
            }
        }
    }

    public Event eventSearch(String title, int type) {
        for (Event event : events)
            if (event.getTitle().equals(title))
                if ((type == 1 && event instanceof Appointment) || (type == 2 && event instanceof Project))
                    return event;

        return null;
    }
}
    /*
    public void editEvent() {
        int choice;
        String title;
        Event searchedEvent;

        do {
            Validate.println("""
                    Change:
                        1) Appointment
                        2) Project
                        3) Exit""");
            //Choosing one of the above options
            choice = Validate.checkAndReturnIntBetween(1, 3);
            //Printing all the events, appointments or projects:
            for (Event event : events) {
                if (choice == 2 && event instanceof Project || choice == 1 && event instanceof Appointment )
                    Validate.println(event);
                else if (choice == 3)
                    return;
            }

            Validate.println("Type the title of the event you want to change:");
            //Finding the title of the event:
            while (true) {
                title = Validate.strInput();
                searchedEvent = eventSearch(title, choice);
                if (searchedEvent == null)
                    Validate.println("You typed wrong title. Try again.");
                else
                    break;
            }
            //Changing the fields of the chosen event:
            searchedEvent.editEvent();
            Validate.println("");
        } while (choice != 3);
    }
    public void changeProjectCondition() {
        Validate.println("Please provide the name of the Project you wish to update its status");

        while (true) {
            String title = Validate.strInput();
            Event event = eventSearch(title, 2);

            if (event instanceof Project project) {
                project.setFinished(!project.getIsFinished());
                Validate.printf("The status of the Project is %s", project.getIsFinished() ? "Finished" : "Ongoing");
                return; // Exit the method if a valid project name is provided
            }
            Validate.println("Project does not exist. Please try again.");
        }
    }
   */
