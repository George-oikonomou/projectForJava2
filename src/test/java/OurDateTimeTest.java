import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OurDateTimeTest {

    @Test
    public void icsFormatToOurDateTimeParsesDateOnly() {
        OurDateTime result = OurDateTime.Functionality.ICSFormatToOurDateTime("20220101");
        OurDateTime expected = new OurDateTime(2022, 1, 1, 0, 0);

        // Compare individual fields
        assertEquals(expected.getYear(), result.getYear());
        assertEquals(expected.getMonth(), result.getMonth());
        assertEquals(expected.getDay(), result.getDay());
        assertEquals(expected.getHour(), result.getHour());
        assertEquals(expected.getMinute(), result.getMinute());
    }


    @Test
    public void icsFormatToOurDateTimeParsesDateAndTime() {
        OurDateTime result = OurDateTime.Functionality.ICSFormatToOurDateTime("20220101T1030");
        OurDateTime expected = new  OurDateTime(2022, 1, 1, 10, 30);
        // Compare individual fields
        assertEquals(expected.getYear(), result.getYear());
        assertEquals(expected.getMonth(), result.getMonth());
        assertEquals(expected.getDay(), result.getDay());
        assertEquals(expected.getHour(), result.getHour());
        assertEquals(expected.getMinute(), result.getMinute());
    }

    @Test
    public void icsFormatToOurDateTimeThrowsExceptionForInvalidFormat() {
        assertThrows(NumberFormatException.class, () -> OurDateTime.Functionality.ICSFormatToOurDateTime("invalid"));
    }



        @Test
        public void setCalculationFormatCreatesCorrectFormatForMidnight() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 0, 0);
            assertEquals(202201010000L, dateTime.getCalculationFormat());
        }

        @Test
        public void setCalculationFormatCreatesCorrectFormatForNoon() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(202201011200L, dateTime.getCalculationFormat());
        }

        @Test
        public void setCalculationFormatCreatesCorrectFormatForLastMinuteOfDay() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 23, 59);
            assertEquals(202201012359L, dateTime.getCalculationFormat());
        }

}
