import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        OurDateTime dateTime = new OurDateTime(2023, 12, 13, 12, 0, "13/12/2004","12:00");
        appointment = new Appointment(dateTime, "title", "description", 1);
    }

    @Test
    void testGetDuration() {
        assertEquals(1, appointment.getDuration());
    }

    @Test
    void testSetDuration() {
        assertEquals(1, appointment.getDuration());
        appointment.setDuration(2);
        assertEquals(2, appointment.getDuration());
    }
}