import gr.hua.dit.oop2.calendar.TimeService;
import net.fortuna.ical4j.model.component.VLocation;
import java.util.ArrayList;
public class App {
    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {
        calendarListFiller();

        int choice, option;
        ICSFile file = new ICSFile("calendar.ics");
        do {
            Validate.println("""
                
                add an event enter (1)
                edit an event enter (2)
                change the condition of a project (3)
                save events to file enter (4)
                load events from file enter (5)
                print upcoming events (6)
                print old events (7)
                print unfinished projects with uncompleted due deadline (8)
                print unfinished projects with completed due deadline (9)
                exit enter (10)
                
                """);

            choice = Validate.checkAndReturnIntBetween(1, 10);
            switch (choice) {
                case 1:
                    calendar.addEvents();
                    break;
                case 2:
                    calendar.editEvent();
                    break;
                case 3:
                    calendar.changeProjectCondition();
                    break;
                case 4:
                    file.StoreEvents(calendar.getEvents());
                    break;
                case 5:
                    file.LoadEvents();
                    ArrayList<Event> eventsToPrint = calendar.getEvents();
                    for (Event event : eventsToPrint) {
                        Validate.println(event);
                    }
                    break;
                case 6:
                    Validate.println("""
                print the upcoming events:
                for today (1)
                for this week (2)
                for this month (3)""");
                    option = Validate.checkAndReturnIntBetween(1, 3);
                    calendar.printUpcomingEvents(option);
                    break;
                case 7:
                    Validate.println("""
                print the old events:
                from today (1)
                from this week (2)
                from this month (3)""");
                    option = Validate.checkAndReturnIntBetween(1, 3);
                    calendar.printOldEvents(option);
                    break;
                case 8, 9:
                    calendar.printUnfinishedProject(choice);
                    break;
            }

        }while(choice != 10);
        TimeService.stop();
    }
    public static void calendarListFiller() {
        ArrayList<Event> events = new ArrayList<>();

        //future event that does not have specific time
        OurDateTime dateTime = new OurDateTime(2023, 12, 25);
        Event event1 = new Event(dateTime, "Christmas", "Biggest holiday of the year");
        event1.setOurCalendar(calendar);
        events.add(event1);

        //future event that does have a specific time
        OurDateTime dateTime1 = new OurDateTime(2024, 1, 9, 10, 35);
        Event event2 = new Event(dateTime1, "yo mama birth", "when yo mama was born");
        event2.setOurCalendar(calendar);
        events.add(event2);

        //future project
        OurDateTime deadline = new OurDateTime(2024, 5, 12, 23, 59);
        OurDateTime dateTime2 = new OurDateTime(2024, 3, 15);
        Project event3 = new Project(dateTime2, "Video Shoot", "You shot a bratty sis scene", deadline);
        event3.setOurCalendar(calendar);
        events.add(event3);


        //future appointment
        OurDateTime dateTime3 = new OurDateTime(2024,2,28,0,0);
        Appointment event4 = new Appointment(dateTime3,"MyBday","dont you assholes forget",120);
        event4.setOurCalendar(calendar);
        events.add(event4);

        //past event that has the same name with a project. it is a different type of event, so it should work
        OurDateTime dateTime4 = new OurDateTime(2023,5,13);
        Event event5 = new Event(dateTime4,"Video Shooter","yo mamas ph shoot");
        event5.setOurCalendar(calendar);
        events.add(event5);

        // a past project that its deadline is fucked
        OurDateTime deadline2 = new OurDateTime(2023, 5, 12, 23, 59);
        OurDateTime dateTime5 = new OurDateTime(2023, 4, 12);
        Project event6 = new Project(dateTime5, "draw yo mama", "You didnt drew yo mama", deadline2);
        event6.setOurCalendar(calendar);
        events.add(event6);

        calendar.setEvents(events);
    }
}