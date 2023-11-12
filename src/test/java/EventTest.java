import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    OurDateTime dateTime;

    @Test
    public void testSetAndGetDateTime() {
        Event event = new Event(dateTime, "title", "description");
        OurDateTime newDateTime = new OurDateTime(2023, 12, 13, 12, 0);
        event.setDateTime(newDateTime);
        assertEquals(newDateTime, event.getDateTime());
    }

    @Test
    public void testSetAndGetTitleAndDescription() {
        Event event = new Event(dateTime, "title", "description");
        event.setTitle("Conference");
        event.setDescription("International tech conference");

        assertEquals("Conference", event.getTitle());
        assertEquals("International tech conference", event.getDescription());
    }
}