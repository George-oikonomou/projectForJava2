import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;
    private OurDateTime dateTime;
    @BeforeEach
    void setUp() {
       dateTime = new OurDateTime(2023, 12, 13, 12, 0, "13/12/2004", "12:00");
    }

    @Test
    public void testGetAndSetDeadline() {
        project = new Project(dateTime, "title", "description", dateTime, false);
        OurDateTime dateTime1 = new OurDateTime(2023, 11, 12, 12, 0, "12/11/2004","12:00");
        project.setDeadline(dateTime1);
        assertEquals(dateTime1, project.getDeadline());
    }

    @Test
    public void testGetAndSetIsFinished() {
        project = new Project(dateTime, "title", "description", dateTime, false);
        project.setFinished(true);
        assertTrue(project.isFinished());
    }
}
