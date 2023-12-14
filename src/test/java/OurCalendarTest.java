import net.fortuna.ical4j.model.property.Status;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OurCalendarTest {
    public OurCalendar calendar;
    public OurDateTime dateTime;


    @Test
    public void addEvents() {
    }

    @Test
    public void editEvents() {
    }
/*
    @Test
    public void testChangeProjectCondition() {
        // setup
        OurDateTime dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        calendar = new OurCalendar();
        ArrayList<Event> events = new ArrayList<>();
        Project project = new Project(dateTime, "ProjectName", "description", dateTime);
        events.add(project);
        calendar.setEvents(events);

        // Test case 1: Finished project
        HelperFuncForTests.setInput("ProjectName");
        String output1 = HelperFuncForTests.captureOutput(() -> calendar.changeProjectCondition());
        assertTrue(output1.contains("The status of the Project is Finished"));

        // Test case 2: Unfinished project
        HelperFuncForTests.setInput("ProjectName");
        String output2 = HelperFuncForTests.captureOutput(() -> calendar.changeProjectCondition());
        assertTrue(output2.contains("The status of the Project is Ongoing"));
    }
*/

    @Test
    public void printUpcomingEvents() {

    }

    @Test
    public void printOldEvents() {
    }

    @Test
    public void printUnfinishedActive() {
    }

    @Test
    public void printUnfinishedNotActive() {
    }

    @Test
    public void reminder() {
    }
    @Test
    public void testSetAndGetEvents() {
        // Arrange
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Project("title","description",dateTime, Status.VTODO_NEEDS_ACTION));

        ourCalendar.setEvents(expectedEvents);
        ArrayList<Event> actualEvents = ourCalendar.getEvents();

        assertEquals(expectedEvents, actualEvents);
    }
}