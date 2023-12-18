import gr.hua.dit.oop2.calendar.TimeService;

import javax.swing.*;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}

    static OurCalendar calendar = new OurCalendar();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Replace "path/to/your/image.jpg" with the actual path to your image file
            MainPage frame = new MainPage();
        });
        TimeService.stop();
    }
}