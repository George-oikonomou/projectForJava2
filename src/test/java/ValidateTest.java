import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class ValidateTest {

    @Test
    public void test_empty_strInput() {
        String input = """

                test
                """;

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String result = Validate.strInput();

        assertTrue(outContent.toString().contains("You typed something wrong. Try again."));

        assertEquals("test", result);
    }


    @Test
    public void test_Hello_strInput() {
        String input = "hello\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        String result = Validate.strInput();

        assertEquals("hello", result);
    }

    @Test
    public void test_checkAndReturnIntBetween() {
        String input = "f\n5\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int result = Validate.checkAndReturnIntBetween(1, 10);

        assertEquals(5, result);
        assertTrue(outContent.toString().contains("You didnt type a number.Try again."));
    }
    @Test
    public void test_out_of_bounds_number_checkAndReturnIntBetween() {
        String input = "11\n5\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int result = Validate.checkAndReturnIntBetween(1, 10);

        assertEquals(5, result);
        assertTrue(outContent.toString().contains("The number you typed is invalid, it should be between 1 and 10. Try again."));
    }
    @Test
    public void test_date (){
        int year = 2021;
        int month = 12;
        int day = 31;
        String result = Validate.date(year, month, day);
        assertEquals("31/12/2021", result);

        month = 10;
        day = 1;
        result = Validate.date(year, month, day);
        assertEquals("01/10/2021", result);

        month = 1;
        result = Validate.date(year, month, day);
        assertEquals("01/01/2021", result);

        day = 31;
        result = Validate.date(year, month, day);
        assertEquals("31/01/2021", result);
    }
    @Test
    public void test_time (){
        int hour = 23;
        int minute = 59;
        String result = Validate.time(minute,hour);
        assertEquals("23:59", result);

        hour = 0;
        minute = 0;
        result = Validate.time(minute,hour);
        assertEquals("00:00", result);

        hour = 12;
        minute = 0;
        result =Validate.time(minute,hour);
        assertEquals("12:00", result);

        hour = 0;
        minute = 30;
        result = Validate.time(minute,hour);
        assertEquals("00:30", result);
    }
    @Test
    public void test_day_when_leap_year(){
        HelperFuncForTests.setInput(29+"\n");
        int result = Validate.day(2, 2024,29);
        assertEquals(29, result);
    }
    @Test
    public void test_day_when_not_leap_year(){
        HelperFuncForTests.setInput("""
                29
                26
                """);
        String output = HelperFuncForTests.captureOutput(() -> Validate.day(2, 2023, 1));
        assertTrue(output.contains("The number you typed is invalid, it should be between 1 and 28. Try again."));

        HelperFuncForTests.setInput("""
                28
                """);
        int result = Validate.day(2, 2023, 1);
        assertEquals(28, result);
    }

    @Test
    public void test_day_in_month_with_31_or_30_days(){
        HelperFuncForTests.setInput("""
                31
                """);
        int result = Validate.day(1, 2023, 1);
        assertEquals(31, result);

        HelperFuncForTests.setInput("""
                31
                28
                """);
        int result2 = Validate.day(4, 2023, 1);
        assertEquals(result2, 28);
    }
    @Test
    public void test_day_with_Custom_values(){
        HelperFuncForTests.setInput("""
                26
                28
                """);
        int result = Validate.day(4, 2023, 27);

        assertEquals(result, 28);
    }

    @Test
    public void test_day_with_custom_values(){
        HelperFuncForTests.setInput("""
                26
                28
                """);
        int result = Validate.day(4, 2023, 27);

        assertEquals(result, 28);
    }
}





