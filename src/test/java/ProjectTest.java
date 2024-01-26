import Models.OurDateTime;
import Models.Project;
import net.fortuna.ical4j.model.property.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {
    private Project project;
    private OurDateTime dateTime;


    

    @Test
    public void projectInProcessWhenStatusIsNeedsAction() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS);
        assertEquals(project.getStatus(), Status.VTODO_COMPLETED);
    }

    @Test
    public void getDueReturnsCorrectDateTime() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS, "filename");
        assertEquals(dateTime, project.getDue());
    }

    @Test
    public void getStatusReturnsCorrectStatus() {
        dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        project = new Project("title", "description", dateTime, Status.VTODO_IN_PROCESS, "filename");
        assertEquals(Status.VTODO_IN_PROCESS, project.getStatus());
    }
}