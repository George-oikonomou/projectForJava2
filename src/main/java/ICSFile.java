import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class ICSFile {
    private final String filePath;
    public ICSFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * method that loads Events from the ics file and saves it the "events" ArrayList on hour calendar
     * see documentation for more info
     */
    public void LoadEvents() {
        //these 2 lines make sure that the logs of the ical4j library are not printed on the stdout
        ch.qos.logback.classic.Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        ArrayList <Event> events = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);

            for (Object component : calendar.getComponents(Component.VEVENT)) {

                if (component instanceof VEvent event) {
                    // try to create an appointment
                    try {
                        Appointment newAppointment = createAppointment(event);
                    }catch (NoSuchElementException e){
                        // TODO: 29/11/23 print error if event in file does not have the correct amount
                        // TODO: 29/11/23 information
                    }

                }
            }
        }catch (IOException | ParserException e){
            Validate.println("error reading from the file");
        }
        App.calendar.setEvents(events);
    }

    private Appointment createAppointment (VEvent event){
        Date dtstart = event.getStartDate().getDate();
        Date dtend = event.getEndDate().getDate();
        String title = event.getSummary().getValue();
        String description = event.getDescription().getValue();
        if ( dtstart == null || dtend == null || title == null || description == null){
            throw new NoSuchElementException();
        }
        OurDateTime startDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtstart.toString());
        OurDateTime endDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtend.toString());

        return new Appointment(startDate,endDate,title,description);
    }

    public void StoreEvents(ArrayList<Event> events) {

        try {
            /*
             * checks if the File Class found an existing file with the same name
             * of if it did not find and created a new one
             */
            File file = new File(filePath);
            if (file.createNewFile()) {
                Validate.println("File created " + file.getName());
            } else {
                Validate.println("File already exists. Overwriting");
            }

            FileWriter fileWriter = new FileWriter(file);
            /*
                these contents must be at the start of every calendar
             */
            fileWriter.write("BEGIN:VCALENDAR\n");
            fileWriter.write("VERSION:2.0\n");
            fileWriter.write("PRODID:-//Java Team//My Calendar//EN\n");
            fileWriter.write("CALSCALE:GREGORIAN\n");
                /*
                we loop throw all of the events saving their information depending on the type of event
                 */
            for (Event event : events) {
                fileWriter.write("BEGIN:VEVENT\n");

                String dtStart = ICSFile.Functionality.OurDateTimeToICSFormat(event.getDateTime());
                fileWriter.write("SUMMARY:" + event.getTitle() + "\n");
                if(dtStart.length() == 8){
                    fileWriter.write("DTSTART;VALUE=DATE:" + dtStart + "\n");
                }else {
                    fileWriter.write("DTSTART:" + dtStart + "\n");
                }
                fileWriter.write("DESCRIPTION:" + event.getDescription() + "\n");

                if (event instanceof Project) {

                    fileWriter.write("CATEGORIES:PROJECT\n");
                    String due = ICSFile.Functionality.OurDateTimeToICSFormat(((Project) event).getDeadline());
                    fileWriter.write("DUE:" + due + "\n");
                    fileWriter.write("STATUS:" + (((Project) event).isFinished() ? "COMPLETED" : "INPROCESS") + "\n");
                } else if (event instanceof Appointment) {
                    String duration = ICSFile.Functionality.DurationToICSFormat(((Appointment) event).getDuration());
                    fileWriter.write("CATEGORIES:APPOINTMENT\n");
                    fileWriter.write("DURATION:" + duration + "\n");
                } else {
                    fileWriter.write("CATEGORIES:EVENT\n");
                }
                fileWriter.write("END:VEVENT\n");

            }
            fileWriter.write("END:VCALENDAR\n");
            fileWriter.close();
            Validate.println("successfully exported events to " + filePath);

        } catch (IOException e) {
            Validate.println("error could not save file");
        }
    }

    private static class Functionality{
        private static String OurDateTimeToICSFormat(OurDateTime dateTime){
            String month ,day;
            if(dateTime.getMonth() < 10){
                month = "0".concat(Integer.toString(dateTime.getMonth()));
            }else {
                month = Integer.toString(dateTime.getMonth());
            }
            if(dateTime.getDay() < 10){
                day = "0".concat(Integer.toString(dateTime.getDay()));
            }else {
                day = Integer.toString(dateTime.getDay());
            }

            String date = Integer.toString(dateTime.getYear()).concat(month).concat(day);
            if(dateTime.getTime() == null){
                return date.concat("T000000");
            }else {
                String  time = dateTime.getTime().replace(":","");
                return date.concat("T").concat(time).concat("00");
            }
        }
        private static String DurationToICSFormat(int duration){
            int hours = duration / 60;
            int minutes = duration % 60;
            return "PT".concat(Integer.toString(hours)).concat("H").concat(Integer.toString(minutes)).concat("M");
        }
    }
}

