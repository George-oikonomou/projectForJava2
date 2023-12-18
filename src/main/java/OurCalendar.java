import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Status;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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
                  __________________
                 |   Make a new:    |
                 |   1) Appointment |
                 |   2) Project     |
                 |   3) Exit        |
                 |__________________|
            """);
            choice = Validate.checkAndReturnIntBetween(1, 3);
            if (choice == 3) break;
            //Title
            Validate.print("\nTitle:\t");
            title = Validate.strInput();
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

    protected static long compareEvents(Event event1, Event event2) {
        OurDateTime startDate1 = (event1 instanceof Project) ? ((Project) event1).getDue() : event1.getStartDate();
        OurDateTime startDate2 = (event2 instanceof Project) ? ((Project) event2).getDue() : event2.getStartDate();

        return startDate1.getCalculationFormat() - startDate2.getCalculationFormat();
    }

    private void printBetween(long minTime, long maxTime) {     //code 2 is for upcoming events this week, code 3 is for old events this week, code 1 is for the other prints
        long eventTime;
        for (Event event : events) {
            if (event instanceof Project) {
                eventTime = ((Project) event).getDue().getCalculationFormat();
            } else{
                eventTime = event.getStartDate().getCalculationFormat();
            }
            if (eventTime >= minTime && eventTime <= maxTime) { //if the event is between minimum and maximum time
                Validate.println(event);
            }
        }
    }

    public void printUpcomingEvents(App.AppChoices choice) {
        OurDateTime minTime = new OurDateTime(); //if we want to print an upcoming event the min time should be the current time
        OurDateTime maxTime = new OurDateTime(); //this value will always change based on the choice
        LocalDateTime currentTime = LocalDateTime.of(minTime.getYear(),minTime.getMonth()
                ,minTime.getDay(),minTime.getHour(),minTime.getMinute());
        sortList(events);
        switch (choice) {
            case day -> {
                //LocalTime.MAX sets the time of a date time object to 23:59 basically giving us the end of the day
                LocalDateTime endOfDay = currentTime.with(LocalTime.MAX);
                maxTime = new OurDateTime(endOfDay.getYear(),endOfDay.getMonthValue()
                        ,endOfDay.getDayOfMonth(),endOfDay.getHour(),endOfDay.getMinute());
            }
            case week -> {
                //the temporalAdjuster sets advances the current time to next sunday and the sets the time to 23:59
                LocalDateTime endOfSunday = currentTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
                        .with(LocalTime.MAX);
                maxTime = new OurDateTime(endOfSunday.getYear(),endOfSunday.getMonthValue(),
                        endOfSunday.getDayOfMonth(),endOfSunday.getHour(),endOfSunday.getMinute());
            }
            case month -> {
                /*getMonth gets the month from current time
                inputDateTime.toLocalDate().isLeapYear() this checks if the year is a leap year
                then we set the length of the month considering if it is a leap year.
                Then at the end we put back the time so we don't lose it */
                LocalDateTime endOfMonth = currentTime.withDayOfMonth(currentTime.getMonth().
                                length(currentTime.toLocalDate().isLeapYear())).with(LocalDateTime.now().toLocalTime());
                maxTime = new OurDateTime(endOfMonth.getYear(),endOfMonth.getMonthValue(),
                        endOfMonth.getDayOfMonth(),endOfMonth.getHour(),endOfMonth.getMinute());
            }
        }
        printBetween(minTime.getCalculationFormat(), maxTime.getCalculationFormat());
    }
    public void printOldEvents(App.AppChoices choice) {
        OurDateTime maxTime = new OurDateTime(); //if we want to print an old event the max time should be the current time
        OurDateTime minTime = new OurDateTime(); //this value will always change based on the choice
        LocalDateTime currentTime = LocalDateTime.of(maxTime.getYear(),maxTime.getMonth()
                ,maxTime.getDay(),maxTime.getHour(),maxTime.getMinute());
        sortList(events);
        switch (choice) {
            case pastday -> {
                //LocalTime.MIN sets the time of a date time object to 00:00 basically giving us the start of the day
                LocalDateTime startOfDay = currentTime.with(LocalTime.MIN);
                minTime = new OurDateTime(startOfDay.getYear(),startOfDay.getMonthValue()
                        ,startOfDay.getDayOfMonth(),startOfDay.getHour(),startOfDay.getMinute());
            }
            case pastweek -> {
                //the temporalAdjuster sets the current time to monday (start of week) and the sets the time to 00:00
                LocalDateTime startOfWeek = currentTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                        .with(LocalTime.MIN);
                minTime = new OurDateTime(startOfWeek.getYear(),startOfWeek.getMonthValue(),
                        startOfWeek.getDayOfMonth(),startOfWeek.getHour(),startOfWeek.getMinute());
            }
            case pastmonth -> {
                // set the current month day to 1 and time to 00:00
                LocalDateTime startOfMonth = currentTime.withDayOfMonth(1).with(LocalTime.MIN);
                minTime = new OurDateTime(startOfMonth.getYear(),startOfMonth.getMonthValue(),
                        startOfMonth.getDayOfMonth(),startOfMonth.getHour(),startOfMonth.getMinute());
            }
        }
        printBetween(minTime.getCalculationFormat(), maxTime.getCalculationFormat());
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
}
