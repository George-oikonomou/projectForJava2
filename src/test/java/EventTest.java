import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    OurDateTime dateTime;
    @BeforeEach
    void setUp() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
    }

    @Test
    void getDate() {
    }

    @Test
    void setDate() {
    }

    @Test
    void getTime() {
    }

    @Test
    void setTime() {
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