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

    @Test
    public void durationInMinutesIsSetCorrectlyForDifferentDates() {
            OurDateTime startDateTime = new OurDateTime(2023, 12, 31, 23, 30);
            OurDateTime endDateTime = new OurDateTime(2024, 12, 31, 23, 30);
            Appointment appointment = new Appointment(startDateTime, endDateTime, "Title", "Description");

            assertEquals(527040, appointment.getDurationInMin());
    }

    @Test
    public void durationInMinutesIsSetCorrectlyForSameDates() {
            OurDateTime startDateTime = new OurDateTime(2023, 12, 31, 23, 30);
            OurDateTime endDateTime = new OurDateTime(2023, 12, 31, 23, 30);
            Appointment appointment = new Appointment(startDateTime, endDateTime, "Title", "Description");

            assertEquals(0, appointment.getDurationInMin());
    }

    @Test
    public void durationInMinutesIsSetCorrectlyForDatesInDifferentMonths() {
            OurDateTime startDateTime = new OurDateTime(2023, 8, 30, 23, 30);
            OurDateTime endDateTime = new OurDateTime(2023, 12, 1, 0, 30);
            Appointment appointment = new Appointment(startDateTime, endDateTime, "Title", "Description");

            assertEquals(132540, appointment.getDurationInMin());
    }

    @Test
    public void durationInMinutesIsSetCorrectlyForDatesInDifferentYears() {
            OurDateTime startDateTime = new OurDateTime(2023, 12, 31, 23, 30);
            OurDateTime endDateTime = new OurDateTime(2024, 1, 1, 0, 30);
            Appointment appointment = new Appointment(startDateTime, endDateTime, "Title", "Description");

            assertEquals(60, appointment.getDurationInMin());
    }

}