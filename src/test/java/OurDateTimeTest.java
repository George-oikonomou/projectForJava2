import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OurDateTimeTest {
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
