import net.fortuna.ical4j.model.property.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;
    private OurDateTime dateTime;
    @BeforeEach
    public void setUp() {
       dateTime = new OurDateTime(2023, 12, 13, 12, 0);
    }
    @Test
    public void testGetAndSetDeadline() {
        project = new Project("title", "description", dateTime, Status.VTODO_NEEDS_ACTION);
        OurDateTime dateTime1 = new OurDateTime(2023, 11, 12, 12, 0);
        project.setDeadline(dateTime1);
        assertEquals(dateTime1, project.getDeadline());
    }

    @Test
    public void testGetAndSetIsFinished() {
        project = new Project("title", "description", dateTime, Status.VTODO_NEEDS_ACTION);
        project.setFinished(true);
        assertTrue(project.getIsFinished());
    }
}
