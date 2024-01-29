package  Models;

import net.fortuna.ical4j.model.property.Status;

import javax.swing.*;
import java.awt.*;

public class Project extends Event{
    private Status status;
    private OurDateTime due;    //date and time of project deadline
    private JPanel panel;


    public Project(String title, String description, OurDateTime due, Status status, String filename) {
        super(null, title, description,filename);
        this.due = due;
        this.status = status;
        setPanel();
    }
    public void setStatus(Status status) { this.status = status; }
    public Status getStatus() { return status; }
    public OurDateTime getDue() { return due; }
    public void setDue(OurDateTime due) { this.due = due; }

    @Override
    public void setPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(100, 85)); // Adjust the size as needed
        panel.add(new JLabel("Title: " + getTitle()));
        panel.add(new JLabel("Description: " + getDescription()));
        panel.add(new JLabel("Due Date: " + getDue()));
        panel.add(new JLabel("Status: " + getStatus().getValue()));
        panel.add(new JLabel("Calendar Name: " + getFileName()));
        panel.putClientProperty("uid", getUuid());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}