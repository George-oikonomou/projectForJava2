import gr.hua.dit.oop2.calendar.TimeService;
import java.io.File;
import java.util.Arrays;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}

    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {
        switch (args.length) {
            case 1 -> handleSingleArgument(args[0]);
            case 2 -> handleDoubleArguments(args);
            default -> Validate.println("Incorrect number of arguments");
        }
        TimeService.stop();
    }

    private static void handleSingleArgument(String arg) {
        ICSFile ourFile = new ICSFile(arg);
        if (new File(arg).exists()){
            System.out.println("file exists loading the events..");
            ourFile.loadEvents();
        } else {
            System.out.println("file does not exist, creating new one");
        }
        calendar.addEvents();
        ourFile.storeEvents(calendar.getEvents());
    }

    private static void handleDoubleArguments(String[] args) {
        AppChoices choice = Arrays.stream(AppChoices.values())
                                  .filter(appChoice -> appChoice.toString().equals(args[0]))
                                  .findFirst()
                                  .orElse(null);

        if (choice == null) {
            Validate.println("Error: wrong functionality option. Program exiting...");
            System.exit(1);
        }

        ICSFile file = new ICSFile(args[1]);
        file.loadEvents();

        switch (choice) {
            case day, week, month -> calendar.printUpcomingEvents(choice);
            case pastday, pastweek, pastmonth -> calendar.printOldEvents(choice);
            default -> calendar.printUnfinishedProject(choice);
        }
    }
}