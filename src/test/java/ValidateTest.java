import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateTest {


    @Test
    public void StringInput() {
        HelperFuncForTests.setInput("string");
        assertEquals(Validate.strInput(),"string");
    }

    @Test
    public void checkAndReturnIntBetweenTest() {
        HelperFuncForTests.setInput(1);
        assertEquals(Validate.checkAndReturnIntBetween(0, 2),1);
    }

    @Test
    public void getDaysInMonthReturnsCorrectDaysForFebruaryInLeapYear() {
        assertEquals(29, Validate.getDaysInMonth(2, 2020));
    }

    @Test
    public void getDaysInMonthReturnsCorrectDaysForFebruaryInNonLeapYear() {
        assertEquals(28, Validate.getDaysInMonth(2, 2021));
    }

    @Test
    public void getDaysInMonthReturnsCorrectDaysForMonthWith31Days() {
        assertEquals(31, Validate.getDaysInMonth(1, 2021));
    }

    @Test
    public void getDaysInMonthReturnsCorrectDaysForMonthWith30Days() {
        assertEquals(30, Validate.getDaysInMonth(4, 2021));
    }
}