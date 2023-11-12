import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OurCalendarTest {
    private OurCalendar calendar;
    private OurDateTime dateTime;
    @BeforeEach
    void setUp() {
        OurDateTime dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        calendar = new OurCalendar();
    }

    @Test
    void addEvents() {
    }

    @Test
    void editEvents() {
    }

    @Test
    public void testChangeProjectCondition() {
        // setup
        ArrayList<Event> events = new ArrayList<>();
        Project project = new Project(dateTime, "ProjectName", "description", dateTime);
        events.add(project);
        calendar.setEvents(events);

        // Test case 1: Finished project
        HelperFuncForTests.setInput("ProjectName");
        String output1 = HelperFuncForTests.captureOutput(() -> calendar.changeProjectCondition());
        assertTrue(output1.contains("The status of the Project is finished"));

        // Test case 2: Unfinished project
        HelperFuncForTests.setInput("ProjectName");
        String output2 = HelperFuncForTests.captureOutput(() -> calendar.changeProjectCondition());
        assertTrue(output2.contains("The status of the Project is unfinished"));
    }


    @Test
    void printUpcomingEvents() {
    }

    @Test
    void printOldEvents() {
    }

    @Test
    void printUnfinishedActive() {
    }

    @Test
    void printUnfinishedNotActive() {
    }

    @Test
    void reminder() {
    }
    @Test
    void testSetAndGetEvents() {
        // Arrange
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event(dateTime, "title", "description"));
        expectedEvents.add(new Event(dateTime, "title2", "description"));

        ourCalendar.setEvents(expectedEvents);
        ArrayList<Event> actualEvents = ourCalendar.getEvents();

        assertEquals(expectedEvents, actualEvents);
    }
    @Test
    void testEventSearchWhenEventIsFound() {
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> Events = new ArrayList<>();

        Event expectedEvent =new Event(dateTime, "title", "description");
        Events.add(expectedEvent);
        ourCalendar.setEvents(Events);

        Event actualEvent = ourCalendar.eventSearch("title");

        assertEquals(expectedEvent,actualEvent);
    }
    @Test
    void testEventSearchWhenEventIsNotFound() {
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> Events =new ArrayList<>();

        Event expectedEvent =new Event(dateTime, "titleOfSomethingDifferent", "description");
        Events.add(expectedEvent);
        ourCalendar.setEvents(Events);

        Event actualEvent = ourCalendar.eventSearch("title");
        assertNull(actualEvent);
    }

}