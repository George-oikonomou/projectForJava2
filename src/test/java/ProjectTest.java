import Models.OurDateTime;
import Models.Project;
import net.fortuna.ical4j.model.property.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;
    private OurDateTime dateTime;


    @Test
    public void projectIsFinishedWhenStatusIsCompleted() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_COMPLETED);
        assertTrue(project.getIsFinished());
    }

    @Test
    public void projectIsNotFinishedWhenStatusIsNeedsAction() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS);
        assertFalse(project.getIsFinished());
    }

    @Test
    public void setFinishedChangesIsFinishedStatus() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS);
        project.setFinished(true);
        assertTrue(project.getIsFinished());
    }

    @Test
    public void getDueReturnsCorrectDateTime() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS);
        assertEquals(dateTime, project.getDue());
    }

    @Test
    public void getStatusReturnsCorrectStatus() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS);
        assertEquals(Status.VTODO_IN_PROCESS, project.getStatus());
    }
}