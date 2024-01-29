import GUI.OurMenuGUI;
import GUI.PrintGUI;
import Models.*;
import net.fortuna.ical4j.model.property.Status;
import org.junit.jupiter.api.Test;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.CalScale;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class OurCalendarTest {

    @Test
    public void calendarInitializesWithEmptyEvents() {
        OurCalendar calendar = new OurCalendar();
        assertTrue(calendar.getEvents().isEmpty());
    }

    @Test
    public void setAndGetEventsWorkCorrectly() {
        OurCalendar calendar = new OurCalendar();
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Project("title","description",new OurDateTime(), Status.VTODO_NEEDS_ACTION, "filename"));
        calendar.setEvents(events);
        assertEquals(events, calendar.getEvents());
    }

    @Test
    public void setAndGetVersionWorkCorrectly() {
        OurCalendar calendar = new OurCalendar();
        String version = "2.0";
        calendar.setVersion(version);
        assertEquals(version, calendar.getVersion());
    }

    @Test
    public void setAndGetProdIdWorkCorrectly() {
        OurCalendar calendar = new OurCalendar();
        ProdId prodId = new ProdId("-//java project team//java calendar//EN");
        calendar.setProdId(prodId);
        assertEquals(prodId, calendar.getProdId());
    }

    @Test
    public void setAndGetCalScaleWorkCorrectly() {
        OurCalendar calendar = new OurCalendar();
        CalScale calScale = new CalScale(CalScale.VALUE_GREGORIAN);
        calendar.setCalScale(calScale);
        assertEquals(calScale, calendar.getCalScale());
    }

    @Test
    public void sortListSortsEventsInCorrectOrder() {
            PrintGUI printGUI = new PrintGUI(OurMenuGUI.getAllFiles());

            OurCalendar calendar = new OurCalendar();
            ArrayList<Event> events = new ArrayList<>();
            events.add(new Project("title2","description2",new OurDateTime(2022, 1, 2, 10, 0), Status.VTODO_IN_PROCESS, "filename"));
            events.add(new Project("title1","description1",new OurDateTime(2022, 1, 1, 10, 0), Status.VTODO_IN_PROCESS, "filename"));
            calendar.setEvents(events);
            printGUI.sortList(events,true);
            assertEquals("title1", calendar.getEvents().get(0).getTitle());
            assertEquals("title2", calendar.getEvents().get(1).getTitle());
    }

    @Test
    public void compareEventsComparesProjectsByDueDate() {
        PrintGUI printGUI = new PrintGUI(OurMenuGUI.getAllFiles());

        Project project1 = new Project("title1", "description1", new OurDateTime(2022, 1, 1, 10, 0), Status.VTODO_IN_PROCESS, "filename");
            Project project2 = new Project("title2", "description2", new OurDateTime(2022, 1, 2, 10, 0), Status.VTODO_IN_PROCESS, "filename");
            assertTrue(printGUI.compareEvents(project1, project2) < 0);
    }

    @Test
    public void compareEventsComparesAppointmentsByStartDate() {
            PrintGUI printGUI = new PrintGUI(OurMenuGUI.getAllFiles());

            Appointment appointment1 = new Appointment(new OurDateTime(2022, 1, 1, 10, 0), new OurDateTime(2022, 1, 1, 11, 0), "title1", "description1", "filename");
            Appointment appointment2 = new Appointment(new OurDateTime(2022, 1, 2, 10, 0), new OurDateTime(2022, 1, 2, 11, 0), "title2", "description2", "filename");

            assertTrue(printGUI.compareEvents(appointment1, appointment2) < 0);
    }

    @Test
    public void compareEventsComparesProjectAndAppointmentByDueDateAndStartDate() {
            PrintGUI printGUI = new PrintGUI(OurMenuGUI.getAllFiles());

            Project project = new Project("title1", "description1", new OurDateTime(2022, 1, 2, 10, 0), Status.VTODO_IN_PROCESS, "filename");
            Appointment appointment = new Appointment(new OurDateTime(2022, 1, 1, 10, 0), new OurDateTime(2022, 1, 1, 11, 0), "title2", "description2", "filename");
            assertTrue(printGUI.compareEvents(project, appointment) > 0);
    }

}