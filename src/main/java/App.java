import gr.hua.dit.oop2.calendar.TimeService;
import javax.swing.*;
import java.util.ArrayList;

public class App {

    public enum AppChoices{day, week, month, pastday, pastweek, pastmonth, todo, due}
    private static final ArrayList<ICSFile> AllIcsFiles = new ArrayList<>();

    public static ArrayList<ICSFile> getAllIcsFiles() {
        return AllIcsFiles;
    }
    public static void main(String[] args) {
        // Replace "path/to/your/image.jpg" with the actual path to your image file
        SwingUtilities.invokeLater(MainPage::new);
        TimeService.stop();
    }
}