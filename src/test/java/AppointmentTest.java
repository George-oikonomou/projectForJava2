import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    public Appointment appointment;

    @Test
    public void testGetDuration() {
        OurDateTime dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        appointment = new Appointment(dateTime, "title", "description", 1);
        assertEquals(1, appointment.getDuration());
    }

    @Test
    public void testSetDuration() {
        OurDateTime dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        appointment = new Appointment(dateTime, "title", "description", 1);
        assertEquals(1, appointment.getDuration());
        appointment.setDuration(2);
        assertEquals(2, appointment.getDuration());
    }
}