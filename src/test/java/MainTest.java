import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//TESTaaaaa
public class MainTest {

    @Test
    public void testCalculate() {
        // Arrange
        int x = 5;
        int y = 3;
        int expected = 8; // The expected result

        // Act
        int result = Main.calculate(x, y);

        // Assert
        assertEquals(expected, result);
    }
}
