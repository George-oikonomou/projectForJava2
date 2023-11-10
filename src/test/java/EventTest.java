import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {

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
        Event event = new Event("Meet", "description", "2020-10-10", "10:00 AM");
        event.setTitle("Conference");
        event.setDescription("International tech conference");

        assertEquals("Conference", event.getTitle());
        assertEquals("International tech conference", event.getDescription());
    }
}