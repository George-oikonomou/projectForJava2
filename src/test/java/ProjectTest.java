import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest extends EventTest
{
    private Project project;
    @BeforeEach
    void setUp()
    {
        project = new Project("title", "description", "date", "time", "deadline", true);
    }


    @Test
    public void testGetDeadline()
    {
        assertEquals("deadline", project.getDeadline());
    }

    @Test
    public void testSetDeadline()
    {
        assertEquals("deadline", project.getDeadline());
        project.setDeadline("testDeadline");
        assertEquals("testDeadline", project.getDeadline());
    }

    @Test
    public void testIsFinished()
    {
        assertTrue(project.isFinished());
    }

    @Test
    public void testSetFinished() {
        assertTrue(project.isFinished());
        project.setFinished(false);
        assertFalse(project.isFinished());
    }
}
