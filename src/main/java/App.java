import gr.hua.dit.oop2.calendar.TimeService;
import javax.swing.*;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}

    public static void main(String[] args) {
        // Replace "path/to/your/image.jpg" with the actual path to your image file
        SwingUtilities.invokeLater(MainPage::new);
        TimeService.stop();
    }
}