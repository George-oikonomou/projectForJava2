import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class OurCalendar {

    private ArrayList <Event> events;//has all the events

    public OurCalendar() {
        this.events = new ArrayList<>();
        TimeTeller teller = TimeService.getTeller();
    }

    public ArrayList<Event> getEvents() { return events; }
    public void setEvents(ArrayList<Event> events) { this.events = events; }

    public void addEvents() {
        int choice, duration;
        String title, description;
        OurDateTime datetime1, deadline;

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
        datetime1 = OurDateTime.Functionality.dateAndTime(); //Date & Time
        Validate.println("");
        if (choice == 1) {
            Validate.print("Duration:\t");
            duration = Validate.checkAndReturnIntBetween(15, 6 * 60); //duration is minimum 15 minutes & maximum 6 hours
            Validate.println("");

            Appointment newAppointment = new Appointment(datetime1, title, description, duration);
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

    public void reminder(){
        OurDateTime liveTime = new OurDateTime();
        long formatLive = liveTime.getCalculationFormat();
        Project closestDeadline = (Project) closestDeadlineSearch(formatLive);
        Appointment closestAppointment = (Appointment) searchNextAppointment(formatLive);
        if (closestAppointment == null && closestDeadline == null){
            Validate.println("You dont have any reminders");
        }else if (closestAppointment == null){
            Validate.println("Your next project deadline is in " + closestDeadline.getDeadline().toString());
        }else if(closestDeadline == null){
            Validate.println("Your next appointment is in "+ closestAppointment.getDateTime().toString());
        }else {
            if (closestAppointment.getDateTime().getCalculationFormat() < closestDeadline.getDeadline().getCalculationFormat()){
                Validate.println("Your next appointment is in "+ closestAppointment.getDateTime().toString());
            }else {
                Validate.println("Your next project deadline is in " + closestDeadline.getDeadline().toString());
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
    public Event closestDeadlineSearch(long time){
        long closestDeadline = Long.MAX_VALUE;
        Event closestDeadlineProject = null;
        for ( Event event : events){
            if (event instanceof Project ){
                if ((((Project) event).getDeadline().getCalculationFormat() > time && (((Project) event).getDeadline().getCalculationFormat() < closestDeadline))){
                    closestDeadlineProject = event;
                    closestDeadline = ((Project) event).getDeadline().getCalculationFormat();
                }
            }
        }
        return closestDeadlineProject;
    }
    public Event searchNextAppointment(long time){
        for (Event event : events)
            if (event instanceof Appointment)
                if (event.getDateTime().getCalculationFormat() > time)
                    return event;

        return null;
    }
}