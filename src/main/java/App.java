import gr.hua.dit.oop2.calendar.TimeService;
import java.io.File;




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
            ICSFile ourFile = new ICSFile(args[0]);
                if (file.exists()){
                    System.out.println("file exists loading the events..");
                    ourFile.loadEvents();
                }else {
                    System.out.println("file does not exist, creating new one");
                }
                calendar.addEvents();
                ourFile.storeEvents(calendar.getEvents());

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

            System.out.println(calendar.getEvents());

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
        TimeService.stop();
    }

}