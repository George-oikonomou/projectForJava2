
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;



public class ICSFile {

    /**
     * returns true if file exists
     * @param fileName the file name
     * @return returns its path
     */
    public static boolean ICSFilePath(String fileName) {
        Path path = Paths.get(fileName);

        if (Files.exists(path)) {
            System.out.println("file exists");
            return true;
        }
        return false;
    }

    /**
     * method that will load the contents of the file int the array list of events
     * in the calendar folder
     * fileName the path of the file we want to load
     */
    public static void LoadEvents(String fileName) throws IOException, ParserException {
        ArrayList<Event> events = new ArrayList<>();

        FileInputStream fis = new FileInputStream(fileName);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fis);




    }
}
