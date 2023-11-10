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
        Event newEvent;
        String title, description, date, time;

        // TODO: method that adds events to the ArrayList events;
        // TODO: has to have title description and date

        System.out.print("NEW EVENT:\n\nTitle:\t");
        title = Validation.strInput();

        System.out.print("\nDescription:\t");
        description = Validation.strInput();

        date = Validation.date();

        System.out.print("\nTime:\tHour:\t");

        System.out.print("\tMinute:\t");
        time = Validation.time();

        newEvent = new Event(title, description, date, time);
        events.add(newEvent);
    }
    public void editEvents(){
        // todo: change information of an event according what type it is
    }
    public void changeProjectCondition(){
        System.out.println("Please provide the name of the Project you wish to update its status");
        String title = Validation.strInput();
        Event event = eventSearch(title);

        if(event instanceof Project project){
            boolean status = project.isFinished();
            project.setFinished(!status);
            System.out.printf("The status of the Project is %s", project.isFinished() ? "finished" : "unfinished");
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

    public Event eventSearch(String title){

        for(Event event : getEvents()){
            if (event.getTitle().equals(title)){
                return event;
            }
        }
        return null;
    }
}
