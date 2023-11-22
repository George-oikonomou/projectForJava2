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
                option = Validate.strInput();
                while (true) {
                    if (option.equals("Y")) {
                        datetime1 = OurDateTime.Functionality.dateAndTime(true);
                        break;
                    } else if (option.equals("N")) {
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

                Appointment newAppointment = new Appointment(datetime1, description, title, duration);
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

                Project newProject = new Project(datetime1, description, title, deadline);
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


    public void printUpcomingEvents() {
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        DayOfWeek dayOfWeek = realDateTime.getDayOfWeek();
        int eventHour, eventMin, eventMon, eventYear, eventDay;
        int realHour = realDateTime.getHour();
        int realMin = realDateTime.getMinute();
        int realYear = realDateTime.getYear();
        int realMon = realDateTime.getMonth();
        int realDay = realDateTime.getDay();

        System.out.println("\nUpcoming Events for today:\n");
        for (Event event: events) {
            eventHour = event.getDateTime().getHour();
            eventMin = event.getDateTime().getMinute();
            //if it's the same date and the time is from now until 23:59
            if (event.getDateTime().getDate().equals(realDateTime.getDate()) && (eventHour == realHour && eventMin >= realMin) || (eventHour > realHour))
                System.out.println(event.getTitle() + "\t" + event.getDateTime().getTime());
        }
        System.out.println("\nUpcoming Events this week:\n");
        for (Event event: events) {     //if it's the same year and month and the day is from today until Sunday
            eventHour = event.getDateTime().getHour();
            eventMin = event.getDateTime().getMinute();
            eventMon = event.getDateTime().getMonth();
            eventYear = event.getDateTime().getYear();
            eventDay = event.getDateTime().getDay();

            if (eventYear == realYear && eventMon == realMon && (dayOfWeek.getValue() + eventDay - realDay) <= 7 &&
            (eventDay == realDay && (eventHour == realHour && eventMin >= realMin || eventHour > realHour) || eventDay > realDay))
                System.out.println(event.getTitle() + "\t" + event.getDateTime());
        }
        System.out.println("\nUpcoming Events this month:\n");
        for (Event event: events) {     //if it's the same year and month and the day is from today until the last day of the month
            eventHour = event.getDateTime().getHour();
            eventMin = event.getDateTime().getMinute();
            eventMon = event.getDateTime().getMonth();
            eventYear = event.getDateTime().getYear();
            eventDay = event.getDateTime().getDay();

            if (eventYear == realYear && eventMon == realMon && (eventDay == realDay && (eventHour == realHour && eventMin >= realMin || eventHour > realHour) || eventDay > realDay))
                System.out.println(event.getTitle() + "\t" + event.getDateTime());
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
    public void printUpcomingEvents(String time){

        // TODO: print events until the specified time given

    }
    public void printOldEvents(String time){
        // TODO: print events from the time specified to the current time
    }

    public void printUnfinishedActive(String time){
        // TODO: print the projects that are not finished and the deadline is not due
    }
    public void printUnfinishedNotActive(String time){
        // TODO: print the projects that are not finished and the deadline is due
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
