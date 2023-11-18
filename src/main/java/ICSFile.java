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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ICSFile {

    String filePath;

    public ICSFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * method that will load the contents of the file int the array list of events
     * in the calendar folder
     * fileName the path of the file we want to load
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

            for (Object component : calendar.getComponents(Component.VEVENT)){

                if (component instanceof VEvent event) {
                    String categoryVal;
                    if (event.getProperty("CATEGORIES") != null) {
                        categoryVal = event.getProperty("CATEGORIES").getValue();
                    } else {
                        System.out.println("Error: File does not have 'CATEGORIES' property file cannot be read");
                        return;
                    }
                    String dateTime;
                    String title = event.getSummary().getValue();
                    dateTime = event.getStartDate().getValue();


                    String description = event.getDescription().getValue();

                    if (title == null || dateTime == null || description == null) {
                        System.out.println("Error reading from file");
                        return;
                    }

                    switch (categoryVal) {
                        case "EVENT" -> {
                            OurDateTime ourDateTime = OurDateTime.Functionality.ICSFormatToOurDateTime(dateTime);
                            Event newEvent = new Event(ourDateTime, title, description);
                            events.add(newEvent);

                        }
                        case "PROJECT" -> {
                            OurDateTime ourDateTime = OurDateTime.Functionality.ICSFormatToOurDateTime(dateTime);
                            Property dueProperty = event.getProperty("DUE");
                            OurDateTime ourDue;
                            if (dueProperty != null) {
                                String due = dueProperty.getValue();
                                ourDue = OurDateTime.Functionality.ICSFormatToOurDateTime(due);
                            } else {
                                System.out.println("could not find due property in a project");
                                return;
                            }
                            String status = event.getStatus().getValue();
                            Project newProject = new Project(ourDateTime, title, description, ourDue);
                            newProject.setFinished(status.equals("COMPLETED"));
                            events.add(newProject);

                        }
                        case "APPOINTMENT" -> {
                            OurDateTime ourDateTime = OurDateTime.Functionality.ICSFormatToOurDateTime(dateTime);
                            Property durationProperty = event.getProperty("DURATION");
                            int ourDuration;
                            if (durationProperty != null) {
                                String duration = durationProperty.getValue();
                                ourDuration = Appointment.ICSFormatToDuration(duration);
                            } else {
                                System.out.println("could not find due property in a project");
                                return;
                            }
                            Appointment newAppointment = new Appointment(ourDateTime, title, description, ourDuration);
                            events.add(newAppointment);
                        }
                    }
                }
            }
        }catch (IOException | ParserException e){
            System.out.println("error reading from the file");
            e.printStackTrace();
        }
        App.calendar.setEvents(events);
    }
    public void StoreEvents(ArrayList<Event> events) {

        try {
            /*
             * checks if the File Class found an existing file with the same name
             * of if it did not find and created a new one
             */
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created " + file.getName());
            } else {
                System.out.println("File already exists. Overwriting");
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
            System.out.println("successfully exported events to " + filePath);

        } catch (IOException e) {
            System.out.println("error could not save file");
        }
    }

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

