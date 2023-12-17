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
        events.add(new Project("title","description",new OurDateTime(), Status.VTODO_NEEDS_ACTION));
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
            OurCalendar calendar = new OurCalendar();
            ArrayList<Event> events = new ArrayList<>();
            events.add(new Project("title2","description2",new OurDateTime(2022, 1, 2, 10, 0), Status.VTODO_IN_PROCESS));
            events.add(new Project("title1","description1",new OurDateTime(2022, 1, 1, 10, 0), Status.VTODO_IN_PROCESS));
            calendar.setEvents(events);
            OurCalendar.sortList(events);
            assertEquals("title1", calendar.getEvents().get(0).getTitle());
            assertEquals("title2", calendar.getEvents().get(1).getTitle());
    }

    @Test
    public void compareEventsComparesProjectsByDueDate() {
            Project project1 = new Project("title1", "description1", new OurDateTime(2022, 1, 1, 10, 0), Status.VTODO_IN_PROCESS);
            Project project2 = new Project("title2", "description2", new OurDateTime(2022, 1, 2, 10, 0), Status.VTODO_IN_PROCESS);
            assertTrue(OurCalendar.compareEvents(project1, project2) < 0);
    }

    @Test
    public void compareEventsComparesAppointmentsByStartDate() {
            Appointment appointment1 = new Appointment(new OurDateTime(2022, 1, 1, 10, 0), new OurDateTime(2022, 1, 1, 11, 0), "title1", "description1");
            Appointment appointment2 = new Appointment(new OurDateTime(2022, 1, 2, 10, 0), new OurDateTime(2022, 1, 2, 11, 0), "title2", "description2");
            assertTrue(OurCalendar.compareEvents(appointment1, appointment2) < 0);
    }

    @Test
    public void compareEventsComparesProjectAndAppointmentByDueDateAndStartDate() {
            Project project = new Project("title1", "description1", new OurDateTime(2022, 1, 2, 10, 0), Status.VTODO_IN_PROCESS);
            Appointment appointment = new Appointment(new OurDateTime(2022, 1, 1, 10, 0), new OurDateTime(2022, 1, 1, 11, 0), "title2", "description2");
            assertTrue(OurCalendar.compareEvents(project, appointment) > 0);
    }

}