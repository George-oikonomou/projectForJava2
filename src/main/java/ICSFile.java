import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.validate.ValidationException;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

//handle corrupt ics file exceptions

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
        ArrayList<Event> events = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);
            int count = 1;
            for (Object component : calendar.getComponents(Component.VEVENT)) {

                if (component instanceof VEvent appointment) {
                    // try to create an appointment
                    try {
                        Appointment newAppointment = createAppointment(appointment);
                        events.add(newAppointment);
                    } catch (NoSuchElementException e) {
                        System.out.println("the event " + count + " on the file has missing properties moving on to next event..");
                    }
                    count++;

                } else if (component instanceof VToDo project) {
                    //try to create a project
                    try {
                        Project newProject = createProject(project);
                        events.add(newProject);
                    } catch (NoSuchElementException e) {
                        System.out.println("the event " + count + " on the file has missing properties moving on to next event..");
                    }
                    count++;

                } else {
                    System.out.println("The event " + count + " in the file is not an appointment nor a project moving on to next event..");
                }
            }
        } catch (IOException | ParserException e) {
            System.out.println("error reading file");
        }
        App.calendar.setEvents(events);
    }

    private Appointment createAppointment(VEvent appointment) {
        Date dtstart = appointment.getStartDate().getDate();
        Date dtend = appointment.getEndDate().getDate();
        String title = appointment.getSummary().getValue();
        String description = appointment.getDescription().getValue();
        if (dtstart == null || dtend == null || title == null || description == null) {
            throw new NoSuchElementException();
        }
        OurDateTime startDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtstart.toString());
        OurDateTime endDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtend.toString());
        return new Appointment(startDate, endDate, title, description);
    }

    private Project createProject(VToDo project) {
        Date dtstart = project.getStartDate().getDate();
        Date due = project.getDue().getDate();
        String title = project.getSummary().getValue();
        String description = project.getDescription().getValue();
        if (dtstart == null || due == null || title == null || description == null) {
            throw new NoSuchElementException();
        }
        OurDateTime startDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtstart.toString());
        OurDateTime dueDate = OurDateTime.Functionality.ICSFormatToOurDateTime(due.toString());
        return new Project(startDate, title, description, dueDate);
    }

    public void StoreEvents(ArrayList<Event> events) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new Version("VERSION","2.0"));
        calendar.getProperties().add(new ProdId("-//JAVA HUA PROJECT ICS FILE Ltd.//EN"));
        for (Event event : events) {
            if (event instanceof Appointment appointment) {
                calendar.getComponents().add(createVEvent(appointment));
            } else if (event instanceof Project project) {
                calendar.getComponents().add(createVTodo(project));
            }
        }
        try {
            System.out.println(calendar);
            calendar.validate();
            // Rest of the code...
        } catch (ValidationException e) {

            System.out.println("Calendar validation error: " + e.getMessage());
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {

            calendar.validate();
            CalendarOutputter outPutter = new CalendarOutputter();
            outPutter.output(calendar, fileWriter);
            System.out.println("Calendar successfully written to " + filePath);
        } catch (IOException | ValidationException e) {
            System.out.println("There was an error saving your calendar");
        }
    }

    private VEvent createVEvent(Appointment appointment) {
        VEvent event = new VEvent();
        event.getProperties().add(new Summary(appointment.getTitle()));
        event.getProperties().add(new DtStart(appointment.getDateTime().getIcsFormat()));
        event.getProperties().add(new DtEnd(appointment.getEndDate().getIcsFormat()));
        event.getProperties().add(new Description(appointment.getDescription()));
        return event;
    }

    private VToDo createVTodo(Project project) {
        VToDo vToDo = new VToDo();
        vToDo.getProperties().add(new Summary(project.getTitle()));
        vToDo.getProperties().add(new Description(project.getDescription()));
        vToDo.getProperties().add(new DtStart(project.getDateTime().getIcsFormat()));
        vToDo.getProperties().add(new Due(project.getDeadline().getIcsFormat()));
        return vToDo;
    }
}


