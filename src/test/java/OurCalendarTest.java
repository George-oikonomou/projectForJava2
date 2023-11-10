import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OurCalendarTest {
    private OurCalendar calendar;

    @BeforeEach
    void setUp() {
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
        Project project = new Project("ProjectName", "description", "date", "time", "deadline", false);
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
        expectedEvents.add(new Event("title", "description", "date", "time"));
        expectedEvents.add(new Event("titleSecond", "description", "date", "time"));

        ourCalendar.setEvents(expectedEvents);
        ArrayList<Event> actualEvents = ourCalendar.getEvents();

        assertEquals(expectedEvents, actualEvents);
    }
    @Test
    void testEventSearchWhenEventIsFound() {
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> Events =new ArrayList<>();

        Event expectedEvent =new Event("title", "description", "date", "time");
        Events.add(expectedEvent);
        ourCalendar.setEvents(Events);

        Event actualEvent = ourCalendar.eventSearch("title");

        assertEquals(expectedEvent,actualEvent);
    }
    @Test
    void testEventSearchWhenEventIsNotFound() {
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> Events =new ArrayList<>();

        Event expectedEvent =new Event("TitleOfSomethingDifferent", "description", "date", "time");
        Events.add(expectedEvent);
        ourCalendar.setEvents(Events);

        Event actualEvent = ourCalendar.eventSearch("title");
        assertNull(actualEvent);
    }

}