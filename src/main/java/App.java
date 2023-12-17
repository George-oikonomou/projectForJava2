import gr.hua.dit.oop2.calendar.TimeService;
import java.io.File;
import java.util.Arrays;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}

    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {

        Validate.println("""
                             __________________________________
                            |                                  |
                            |    Welcome to our calendar app!  |
                            |__________________________________|
                        """);
        switch (args.length) {//checks the number of arguments to determine in which scenario we are
            case 1 -> handleSingleArgument(args[0]);
            case 2 -> handleDoubleArguments(args);
            default -> Validate.println("""
                 ____________________________________________________________________________________________
                |                                                                                            |
                |    Error: wrong number of arguments. Program exiting...                                    |
                |    Correct format: java -jar  ProjectForJava2-1.0-SNAPSHOT.jar [functionality] [file]      |
                |    functionality:  {Optional} day, week, month, pastday, pastweek, pastmonth, todo, due    |
                |    file: the path to the file you want to use                                              |
                |____________________________________________________________________________________________|
            """);
        }
        Validate.println("""
                  #####                                            ### ### ###\s
                 #     #  ####   ####  #####  #####  #   # ######  # # # # # #\s
                 #       #    # #    # #    # #    #  # #  #       # # # # # #\s
                 #  #### #    # #    # #    # #####    #   #####   # # # # # #\s
                 #     # #    # #    # #    # #    #   #   #        #   #   # \s
                 #     # #    # #    # #    # #    #   #   #                  \s
                  #####   ####   ####  #####  #####    #   ######   #   #   #""");
                           
        TimeService.stop();
    }

    private static void handleSingleArgument(String arg) {
        ICSFile ourFile = new ICSFile(arg);
        if (new File(arg).exists()){
            Validate.println("""
                 ____________________________________
                |                                    |
                |  file exists loading the events..  |
                |____________________________________|

            """);
            ourFile.loadEvents();
        } else {
            Validate.println("""
                 ___________________________________________
                |                                           |
                |  file does not exist ,creating new one..  |
                |___________________________________________|
            
            """);
        }
        calendar.addEvents();
        OurCalendar.sortList(calendar.getEvents());
        ourFile.storeEvents(calendar.getEvents());
    }

    private static void handleDoubleArguments(String[] args) {
        AppChoices choice = Arrays.stream(AppChoices.values())//provides a stream array of the enum values
                                  .filter(appChoice -> appChoice.toString().equals(args[0]))//filters the stream to find the enum value that matches the first argument
                                  .findFirst()//returns the first match
                                  .orElse(null);//if no match is found returns null

        if (choice == null) {//if no match is found
            Validate.println("""
                 ____________________________________________________________________________________________
                |                                                                                            |
                |    Error: wrong functionality option. Program exiting...                                   |
                |    Correct format: java -jar  ProjectForJava2-1.0-SNAPSHOT.jar [functionality] [file]      |
                |    functionality: day, week, month, pastday, pastweek, pastmonth, todo, due                |
                |    file: the path to the file you want to use                                              |
                |____________________________________________________________________________________________|
            """);
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