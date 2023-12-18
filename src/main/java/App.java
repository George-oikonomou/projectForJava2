import gr.hua.dit.oop2.calendar.TimeService;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}

    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {
        new MainPage();
        TimeService.stop();
    }
}