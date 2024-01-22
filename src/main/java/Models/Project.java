package  Models;

import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import java.awt.*;

public class Project extends Event{
    private Status status;
    private OurDateTime due;    //date and time of project deadline
    private boolean isFinished = false;     // boolean value that tells if the project is finished
    private JPanel panel;

    public void setFinished(boolean finished) { isFinished = finished; }

    public Project(String title, String description, OurDateTime due, Status status) {
        super(null, title, description);
        this.due = due;
        this.status = status;
        setFinished(status == Status.VTODO_COMPLETED);
        setPanel();
    }
    public void setStatus(Status status) { this.status = status; }
    public Status getStatus() { return status; }
    public OurDateTime getDue() { return due; }
    public void setDue(OurDateTime due) { this.due = due; }
    public boolean getIsFinished() { return isFinished; }

    @Override
    public String toString() {
        return String.format("""
        Project:
            title: %s
            description: %s
            due: %s
            status: %s
        """, getTitle(), getDescription(), getDue(), getStatus().getValue());
    }

    @Override
    public void setPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(100, 80)); // Adjust the size as needed
        panel.add(new JLabel("Title: " + getTitle()));
        panel.add(new JLabel("Description: " + getDescription()));
        panel.add(new JLabel("Due Date: " + getDue()));
        panel.add(new JLabel("Status: " + getStatus().getValue()));
        panel.putClientProperty("uid", getUuid());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}