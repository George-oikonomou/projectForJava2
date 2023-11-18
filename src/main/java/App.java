import gr.hua.dit.oop2.calendar.TimeService;
import java.util.ArrayList;
import java.util.Scanner;
public class App {
    static Scanner input = new Scanner(System.in);
    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {

        System.out.println("add an event enter (1)");
        int choice = input.nextInt();
        ICSFile file = new ICSFile("calendar.ics");
        switch (choice) {
            case 1:
                calendar.addEvents();
                break;
            case 2:
                file.StoreEvents(calendar.getEvents());
                break;
            case 3:
                file.LoadEvents();
                ArrayList <Event> eventsToPrint = calendar.getEvents();
                System.out.println(eventsToPrint);
        }
        TimeService.stop();
    }
}





