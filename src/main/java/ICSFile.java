import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.validate.ValidationException;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ICSFile {
    private final String filePath;

    public ICSFile(String filePath) { this.filePath = filePath; }
    /**
     * method that loads Events from the ics file and saves it the "events" ArrayList on our calendar
     * see documentation for more info
     */
    public void loadEvents() {
        //these 2 lines make sure that the logs of the ical4j library are not printed on the stdout
        ch.qos.logback.classic.Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        ArrayList<Event> events = new ArrayList<>();

        try {
            //create cal object
            InputStream inputStream = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);
            int count = 1;
            for (Object component : calendar.getComponents()) {
                try {
                    if (component instanceof VEvent appointment)  // try to create an appointment
                        events.add(createAppointment(appointment));
                    else if (component instanceof VToDo project) //try to create a project
                        events.add(createProject(project));
                    else
                        Validate.println("The event " + count + " in the file is not an appointment nor a project moving on to next event..");
                } catch (NoSuchElementException e) {
                    // if something goes wrong here it will not terminate the program but move to the next comp
                    Validate.println("the event " + count + " on the file has missing properties moving on to next event..");
                }

                count++;
            }
            App.calendar.setCalScale(calendar.getCalendarScale());
            App.calendar.setProdId(calendar.getProductId());
            App.calendar.setVersion(calendar.getVersion().getValue());
            App.calendar.setEvents(events);
        } catch (ParserException | NullPointerException e) {
            Validate.println("The file you have provided is corrupt ");
            System.exit(1);
        }catch (IOException e){
            Validate.println("The file you provided does not exist");
            System.exit(1);
        }
    }

    private Appointment createAppointment(VEvent appointment) {
        //must have properties
        if (appointment.getSummary() == null || appointment.getStartDate() == null || (appointment.getEndDate() == null && appointment.getDuration() == null))
              throw new NoSuchElementException();

        String  title = appointment.getSummary().getValue();
        //optional property
        String description = ( appointment.getDescription() != null )
                             ? appointment.getDescription().getValue()
                             : "";

        OurDateTime startDate = ICSFormatToOurDateTime(appointment.getStartDate().getValue());
        // if i have duration get duration else get dtend
        return (appointment.getDuration() != null)
                ? new Appointment(startDate, appointment.getDuration(), title, description)
                : new Appointment(startDate, ICSFormatToOurDateTime(appointment.getEndDate().getValue()), title, description);
    }

    private Project createProject(VToDo project) {
        //must have properties
        if (project.getStatus() == null || project.getDue() == null || project.getSummary() == null)
            throw new NoSuchElementException();

        String title = project.getSummary().getValue();
        //optional
        String description = ( project.getDescription() != null )
                ? project.getDescription().getValue()
                : "";

        OurDateTime dueDate = ICSFormatToOurDateTime(project.getDue().getValue());
        Status status = project.getStatus();

        return new Project(title, description, dueDate, status);
    }

    public void storeEvents(ArrayList<Event> events) {
        Calendar calendar = new Calendar();
        Version version = new Version();
        version.setValue(App.calendar.getVersion());
        calendar.getProperties().add((version));
        calendar.getProperties().add(App.calendar.getProdId());
        calendar.getProperties().add(App.calendar.getCalScale());
        for (Event event : events) {
            if (event instanceof Appointment appointment)
                calendar.getComponents().add(createVEvent(appointment));
            else if (event instanceof Project project)
                calendar.getComponents().add(createVTodo(project));
        }
        //create ics file using the calendar object
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            CalendarOutputter outPutter = new CalendarOutputter();
            outPutter.output(calendar, fileWriter);
            Validate.println("Calendar successfully written to " + filePath);
        } catch (IOException | ValidationException e) {
            Validate.println("There was an error saving your calendar");
        }
    }

    /**
     * create a vevent using the info of an ourEvent
     * @param appointment the appointment that we will use to create a vEvent
     * @return returns the vEvent
     */
    private VEvent createVEvent(Appointment appointment) {
        VEvent event = new VEvent();
        event.getProperties().add(new Uid(appointment.getUuid()));
        event.getProperties().add(new Summary(appointment.getTitle()));
        event.getProperties().add(new DtStart(appointment.getStartDate().getIcsFormat()));
        event.getProperties().add(new DtEnd(appointment.getEndDate().getIcsFormat()));
        if (!appointment.getDescription().isEmpty()) {
            event.getProperties().add(new Description(appointment.getDescription()));
        }
        return event;
    }
    private VToDo createVTodo(Project project) {
        VToDo vToDo = new VToDo();
        vToDo.getProperties().add(new Uid(project.getUuid()));
        vToDo.getProperties().add(new Summary(project.getTitle()));
        if (!project.getDescription().isEmpty()) {
            vToDo.getProperties().add(new Description(project.getDescription()));
        }
        vToDo.getProperties().add(new Due(project.getDue().getIcsFormat()));
        vToDo.getProperties().add(project.getStatus());
        return vToDo;
    }

    private OurDateTime ICSFormatToOurDateTime(String string) {
        int year    = Integer.parseInt(string.substring(0, 4));
        int month   = Integer.parseInt(string.substring(4, 6));
        int day     = Integer.parseInt(string.substring(6, 8));
        int hour    = (string.length() == 13) ? Integer.parseInt(string.substring(9, 11)) : 0;
        int minutes = (string.length() == 13) ? Integer.parseInt(string.substring(11, 13)) : 0;

        return new OurDateTime(year, month, day, hour, minutes);
    }
}
