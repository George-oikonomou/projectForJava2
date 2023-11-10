import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;

    @BeforeEach
    public void setUp() {
        project = new Project("title", "description", "date", "time", "deadline", true);
    }
    @AfterEach
    public void tearDown() {
        project = null;
    }


    @Test
    public void testGetDeadline() {
        assertEquals("deadline", project.getDeadline());
    }

    @Test
    public void testSetDeadline() {
        project.setDeadline("testDeadline");
        assertEquals("testDeadline", project.getDeadline());
    }

    @Test
    public void testIsFinished() {
        assertTrue(project.isFinished());
    }

    @Test
    public void testSetFinished() {
        project.setFinished(false);
        assertFalse(project.isFinished());
    }
}
