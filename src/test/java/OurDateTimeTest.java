import Models.OurDateTime;
import net.fortuna.ical4j.model.DateTime;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.*;

public class OurDateTimeTest {
        @Test
        public void setCalculationFormatCreatesCorrectFormatForMidnight() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 0, 0);
            assertEquals(20220101000000L, dateTime.getCalculationFormat());
        }

        @Test
        public void setCalculationFormatCreatesCorrectFormatForNoon() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(20220101120000L, dateTime.getCalculationFormat());
        }

        @Test
        public void setCalculationFormatCreatesCorrectFormatForLastMinuteOfDay() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 23, 59);
            assertEquals(20220101235900L, dateTime.getCalculationFormat());
        }

        @Test
        public void getDateReturnsCorrectDate() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals("01/01/2022", dateTime.getDate());
        }

        @Test
        public void getYearReturnsCorrectYear() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(2022, dateTime.getYear());
        }

        @Test
        public void getMonthReturnsCorrectMonth() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(1, dateTime.getMonth());
        }

        @Test
        public void getDayReturnsCorrectDay() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(1, dateTime.getDay());
        }

        @Test
        public void getTimeReturnsCorrectTime() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals("12:00:00", dateTime.getTime());
        }

        @Test
        public void getHourReturnsCorrectHour() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(12, dateTime.getHour());
        }

        @Test
        public void getMinuteReturnsCorrectMinute() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            assertEquals(0, dateTime.getMinute());
        }

        @Test
        public void getIcsFormatReturnsCorrectFormat() throws ParseException {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            DateTime expectedIcsFormat = new DateTime("20220101T120000");
            assertEquals(expectedIcsFormat, dateTime.getIcsFormat());
        }

        @Test
        public void setIcsFormatSetsCorrectFormat() {
            OurDateTime dateTime = new OurDateTime(2022, 1, 1, 12, 0);
            dateTime.setIcsFormat();
            DateTime expectedIcsFormat = null;
            try {
                expectedIcsFormat = new DateTime("20220101T120000");
            } catch (ParseException ignored) {}
            assertEquals(expectedIcsFormat, dateTime.getIcsFormat());
        }
}
