import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    public Appointment appointment;



    @Test
    public void testICSFormatToDuration() {
        // Example duration string in ISO 8601 format (PT2H30M means 2 hours and 30 minutes)
        String durationString = "PT2H30M";

        // Call the method
        int result = Appointment.ICSFormatToDuration(durationString);

        // Expected result: 2 hours + 30 minutes = 150 minutes
        assertEquals(150, result);
    }

    @Test
    public void testICSFormatToDurationWithZero() {
        // Example duration string with zero hours and minutes
        String durationString = "PT0H0M";

        // Call the method
        int result = Appointment.ICSFormatToDuration(durationString);

        // Expected result: 0 minutes
        assertEquals(0, result);
    }

    @Test
    public void testICSFormatToDurationWithHoursOnly() {
        // Example duration string with only hours (PT3H means 3 hours)
        String durationString = "PT3H";

        // Call the method
        int result = Appointment.ICSFormatToDuration(durationString);

        // Expected result: 3 hours = 180 minutes
        assertEquals(180, result);
    }

    @Test
    public void testICSFormatToDurationWithMinutesOnly() {
        // Example duration string with only minutes (PT45M means 45 minutes)
        String durationString = "PT45M";

        // Call the method
        int result = Appointment.ICSFormatToDuration(durationString);

        // Expected result: 45 minutes
        assertEquals(45, result);
    }
}