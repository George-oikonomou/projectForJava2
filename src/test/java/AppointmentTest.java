import org.junit.jupiter.api.Test;
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
    public void testSetEndDate() {
        OurDateTime dateTime = new OurDateTime(2021, 5, 12, 23, 59);
        OurDateTime endDate = new OurDateTime(2021, 5, 13, 23, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        OurDateTime newEndDate = new OurDateTime(2021, 5, 14, 23, 59);
        appointment.setEndDate(newEndDate);
        assertEquals(newEndDate, appointment.getEndDate());
    }

    @Test
    public void testSetDuration() {
        OurDateTime dateTime = new OurDateTime(2023, 5, 12, 10, 56);
        OurDateTime endDate = new OurDateTime(2024, 5, 14, 23, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");

        appointment.setEndDate(endDate);
        appointment.setDuration(dateTime, endDate);
        assertEquals("368 days 13 hours 3 minutes", appointment.getDuration());
    }

    @Test
    public void testToString() {
        OurDateTime dateTime = new OurDateTime(2021, 5, 12, 23, 59);
        OurDateTime endDate = new OurDateTime(2021, 5, 13, 23, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        OurDateTime newEndDate = new OurDateTime(2021, 5, 14, 23, 59);
        appointment.setEndDate(newEndDate);
        appointment.setDuration(dateTime, newEndDate);
        assertEquals("""
                Appointment:
                    title: title
                    description: description
                    start date & time: 12/05/2021 23:59
                    end date & time: 14/05/2021 23:59
                    duration: 2 days
                """, appointment.toString());
    }

    @Test
    public void testCalculateDurationInDays2() {
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 5, 13, 22, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("90 days 5 hours 1 minute", appointment.getDuration());
    }

    @Test
    public void testDurationOnlyDays(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 16, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("1 day", appointment.getDuration());
    }

    @Test
    public void testDurationOnlyHours(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 13, 17, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("1 hour", appointment.getDuration());
    }

    @Test
    public void testDurationOnlyMinutes(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 13, 16, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("0 minutes", appointment.getDuration());
    }

    @Test
    public void testDurationDaysAndHours(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 16, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 17, 59);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("1 day 1 hour", appointment.getDuration());
    }

    @Test
    public void testDurationDaysAndMinutes(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 15, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 16, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("1 day 1 minute", appointment.getDuration());
    }

    @Test
    public void testDurationHoursAndMinutes(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 15, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 13, 17, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("1 hour 1 minute", appointment.getDuration());
    }

    @Test
    public void testDurationDaysHoursAndMinutes(){
        OurDateTime dateTime = new OurDateTime(2024, 2, 13, 15, 59);
        OurDateTime endDate = new OurDateTime(2024, 2, 14, 17, 0);
        Appointment appointment = new Appointment(dateTime, endDate, "title", "description");
        appointment.setDuration(dateTime, endDate);
        assertEquals("1 day 1 hour 1 minute", appointment.getDuration());
    }
}