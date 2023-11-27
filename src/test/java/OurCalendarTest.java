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
        expectedEvents.add(new Project(dateTime,"title","description",dateTime));
        expectedEvents.add(new Appointment(dateTime, "title2", "description",60));

        ourCalendar.setEvents(expectedEvents);
        ArrayList<Event> actualEvents = ourCalendar.getEvents();

        assertEquals(expectedEvents, actualEvents);
    }
    @Test
    public void testEventSearchWhenEventIsFound() {
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> Events = new ArrayList<>();

        Project expectedEvent =new Project(dateTime, "title", "description",dateTime);
        Events.add(expectedEvent);
        ourCalendar.setEvents(Events);

        Event actualEvent = ourCalendar.eventSearch("title", 2);

        assertEquals(expectedEvent,actualEvent);
    }
    @Test
    public void testEventSearchWhenEventIsNotFound() {
        OurCalendar ourCalendar = new OurCalendar();
        ArrayList<Event> Events =new ArrayList<>();

        Project expectedEvent =new Project(dateTime, "titleOfSomethingDifferent", "description",dateTime);
        Events.add(expectedEvent);
        ourCalendar.setEvents(Events);

        Event actualEvent = ourCalendar.eventSearch("title", 1);
        assertNull(actualEvent);
    }

}