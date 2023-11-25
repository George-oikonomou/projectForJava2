import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;
import java.time.DayOfWeek;
import java.util.ArrayList;

// TODO: 8/11/23 DECIDE IF YOU WANT TO HAVE 5 DIFF LISTS OR 1
//  THIS IS IMPLEMENTATION FOR 1 LIST SEE 1.3 ON PROJECT AND DISCORD

public class OurCalendar {

    private ArrayList <Event> events;           //has all the events
    private final TimeTeller teller;

    public OurCalendar() {
        this.events = new ArrayList<>();
        this.teller = TimeService.getTeller();
    }

    public void addEvents() {
        int choice;
        String title, description;
        int duration;
        OurDateTime datetime1, deadline;

        //NEW EVENT:
        System.out.println("Make a new:\n1) Event\n2) Appointment\n3) Project\n");
        choice = Validate.checkAndReturnIntBetween(1, 3);
        //Title
        System.out.print("\nTitle:\t");
        title = Validate.strInput();
        //Description
        System.out.print("\nDescription:\t");
        description = Validate.strInput();

        //Adding one event to the arraylist
        switch (choice) {
            case 1: {
                //Date & Time
                String option;

                System.out.println("\nDo you want to add a time for this event [Y/N]");

                while (true) {
                    option = Validate.strInput();
                    if (option.equalsIgnoreCase("Y")) {
                        datetime1 = OurDateTime.Functionality.dateAndTime(true);
                        break;
                    } else if (option.equalsIgnoreCase("N")) {
                        datetime1 = OurDateTime.Functionality.dateAndTime(false);
                        break;
                    } else {
                        System.out.println("Wrong input try again:\n");
                    }
                }
                System.out.println();

                Event newEvent = new Event(datetime1, title, description);
                events.add(newEvent);
                break;
            }
            case 2: {

                //Date & Time
                datetime1 = OurDateTime.Functionality.dateAndTime(true);
                System.out.println();

                System.out.print("Duration:\t");
                duration = Validate.checkAndReturnIntBetween(15, 6 * 60); //duration is minimum 15 minutes & maximum 6 hours
                System.out.println();

                Appointment newAppointment = new Appointment(datetime1, title, description, duration);
                events.add(newAppointment);
                break;
            }

            default: {

                //Date & Time
                datetime1 = OurDateTime.Functionality.dateAndTime(true);
                System.out.println();

                System.out.print("Deadline:\t");
                deadline = Validate.deadline(datetime1);
                System.out.println();

                Project newProject = new Project(datetime1, title, description, deadline);
                events.add(newProject);
                break;
            }
        }
    }

    public void editEvent() {
        int choice;
        boolean flag = false;
        String title;
        Event searchedEvent = null;

        do {
            System.out.println("Change:\n1) Event\n2) Appointment\n3) Project\n4) Exit");
            //Choosing one of the above options
            choice = Validate.checkAndReturnIntBetween(1, 4);
            //Printing all the events, appointments or projects:
            for (Event event : events) {
                if (choice == 3 && event instanceof Project) {
                    System.out.println(event);
                } else if (choice == 2 && event instanceof Appointment) {
                    System.out.println(event);
                } else if (choice == 1 && !(event instanceof Appointment) && !(event instanceof Project)) {
                    System.out.println(event.toString());
                } else if (choice == 4) {
                    return;
                }
            }

            System.out.println("Type the title of the event you want to change:");
            //Finding the title of the event:
            while (!flag) {
                title = Validate.strInput();
                searchedEvent = eventSearch(title, choice);
                if (searchedEvent == null) {
                    System.out.println("You typed wrong title. Try again.");
                } else {
                    flag = true;
                }
            }
            //Changing the fields of the chosen event:
            searchedEvent.editEvent();
            System.out.println();
        } while (choice != 4);
    }


    public void changeProjectCondition(){
        System.out.println("Please provide the name of the Project you wish to update its status");
        String title = Validate.strInput();
        Event event = eventSearch(title, 3);

        if(event instanceof Project project){
            boolean status = project.isFinished();
            project.setFinished(!status);
            System.out.printf("The status of the Project is %s", project.isFinished() ? "Finished" : "Ongoing");
        }else{
            throw new IllegalArgumentException("Project does not exist");
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
    private static void printEvents(ArrayList<Event> events) {
        for (Event event : events) {
            System.out.println(event.toString());
        }
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
                    System.out.println(event.getTitle() + "\t" + event.getDateTime());
                else if (code == 3 && (1 + eventDay - realDay) <= dayOfWeek.getValue())
                    System.out.println(event.getTitle() + "\t" + event.getDateTime());
                else if (code == 1)
                    System.out.println(event.getTitle() + "\t" + event.getDateTime());
            }
        }
    }

    public void printUpcomingEvents(int choice){
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        long format = realDateTime.getCalculationFormat();

        if (choice == 1) {
                System.out.println("\nUpcoming Events for today:\n");
                //from the realDateTime format we are changing the day from today to tomorrow and the time becomes 00:00
                format = format + 10000 - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                timePeriod(format, realDateTime.getCalculationFormat(), 1);
        } else {
                if (choice == 2)
                    System.out.println("\nUpcoming Events this week:\n");
                else
                    System.out.println("\nUpcoming Events this month:\n");
                //from the realDateTime format we are changing the month from the current month to the next one and the day and time become 01, 00:00
                if (realDateTime.getMonth() != 12)   //if its December, we are changing the year and the month becomes January
                    format = format + 89000000 - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
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
                System.out.println("\nOld Events from today:\n");
                //from the realDateTime format we are changing the time to 00:00
                format = format - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                timePeriod(realDateTime.getCalculationFormat(), format,1);
                break;
            }
            case 2: {
                System.out.println("\nOld Events from this week:\n");
                //from the realDateTime format the day and time become 01, 00:00
                format = format - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                timePeriod(realDateTime.getCalculationFormat(), format, 3);
                break;
            }
            default: {
                System.out.println("\nOld Events from this month:\n");
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
                if (choice == 7 && format < ((Project) event).getDeadline().getCalculationFormat())
                    System.out.println(event.getTitle() + "\t" + event.getDateTime() + "\twith Deadline\t" + ((Project) event).getDeadline());
                else if (choice == 8 && format >= ((Project) event).getDeadline().getCalculationFormat())
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
            System.out.println("You dont have any reminders");
        }else if (closestAppointment == null){
            System.out.println("Your next project deadline is in " + closestDeadline.getDeadline().toString());
        }else if(closestDeadline == null){
            System.out.println("Your next appointment is in "+ closestAppointment.getDateTime().toString());
        }else {
            if (closestAppointment.getDateTime().getCalculationFormat() < closestDeadline.getDeadline().getCalculationFormat()){
                System.out.println("Your next appointment is in "+ closestAppointment.getDateTime().toString());
            }else {
                System.out.println("Your next project deadline is in " + closestDeadline.getDeadline().toString());
            }
        }
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }


    // TODO: 11/11/23 allow multiple titles of events but only if they are different types
    public Event eventSearch(String title, int type){

        for(Event event : getEvents()){
            if (event.getTitle().equals(title) && type == 1 && !(event instanceof Appointment) && !(event instanceof Project)) {
                return event;
            } else if (event.getTitle().equals(title) && event instanceof Appointment && type == 2) {
                return event;
            } else if (event.getTitle().equals(title) && event instanceof Project && type == 3) {
                return event;
            }
        }
        return null;
    }
    public Event closestDeadlineSearch(long time){
        long closestDeadline = Long.MAX_VALUE;
        Event closestDeadlineProject = null;
        for ( Event event : getEvents()){
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
        for (Event event : getEvents()){
            if (event instanceof Appointment){
                if (event.getDateTime().getCalculationFormat() > time){
                    return event;
                }
            }
        }
        return null;
    }


}
