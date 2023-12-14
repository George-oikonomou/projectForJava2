import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class OurCalendar {

    private ArrayList<Event> events;//has all the events

    //if the file does not exist the new calendar will have these values
    private String version = "2.0";
    private ProdId prodId = new ProdId("-//java project team//java calendar//EN");
    private CalScale calScale = new CalScale(CalScale.VALUE_GREGORIAN);

    public OurCalendar() { this.events = new ArrayList<>(); }

    public ArrayList<Event> getEvents() { return events; }
    public void setEvents(ArrayList<Event> events) { this.events = events; }

    public String getVersion() { return version;}
    public void setVersion(String version) {this.version = version;}

    public ProdId getProdId() { return prodId; }
    public void setProdId(ProdId prodId) { this.prodId = prodId; }

    public CalScale getCalScale() { return calScale; }
    public void setCalScale(CalScale calScale) { this.calScale = calScale; }

    public void addEvents() {
        int choice;
        String title, description;
        OurDateTime startDate, endDate, due;
        do {
            //NEW EVENT:
            Validate.println("""
                    Make a new:
                        1) Appointment
                        2) Project
                        3) Exit
                    """);
            choice = Validate.checkAndReturnIntBetween(1, 3);
            if (choice == 3) break;
            //Title
            Validate.print("\nTitle:\t");
            title = Validate.Title(this, choice);
            //Description
            Validate.print("\nDescription:\t");
            description = Validate.strInput();

            if (choice == 1) { //Adding one event to the arraylist
                Validate.println("Enter the date and time that the event starts");
                startDate = OurDateTime.Functionality.dateAndTime(); //Date & Time

                Validate.println("");

                Validate.println("Enter date and time that the event ends:\t");
                endDate = Validate.DateTime(startDate);

                events.add(new Appointment(startDate, endDate, title, description));
            } else {//Date & Time
                Validate.print("Enter Due date for the event:\t");

                due = OurDateTime.Functionality.dateAndTime();
                events.add(new Project(title, description, due, Status.VTODO_IN_PROCESS));
            }
        } while (true);
    }

    public static void sortList(ArrayList<Event> events) {events.sort((event1, event2) -> (int) (compareEvents(event1, event2)));}

    private static long compareEvents(Event event1, Event event2) {
        OurDateTime startDate1 = (event1 instanceof Project) ? ((Project) event1).getDue() : event1.getStartDate();
        OurDateTime startDate2 = (event2 instanceof Project) ? ((Project) event2).getDue() : event2.getStartDate();

        return startDate1.getCalculationFormat() - startDate2.getCalculationFormat();
    }

    private void timePeriod(long maxTime, long minTime, int code) {     //code 2 is for upcoming events this week, code 3 is for old events this week, code 1 is for the other prints
        OurDateTime realDateTime = new OurDateTime();       //current date & time
        DayOfWeek dayOfWeek = realDateTime.getDayOfWeek();
        int  eventDay, realDay = realDateTime.getDay();
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
                boolean isOld = (code == 3 && (1 + realDay - eventDay) <= dayOfWeek.getValue());

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
                if ( Validate.getDaysInMonth(realDateTime.getMonth(), realDateTime.getYear()) == realDateTime.getDay()) { //if it's the last day of the month, change month & day becomes 01
                    format = format + 1000000L - (realDateTime.getDay() - 1) * 10000L - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                    if (realDateTime.getMonth() == 12)  //if its December, the month is January and year is increased by 1
                        format = format + 88000000L;
                } else {
                    format = format + 10000 - realDateTime.getMinute() - (realDateTime.getHour() * 100L);
                }
                timePeriod(format, realDateTime.getCalculationFormat(), 1);
            }
            case week, month -> {
                Validate.println("\nUpcoming Events " + ((choice == App.AppChoices.week) ? "this week" : "this month") + ":\n");
                //from the realDateTime format we are changing the month to the next one, making day 01, and time 00:00
                format += ((realDateTime.getMonth() == 12) ? 89000000L : 1000000L)  //if month is December it changes year and month is January
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
            case pastweek,pastmonth -> {//from the realDateTime format the day and time become 01, 00:00
                Validate.println("\nOld Events from this " + ((choice == App.AppChoices.pastweek) ? "week" : "month") + ":\n");
                format = format - (realDateTime.getDay() - 1) * 10000L - realDateTime.getHour() * 100L - realDateTime.getMinute();
                timePeriod(realDateTime.getCalculationFormat(), format, choice == App.AppChoices.pastweek ? 3 : 1);
            }
        }
    }

    public void printUnfinishedProject(App.AppChoices choice) {
        sortList(events);
        OurDateTime realDateTime = new OurDateTime(); // Current date & time
        long format = realDateTime.getCalculationFormat();

        for (Event event : events) {
            if (event instanceof Project && !((Project) event).getIsFinished()) {
                long dueFormat = ((Project) event).getDue().getCalculationFormat(); //making a DueFormat to check if it surpasses the realDateTime
                if ((choice == App.AppChoices.todo && format < dueFormat) || (choice == App.AppChoices.due && format >= dueFormat))
                    Validate.println(event);
            }
        }
    }

    public Event eventSearch(String title, int type) {
        return events.stream()
                     .filter(event -> event.getTitle().equals(title))
                     .filter(event -> (type == 1 && event instanceof Appointment) || (type == 2 && event instanceof Project))
                     .findFirst()
                     .orElse(null);
    }
}