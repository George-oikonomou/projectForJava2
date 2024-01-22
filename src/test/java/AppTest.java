import Main.App;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void testMainWithOneArgument() {
        String[] args = {"day"};
        assertTrue(Arrays.stream(App.AppChoices.values())
                .anyMatch(choice -> choice.toString().equals(args[0])));
    }

    @Test
    public void testMainWithTwoArguments() {
        String[] args = {"day", "myFile.ics"};
        assertTrue(Arrays.stream(App.AppChoices.values())
                .anyMatch(choice -> choice.toString().equals(args[0])));
    }

    @Test
    public void testMainWithfalseArguments() {
        String[] args = {"iman", "myFile.ics"};
        assertFalse(Arrays.stream(App.AppChoices.values())
                .anyMatch(choice -> choice.toString().equals(args[0])));
    }
}
