import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class HelperFuncForTests

{
    static void setInput(Object input) {
        ByteArrayInputStream inputStream = null;

        if (input instanceof String) {
            inputStream = new ByteArrayInputStream(((String) input).getBytes());
        } else if (input instanceof Integer) {
            inputStream = new ByteArrayInputStream(input.toString().getBytes());
        }

        System.setIn(inputStream);
    }

    static String captureOutput(Runnable action) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        action.run();

        return outContent.toString();
    }

}
