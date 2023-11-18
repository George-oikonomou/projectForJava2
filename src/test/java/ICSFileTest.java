import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ICSFileTest {

    @Test
    public void testICSFilePathWithExistingFile(@TempDir Path tempDir) throws IOException {
        // Create a temporary file
        Path tempFile = Files.createTempFile(tempDir, "test", ".ics");

        // Call the method with the temporary file path
        boolean result = ICSFile.ICSFilePath(tempFile.toString());

        // Assert that the method returns true since the file exists
        assertTrue(result);
    }

    @Test
    public void testICSFilePathWithNonExistingFile() {
        // Provide a path to a non-existing file
        String nonExistingFilePath = "non-existing-file.ics";

        // Call the method with the non-existing file path
        boolean result = ICSFile.ICSFilePath(nonExistingFilePath);

        // Assert that the method returns false since the file doesn't exist
        assertFalse(result);
    }
    @Test
    public void loadEvents() {

    }

    @Test
    public void testStoreEvents() throws IOException {
        // setup
        String expectedContent = """
                BEGIN:VCALENDAR
                VERSION:2.0
                PRODID:-//My Calendar//EN
                CALSCALE:GREGORIAN
                BEGIN:VEVENT
                DTSTART:13/12/2023\t12:00
                SUMMARY:Event1
                DESCRIPTION:EventDescription1
                END:VEVENT
                BEGIN:VEVENT
                DTSTART:13/12/2023\t12:00
                SUMMARY:Project1
                DESCRIPTION:ProjectDescription1
                DEADLINE:13/12/2023\t12:00
                PROJECT_STATUS:Ongoing
                END:VEVENT
                BEGIN:VEVENT
                DTSTART:13/12/2023\t12:00
                SUMMARY:Appointment1
                DESCRIPTION:AppointmentDescription1
                DURATION:1
                END:VEVENT
                END:VCALENDAR
                """;


        ArrayList<Event> events = new ArrayList<>();
        OurDateTime dateTime = new OurDateTime(2023, 12, 13, 12, 0);
        events.add(new Event(dateTime, "Event1", "EventDescription1"));
        events.add(new Project(dateTime, "Project1", "ProjectDescription1", dateTime));
        events.add(new Appointment(dateTime, "Appointment1", "AppointmentDescription1", 1));

        Path tempDir = Files.createTempDirectory("testStoreEvents");
        String testFilePath = Paths.get(tempDir.toString(), "test.ics").toString();

        ICSFile icsFile = new ICSFile(testFilePath);
        icsFile.StoreEvents(events);

        Path filePath = Paths.get(testFilePath);
        String fileContent = Files.readString(filePath);

        assertTrue(Files.exists(filePath));
        assertTrue(Files.size(filePath) > 0);
        assertTrue(Files.isRegularFile(filePath));
        assertTrue(Files.isReadable(filePath));
        assertEquals(expectedContent, fileContent);
    }

    @Test
    public void testEmptyStoreEvents() throws IOException {
        // setup
        ArrayList<Event> events = new ArrayList<>();

        Path tempDir = Files.createTempDirectory("testStoreEvents");
        String testFilePath = Paths.get(tempDir.toString(), "test.ics").toString();

        ICSFile icsFile = new ICSFile(testFilePath);
        icsFile.StoreEvents(events);

        Path filePath = Paths.get(testFilePath);
        assertTrue(Files.exists(filePath));
        assertTrue(Files.size(filePath) > 0);
        assertTrue(Files.isRegularFile(filePath));
        assertTrue(Files.isReadable(filePath));
        //check if it contains any events or not
        String fileContent = Files.readString(filePath);
        assertFalse(fileContent.contains("BEGIN:VEVENT"));
        assertTrue(fileContent.contains("BEGIN:VCALENDAR"));
    }

    @Test
    public void testIcsFileFormat() throws IOException {
        Path tempDir = Files.createTempDirectory("testStoreEvents");
        String testFilePath = Paths.get(tempDir.toString(), "test.ics").toString();

        ICSFile icsFile = new ICSFile(testFilePath);
        icsFile.StoreEvents(new ArrayList<>());

        Path filePath = Paths.get(testFilePath);
        String fileContent = Files.readString(filePath);
        assertTrue(fileContent.contains("BEGIN:VCALENDAR"));
        assertTrue(fileContent.contains("VERSION:2.0"));
        assertTrue(fileContent.contains("PRODID:-//My Calendar//EN"));
        assertTrue(fileContent.contains("CALSCALE:GREGORIAN"));
        assertTrue(fileContent.contains("END:VCALENDAR"));

    }
}

