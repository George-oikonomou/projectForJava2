import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    @Test
    public void uuidIsGeneratedOnCreation() {
        Event event = new Appointment(new OurDateTime(2023, 11, 22, 22, 0), new OurDateTime(2024, 2, 2, 11, 11), "title", "description");
        assertNotNull(event.getUuid());
    }

    @Test
    public void uuidContainsTimestamp() {
        Event event = new Appointment(new OurDateTime(2023, 11, 22, 22, 0), new OurDateTime(2024, 2, 2, 11, 11), "title", "description");
        assertTrue(event.getUuid().matches("\\d+_\\d+_@javaTeamGGS"));
    }

    @Test
    public void uuidContainsRandomPart() {
        Event event = new Appointment(new OurDateTime(2023, 11, 22, 22, 0), new OurDateTime(2024, 2, 2, 11, 11), "title", "description");
        String uuid = event.getUuid();
        String randomPart = uuid.split("_")[1];
        int randomPartInt = Integer.parseInt(randomPart);
        assertTrue(randomPartInt >= 1000 && randomPartInt <= 9999);
    }

    @Test
    public void uuidContainsFixedSuffix() {
        Event event = new Appointment(new OurDateTime(2023, 11, 22, 22, 0), new OurDateTime(2024, 2, 2, 11, 11), "title", "description");
        assertTrue(event.getUuid().endsWith("@javaTeamGGS"));
    }


    @Test
    public void testGetters() {
        OurDateTime dateTime = new OurDateTime(2023,11,22,22,0);
        OurDateTime endDate = new OurDateTime(2024,2,2,11,11);
        Appointment appointment = new Appointment(dateTime,endDate,"title", "description");

        assertEquals(dateTime, appointment.getStartDate());
        assertEquals(endDate, appointment.getEndDate());
        assertEquals("title", appointment.getTitle());
        assertEquals("description", appointment.getDescription());
    }
}