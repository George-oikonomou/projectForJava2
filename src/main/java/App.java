import gr.hua.dit.oop2.calendar.TimeService;
import java.io.File;
import java.util.ArrayList;



public class App {

    public enum AppChoices{
        day,
        week,
        month,
        pastday,
        pastweek,
        pastmonth,
        todo,
        due
    }
    static OurCalendar calendar = new OurCalendar();
    public static void main(String[] args) {

        if (args.length == 1){
            File file = new File(args[0]);
                if (file.exists()){
                    System.out.println("file exists loading the events..");
                    ICSFile ourFile = new ICSFile(args[0]);
                    ourFile.loadEvents();
                }else {
                    System.out.println("file does not exist, creating new one");
                    ICSFile ourFile = new ICSFile(args[0]);
                    calendar.addEvents();
                    ourFile.storeEvents(calendar.getEvents());
                }
        }else if (args.length == 2){
            AppChoices choice = null;
            for (AppChoices appChoices : AppChoices.values()){
                if (appChoices.toString().equals(args[0])){
                    choice = appChoices;
                }
            }
            if (choice == null){
                System.out.println("error wrong functionality option");
                System.exit(1);
            }
            ICSFile file = new ICSFile(args[1]);
            file.loadEvents();

            if (choice.equals(AppChoices.day) || choice.equals(AppChoices.week) || choice.equals(AppChoices.month)){
                calendar.printUpcomingEvents(choice);
            } else if (choice.equals(AppChoices.pastday) || choice.equals(AppChoices.pastweek) || choice.equals(AppChoices.pastmonth)) {
                calendar.printOldEvents(choice);
            }else {
                calendar.printUnfinishedProject(choice);
            }
        }else {
            System.out.println("Incorrect number of arguments");
        }


        calendarListFiller();

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