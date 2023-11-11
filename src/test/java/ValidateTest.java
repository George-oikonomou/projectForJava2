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
}





