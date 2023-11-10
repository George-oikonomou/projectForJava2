import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;

    @Test
    public void testGetAndSetDeadline() {
        project = new Project("name", "description", "deadline","2020-12-12","2020-12-12",false);
        project.setDeadline("deadline");
        assertEquals("deadline", project.getDeadline());
    }

    @Test
    public void testGetAndSetIsFinished() {
        project = new Project("name", "description", "deadline","2020-12-12","2020-12-12",false);
        project.setFinished(true);
        assertTrue(project.isFinished());
    }
}
