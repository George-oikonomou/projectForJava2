import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest
{

    @Test
    public void testProject()
    {
        Project project = new Project("title", "description", "date", "time", "deadline", true);
        assertEquals("title", project.getTitle());
        assertEquals("description", project.getDescription());
        assertEquals("date", project.getDate());
        assertEquals("time", project.getTime());
        assertEquals("deadline", project.getDeadline());
        assertTrue(project.isFinished());
    }


    @Test
    public void testGetDeadline()
    {
        Project project = new Project("title", "description", "date", "time", "deadline", true);
        assertEquals("deadline", project.getDeadline());
    }

    @Test
    public void testSetDeadline()
    {
        Project project = new Project("title", "description", "date", "time", "deadline", true);
        assertEquals("deadline", project.getDeadline());
        project.setDeadline("testDeadline");
        assertEquals("testDeadline", project.getDeadline());
    }

    @Test
    public void testIsFinished()
    {
        Project project = new Project("title", "description", "date", "time", "deadline", true);
        assertTrue(project.isFinished());
    }

    @Test
    public void testSetFinished() {
        Project project = new Project("title", "description", "date", "time", "deadline", false);
        assertFalse(project.isFinished());
        project.setFinished(true);
        assertTrue(project.isFinished());
    }
}
