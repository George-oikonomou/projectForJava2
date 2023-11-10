import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest extends EventTest
{
    private Project project;

    @Test
    public void testGetDeadline()
    {
        project = new Project("title", "description", "date", "time", "deadline", true);
        assertEquals("deadline", project.getDeadline());
    }

    @Test
    public void testSetDeadline()
    {
        project = new Project("title", "description", "date", "time", "deadline", true);

        assertEquals("deadline", project.getDeadline());
        project.setDeadline("testDeadline");
        assertEquals("testDeadline", project.getDeadline());
    }

    @Test
    public void testIsFinished()
    {
        project = new Project("title", "description", "date", "time", "deadline", true);

        assertTrue(project.isFinished());
    }

    @Test
    public void testSetFinished() {
        project = new Project("title", "description", "date", "time", "deadline", true);

        assertTrue(project.isFinished());
        project.setFinished(false);
        assertFalse(project.isFinished());
    }
}
