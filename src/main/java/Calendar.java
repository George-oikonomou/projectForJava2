import java.util.ArrayList;

// TODO: 8/11/23 DECIDE IF YOU WANT TO HAVE 5 DIFF LISTS OR 1
//  THIS IS IMPLEMENTATION FOR 1 LIST SEE 1.3 ON PROJECT AND DISCORD

public class Calendar {

    private ArrayList <Event> events;           //has all the events

    public void addEvents(){
        // TODO: method that adds events to the ArrayList events;
        // TODO: has to have title description and date
    }
    public void editEvents(){
        // todo: change information of an event according what type it is
    }
    public void changeProjectCondition(){
        // TODO: change the boolean condition of the project
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
}
