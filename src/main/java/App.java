import gr.hua.dit.oop2.calendar.TimeService;
import java.util.ArrayList;


public class App {
    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {

        calendarListFiller();
        int choice, option;
        ICSFile file = new ICSFile("greece.ics");
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
                case 1 -> calendar.addEvents();
                case 2 -> calendar.editEvent();
                case 3 -> calendar.changeProjectCondition();
                case 4 -> file.StoreEvents(calendar.getEvents());
                case 5 -> {
                    file.LoadEvents();
                    ArrayList<Event> eventsToPrint = calendar.getEvents();
                    for (Event event : eventsToPrint) {
                        Validate.println(event);
                    }
                }
                case 6 -> {
                    Validate.println("""
                            print the upcoming events:
                            for today (1)
                            for this week (2)
                            for this month (3)""");
                    option = Validate.checkAndReturnIntBetween(1, 3);
                    calendar.printUpcomingEvents(option);
                }
                case 7 -> {
                    Validate.println("""
                            print the old events:
                            from today (1)
                            from this week (2)
                            from this month (3)""");
                    option = Validate.checkAndReturnIntBetween(1, 3);
                    calendar.printOldEvents(option);
                }
                case 8, 9 -> calendar.printUnfinishedProject(choice);
            }
        }while(choice != 10);
        TimeService.stop();
    }
    public static void calendarListFiller() {
        ArrayList<Event> events = new ArrayList<>();

        //future project
        OurDateTime deadline = new OurDateTime(2024, 5, 12, 23, 59);
        OurDateTime dateTime2 = new OurDateTime(2024, 3, 15,0,0);
        Project event3 = new Project(dateTime2, "Video Shoot", "You shot a bratty sis scene", deadline);
        event3.setOurCalendar(calendar);
        events.add(event3);

        //future appointment
        OurDateTime dateTime3 = new OurDateTime(2024,2,28,0,0);
        OurDateTime endDate3 = new OurDateTime(2024,3,28,0,0);
        Appointment event4 = new Appointment(dateTime3,endDate3, "MyBday","dont you assholes forget");
        event4.setOurCalendar(calendar);
        events.add(event4);

        // a past project that its deadline is fucked
        OurDateTime deadline2 = new OurDateTime(2023, 12, 31, 23, 59);
        OurDateTime dateTime5 = new OurDateTime(2023, 4, 12,0,0);
        Project event6 = new Project(dateTime5, "draw yo mama", "You didnt drew yo mama", deadline2);
        event6.setOurCalendar(calendar);
        events.add(event6);

        calendar.setEvents(events);
        OurCalendar.printEvents(events);
    }
}