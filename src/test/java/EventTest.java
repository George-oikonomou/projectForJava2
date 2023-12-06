import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    OurDateTime dateTime;
    OurDateTime endDate;

    @Test
    public void testSetAndGetDateTime() {
        dateTime = new OurDateTime(2023,11,22,22,0);
        endDate = new OurDateTime(2024,2,2,11,11);
        Appointment appointment = new Appointment(dateTime, endDate,"title", "description");
        OurDateTime newDateTime = new OurDateTime(2023, 12, 13, 12, 0);
        appointment.setStartDate(newDateTime);
        assertEquals(newDateTime, appointment.getStartDate());
    }

    @Test
    public void testSetAndGetTitleAndDescription() {
        dateTime = new OurDateTime(2023,11,22,22,0);
        endDate = new OurDateTime(2024,2,2,11,11);
        Appointment appointment = new Appointment(dateTime,endDate,"title", "description");
        appointment.setTitle("Conference");
        appointment.setDescription("International tech conference");

        assertEquals("Conference", appointment.getTitle());
        assertEquals("International tech conference", appointment.getDescription());
    }
}