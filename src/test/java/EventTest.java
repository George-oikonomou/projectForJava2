import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    OurDateTime dateTime;

    @Test
    public void testSetAndGetDateTime() {
        Appointment appointment = new Appointment(dateTime, "title", "description",40);
        OurDateTime newDateTime = new OurDateTime(2023, 12, 13, 12, 0);
        appointment.setDateTime(newDateTime);
        assertEquals(newDateTime, appointment.getDateTime());
    }

    @Test
    public void testSetAndGetTitleAndDescription() {
        Appointment appointment = new Appointment(dateTime, "title", "description",40);
        appointment.setTitle("Conference");
        appointment.setDescription("International tech conference");

        assertEquals("Conference", appointment.getTitle());
        assertEquals("International tech conference", appointment.getDescription());
    }
}