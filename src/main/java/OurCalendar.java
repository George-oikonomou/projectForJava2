import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

public class OurCalendar {

    private ArrayList <Event> events;//has all the events

    //if the file does not exist the new Vcalendar will have these values
    private Version version = new Version("VERSION","2.0");
    private ProdId prodId = new ProdId("-//java project team//java calendar//EN");
    private CalScale calScale = new CalScale(CalScale.VALUE_GREGORIAN);


    public OurCalendar() {
        this.events = new ArrayList<>();
        TimeTeller teller = TimeService.getTeller();
    }

    public ArrayList<Event> getEvents() { return events; }
    public void setEvents(ArrayList<Event> events) { this.events = events; }

    public Version getVersion() {return version;}
    public void setVersion(Version version) {this.version = version;}

    public ProdId getProdId() {return prodId;}
    public void setProdId(ProdId prodId) {this.prodId = prodId;}

    public CalScale getCalScale() {return calScale;}
    public void setCalScale(CalScale calScale) {this.calScale = calScale;}

    public void addEvents() {
        int choice;
        String title, description;
        OurDateTime datetime1, endDate, deadline;

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
        datetime1 = OurDateTime.Functionality.dateAndTime(); //Date & Time
        Validate.println("");
        if (choice == 1) {
            System.out.println("Enter date and time that the event ends");
            endDate = OurDateTime.Functionality.dateAndTime();
            Appointment newAppointment = new Appointment(datetime1,endDate, title, description);
            events.add(newAppointment);
            newAppointment.setOurCalendar(this);

        } else {//Date & Time
            Validate.print("Deadline:\t");
            deadline = Validate.deadline(datetime1);
            Validate.println("");

            Project newProject = new Project(datetime1, title, description, deadline);
            events.add(newProject);
            newProject.setOurCalendar(this);
        }
    }

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
                project.setFinished(!project.isFinished());
                Validate.printf("The status of the Project is %s", project.isFinished() ? "Finished" : "Ongoing");
                return; // Exit the method if a valid project name is provided
            }
            Validate.println("Project does not exist. Please try again.");
        }
    }

    public static void sortList(ArrayList<Event> events) {
        int n = events.size();
        printEvents(events);

        for (int i = 1; i < n; ++i) {
            Event keyEvent = events.get(i);
            OurDateTime keyDateTime = keyEvent.getDateTime();
            keyDateTime.setCalculationFormat();

            int j = i - 1;

            while (j >= 0 && compareEvents(events.get(j), keyEvent) > 0) {
                events.set(j + 1, events.get(j));
                j = j - 1;
            }
            events.set(j + 1, keyEvent);
        }
    }

    private static long compareEvents(Event event1, Event event2) {
        OurDateTime dateTime1 = (event1 instanceof Project) ? ((Project) event1).getDeadline() : event1.getDateTime();
        OurDateTime dateTime2 = (event2 instanceof Project) ? ((Project) event2).getDeadline() : event2.getDateTime();

        return dateTime1.getCalculationFormat() - dateTime2.getCalculationFormat();
    }
    public static void printEvents(ArrayList<Event> events) {
        for (Event event : events)
            Validate.println(event.toString());
    }

    private void timePeriod(long maxTime, long minTime, int code) {     //code 2 is for upcoming events this week, code 3 is for old events this week, code 1 is for the other prints
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        DayOfWeek dayOfWeek = realDateTime.getDayOfWeek();
        int realDay = realDateTime.getDay();
        for (Event event : events) {
            int eventDay = event.getDateTime().getDay();
            long eventFormat = event.getDateTime().getCalculationFormat();
            if (eventFormat >= minTime && eventFormat < maxTime) {     //if the event is between minimum and maximum time
                if (code == 2 && (dayOfWeek.getValue() + eventDay - realDay) <= 7)  //if the event is upcoming
                    Validate.println(event.getTitle() + "\t" + event.getDateTime());
                else if (code == 3 && (1 + eventDay - realDay) <= dayOfWeek.getValue())
                    Validate.println(event.getTitle() + "\t" + event.getDateTime());
                else if (code == 1)
                    Validate.println(event.getTitle() + "\t" + event.getDateTime());
            }
        }
    }

    public void printUpcomingEvents(int choice){
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        long format = realDateTime.getCalculationFormat();
        sortList(events);
        if (choice == 1) {
            Validate.println("\nUpcoming Events for today:\n");
                //from the realDateTime format we are changing the day from today to tomorrow and the time becomes 00:00
                format = format + 10000 - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                timePeriod(format, realDateTime.getCalculationFormat(), 1);
        } else {
                if (choice == 2)
                    Validate.println("\nUpcoming Events this week:\n");
                else
                    Validate.println("\nUpcoming Events this month:\n");
                //from the realDateTime format we are changing the month from the current month to the next one and the day and time become 01, 00:00
                if (realDateTime.getMonth() == 12)   //if its December, we are changing the year and the month becomes January
                    format = format + 89000000 - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                    //+89000000 changes the year by one and the month becomes January
                else
                    format = format + 1000000L - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                System.out.println(format);
                if (choice == 2)
                    timePeriod(format, realDateTime.getCalculationFormat(), 2);
               else
                    timePeriod(format, realDateTime.getCalculationFormat(),1);
        }
    }
    public void printOldEvents(int choice){
        sortList(events);
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        long format = realDateTime.getCalculationFormat();

        switch (choice) {
            case 1: {
                Validate.println("\nOld Events from today:\n");
                //from the realDateTime format we are changing the time to 00:00
                format = format - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                timePeriod(realDateTime.getCalculationFormat(), format,1);
                break;
            }
            case 2: {
                Validate.println("\nOld Events from this week:\n");
                //from the realDateTime format the day and time become 01, 00:00
                format = format - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                timePeriod(realDateTime.getCalculationFormat(), format, 3);
                break;
            }
            default: {
                Validate.println("\nOld Events from this month:\n");
                //from the realDateTime format the day and time become 01, 00:00
                format = format - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                timePeriod(realDateTime.getCalculationFormat(), format, 1);
                break;
            }
        }
    }
    public void printUnfinishedProject(int choice){
        sortList(events);
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        long format = realDateTime.getCalculationFormat();
        for (Event event : events) {
            if (event instanceof Project && !((Project) event).isFinished()) {
                if (choice == 8 && format < ((Project) event).getDeadline().getCalculationFormat())
                    System.out.println(event.getTitle() + "\t" + event.getDateTime() + "\twith Deadline\t" + ((Project) event).getDeadline());
                else if (choice == 9 && format >= ((Project) event).getDeadline().getCalculationFormat())
                    System.out.println(event.getTitle() + "\t" + event.getDateTime() + "\twith Deadline\t" + ((Project) event).getDeadline());
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