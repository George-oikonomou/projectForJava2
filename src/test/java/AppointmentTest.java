import net.fortuna.ical4j.model.ParameterList;
import org.junit.jupiter.api.Test;

import net.fortuna.ical4j.model.property.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    @Test
    public void testCalculateDurationInDays() {

        OurDateTime dateTime = new OurDateTime(2021, 5, 12, 23, 59);
        OurDateTime endDate = new OurDateTime(2021, 5, 13, 23, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("1 day 2 hours 1 minute", appointment.calculateDurationInDays(1561));
        assertEquals("1 day 1 hour", appointment.calculateDurationInDays(1500));
        assertEquals("1 day", appointment.calculateDurationInDays(1440));
        assertEquals("1 hour 1 minute", appointment.calculateDurationInDays(61));
        assertEquals("1 hour", appointment.calculateDurationInDays(60));
        assertEquals("1 minute", appointment.calculateDurationInDays(1));
    }

    @Test
    public void testCalculateDurationInDays2() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 5, 13, 22, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("90 days 5 hours 1 minute", appointment.getDuration());
    }

    @Test
    public void testDurationOnlyDays() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 16, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("1 day", appointment.getDuration());
    }

    @Test
    public void testDurationOnlyHours() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 13, 17, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("1 hour", appointment.getDuration());
    }

    @Test
    public void testDurationOnlyMinutes() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 17, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 13, 18, 50);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("51 minutes", appointment.getDuration());
    }

    @Test
    public void testDurationDaysAndHours() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 17, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("1 day 1 hour", appointment.getDuration());
    }

    @Test
    public void testDurationDaysAndMinutes() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 15, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 16, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("1 day 1 minute", appointment.getDuration());
    }

    @Test
    public void testDurationHoursAndMinutes() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 14, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 13, 17, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("2 hours 1 minute", appointment.getDuration());
    }

    @Test
    public void testDurationDaysHoursAndMinutes() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 15, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 17, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        assertEquals("1 day 1 hour 1 minute", appointment.getDuration());
    }

    @Test
    public void durationIsCalculatedCorrectlyWithDtend() {
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        OurDateTime endDate = new OurDateTime(2022, 1, 1, 12, 30);
        Appointment appointment = new Appointment(startDate, endDate, "Meeting", "Team meeting");

        assertEquals("2 hours 30 minutes", appointment.getDuration());
    }

    @Test
    public void durationIsZeroWhenStartAndEndDatesAreTheSame() {
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        OurDateTime endDate = new OurDateTime(2022, 1, 1, 10, 0);
        Appointment appointment = new Appointment(startDate, endDate, "Meeting", "Team meeting");

        assertEquals("0 minutes", appointment.getDuration());
    }

    @Test
    public void toStringReturnsCorrectFormat() {
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        OurDateTime endDate = new OurDateTime(2022, 1, 1, 12, 30);
        Appointment appointment = new Appointment(startDate, endDate, "Meeting", "Team meeting");

        String expected = """
                Appointment:
                    title: Meeting
                    description: Team meeting
                    start date & time: 01/01/2022 10:00
                    end date & time: 01/01/2022 12:30
                    duration: 2 hours 30 minutes
                """;

        assertEquals(expected, appointment.toString());
    }

    @Test
    public void durationIsCorrectlySetWithIcsDurationHoursAndMinutes() {
            Duration durationFromString = new Duration(new ParameterList(true), "PT1H30M"); // 1 hour and 30 minutes
            OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
            Appointment appointment = new Appointment(startDate, durationFromString, "Meeting", "Team meeting");

            assertEquals("1 hour 30 minutes", appointment.getDuration());
    }

    @Test
    public void durationIsCorrectlySetWithIcsDurationDaysHoursAndMinutes() {
        Duration durationFromString = new Duration(new ParameterList(true), "P1DT1H30M"); // 1 day, 1 hour and 30 minutes
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        Appointment appointment = new Appointment(startDate, durationFromString, "Meeting", "Team meeting");

        assertEquals("1 day 1 hour 30 minutes", appointment.getDuration());
    }

    @Test
    public void durationIsCorrectlySetWithIcsDurationDayAndHour() {
        Duration durationFromString = new Duration(new ParameterList(true), "P1DT1H"); // 1 day and 1 hour
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        Appointment appointment = new Appointment(startDate, durationFromString, "Meeting", "Team meeting");

        assertEquals("1 day 1 hour", appointment.getDuration());
    }

    @Test
    public void durationIsCorrectlySetWithIcsDurationDayAndMinute() {
        Duration durationFromString = new Duration(new ParameterList(true), "P1DT1M"); // 1 day and 1 minute
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        Appointment appointment = new Appointment(startDate, durationFromString, "Meeting", "Team meeting");

        assertEquals("1 day 1 minute", appointment.getDuration());
    }

    @Test
    public void durationIsCorrectlySetWithIcsDuration0Minutes() {
        // 0 minutes
        Duration durationFromString = new Duration(new ParameterList(true), "PT0M");
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        Appointment appointment = new Appointment(startDate, durationFromString, "Meeting", "Team meeting");

        assertEquals("0 minutes", appointment.getDuration());
    }

    @Test
    public void durationIsCorrectlySetWithIcsDuration6() {
        // 0 hours (means 0 minutes)
        Duration durationFromString = new Duration(new ParameterList(true), "PT0H");
        OurDateTime startDate = new OurDateTime(2022, 1, 1, 10, 0);
        Appointment appointment = new Appointment(startDate, durationFromString, "Meeting", "Team meeting");

        assertEquals("0 minutes", appointment.getDuration());
    }
}