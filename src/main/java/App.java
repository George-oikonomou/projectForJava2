import gr.hua.dit.oop2.calendar.TimeService;
import java.util.ArrayList;
import java.util.Scanner;
public class App {
    static Scanner input = new Scanner(System.in);
    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {
        calendarListFiller();
        System.out.println("add an event enter (1)");
        int choice = input.nextInt();
        ICSFile file = new ICSFile("calendar.ics");
        do {
            switch (choice) {
                case 1:
                    //edit the first event in the list
                    Event event = calendar.getEvents().get(0);
                    event.editEvent();
                    break;
                case 2:
                    file.StoreEvents(calendar.getEvents());
                    break;
                case 3:
                    
                    break;
                case 4:
                    OurCalendar.sortList(calendar.getEvents());
                    break;

            }
             choice = input.nextInt();
        }while(choice !=5);
        TimeService.stop();
    }
    public static void calendarListFiller() {
        ArrayList<Event> events = new ArrayList<>();

        //future event that does not have specific time
        OurDateTime dateTime = new OurDateTime(2023, 12, 25);
        Event event1 = new Event(dateTime, "Christmas", "Biggest holiday of the year");
        events.add(event1);

        //future event that does have a specific time
        OurDateTime dateTime1 = new OurDateTime(2024, 1, 9, 10, 35);
        Event event2 = new Event(dateTime1, "yo mama birth", "when yo mama was born");
        events.add(event2);

        //future project
        OurDateTime deadline = new OurDateTime(2024, 5, 12, 23, 59);
        OurDateTime dateTime2 = new OurDateTime(2024, 3, 15);
        Event event3 = new Project(dateTime2, "Video Shoot", "You shot a bratty sis scene", deadline);
        events.add(event3);

        //future appointment
        OurDateTime dateTime3 = new OurDateTime(2024,2,28,0,0);
        Event event4 = new Appointment(dateTime3,"MyBday","dont you assholes forget",120);
        events.add(event4);

        //past event that has the same name with a project. it is a different type of event so it should work
        OurDateTime dateTime4 = new OurDateTime(2023,5,13);
        Event event5 = new Event(dateTime4,"Video Shoot","yo mamas ph shoot");
        events.add(event5);

        // a past project that its deadline is fucked
        OurDateTime deadline2 = new OurDateTime(2023, 5, 12, 23, 59);
        OurDateTime dateTime5 = new OurDateTime(2023, 4, 12);
        Event event6 = new Project(dateTime5, "draw yo mama", "You didnt drew yo mama", deadline2);
        events.add(event6);

        calendar.setEvents(events);
    }

}





