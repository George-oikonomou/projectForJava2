import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testCalculate() {
        // Arrange
        int x = 5;
        int y = 10;
        int expected = 8; // The expected result

        // Act
        int result = Main.calculate(x, y);

        // Assert
        assertEquals(expected, result);
    }
}
