import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

import java.util.ArrayList;

// TODO: 8/11/23 DECIDE IF YOU WANT TO HAVE 5 DIFF LISTS OR 1
//  THIS IS IMPLEMENTATION FOR 1 LIST SEE 1.3 ON PROJECT AND DISCORD

public class OurCalendar {

    private ArrayList <Event> events;           //has all the events
    private TimeTeller teller;

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
                datetime1 = Validate.dateAndTime();
                System.out.println();
                Event newEvent = new Event(datetime1, title, description);
                events.add(newEvent);
                break;
            }

            case 2: {
                //Date & Time
                datetime1 = Validate.dateAndTime();
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
                datetime1 = Validate.dateAndTime();
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
    public void editEvents(){
        // todo: change information of an event according what type it is

    }
    public void changeProjectCondition(){
        System.out.println("Please provide the name of the Project you wish to update its status");
        String title = Validate.strInput();
        Event event = eventSearch(title);

        if(event instanceof Project project){
            boolean status = project.isFinished();
            project.setFinished(!status);
            System.out.printf("The status of the Project is %s", project.isFinished() ? "Finished" : "Ongoing");
        }else{
            throw new IllegalArgumentException("Project does not exist");
        }
    }

    private void shortList(){
        // TODO: the list must always be sorted by time of expiration t
        //  he closest event to the current time must show up first
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

    public Event reminder(){
        // TODO: 8/11/23 return the next event for a reminder to the user
        return null;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }


    // TODO: 11/11/23 allow multiple titles of events but only if they are different types
    public Event eventSearch(String title){

        for(Event event : getEvents()){
            if (event.getTitle().equals(title)){
                return event;
            }
        }
        return null;
    }


}
