import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    OurDateTime dateTime;
    @BeforeEach
    public void setUp() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
    }

    @Test
    public void getDate() {
    }

    @Test
    public void setDate() {
    }

    @Test
    public void getTime() {
    }

    @Test
    public void setTime() {
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