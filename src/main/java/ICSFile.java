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
import net.fortuna.ical4j.model.property.Duration;
public class ICSFile {
    private final String filePath;
    public ICSFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * method that loads Events from the ics file and saves it the "events" ArrayList on hour calendar
     * see documentation for more info
     */
    public void loadEvents() {
        //these 2 lines make sure that the logs of the ical4j library are not printed on the stdout
        ch.qos.logback.classic.Logger rootLogger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.ERROR);
        ArrayList<Event> events = new ArrayList<>();

        try {
            InputStream inputStream = new FileInputStream(filePath);
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(inputStream);
            int count = 1;
            for (Object component : calendar.getComponents()) {

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
            App.calendar.setCalScale(calendar.getCalendarScale());
            App.calendar.setProdId(calendar.getProductId());
            App.calendar.setVersion(calendar.getVersion());
        } catch (ParserException | NullPointerException e) {
            System.out.println("The file you have provided is corrupt ");
        }catch (IOException e){
            System.out.println("The file you provided does not exist");
            System.exit(1);
        }
        App.calendar.setEvents(events);
    }

    private Appointment createAppointment(VEvent appointment) {

        if (appointment.getSummary() == null){
            throw new NoSuchElementException();
        }
        String title = appointment.getSummary().getValue();

        String description = "";
        if (appointment.getDescription() != null) {
            description = appointment.getDescription().getValue();
        }

        DtStart dtStart = appointment.getStartDate();
        if(dtStart == null){
            throw new NoSuchElementException();
        }
        OurDateTime startDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtStart.getValue());

        DtEnd dtEnd = appointment.getEndDate();
        Duration duration = appointment.getDuration();

        if (duration != null){
            return new Appointment(startDate,duration, title, description);
        }else if (dtEnd != null) {
            OurDateTime endDate = OurDateTime.Functionality.ICSFormatToOurDateTime(dtEnd.getValue());
            return new Appointment(startDate, endDate, title, description);
        }else{
            throw new NoSuchElementException();
        }
    }
    private Project createProject(VToDo project) {
        if (project.getStatus() == null){
            throw new NoSuchElementException();
        }
        Status status = project.getStatus();

        if (project.getDue() == null) {
            throw new NoSuchElementException();
        }
        OurDateTime dueDate = OurDateTime.Functionality.ICSFormatToOurDateTime(project.getDue().getValue());

        if (project.getSummary() == null){
            throw new NoSuchElementException();
        }
        String title = project.getSummary().getValue();

        String description = "";
        if (project.getDescription() != null){
            description = project.getDescription().getValue();
        }
        return new Project(title, description, dueDate, status);
    }

    public void storeEvents(ArrayList<Event> events) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(App.calendar.getVersion());
        calendar.getProperties().add(App.calendar.getProdId());
        calendar.getProperties().add(App.calendar.getCalScale());
        for (Event event : events) {
            if (event instanceof Appointment appointment) {
                calendar.getComponents().add(createVEvent(appointment));
            } else if (event instanceof Project project) {
                calendar.getComponents().add(createVTodo(project));
            }
        }
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            CalendarOutputter outPutter = new CalendarOutputter();
            outPutter.output(calendar, fileWriter);
            System.out.println("Calendar successfully written to " + filePath);
        } catch (IOException | ValidationException e) {
            System.out.println("There was an error saving your calendar");
        }
    }

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
        vToDo.getProperties().add(new Summary(project.getTitle()));
        if (!project.getDescription().isEmpty()) {
            vToDo.getProperties().add(new Description(project.getDescription()));
        }
        vToDo.getProperties().add(new Due(project.getDue().getIcsFormat()));
        vToDo.getProperties().add(project.getStatus());
        return vToDo;
    }
}
