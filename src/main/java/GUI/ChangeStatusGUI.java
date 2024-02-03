package  GUI;
import Models.ICSFile;
import Models.Project;
import net.fortuna.ical4j.model.property.Status;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChangeStatusGUI extends EventManagement {
    private ArrayList<Project> projects;

    public ChangeStatusGUI(ArrayList<ICSFile> allFiles) {
        super(allFiles,"Enter Project Title");
    }

    public void search(String searchText){

        for (Project project : projects) {
            if (project.getTitle().toLowerCase().startsWith(searchText)) {
                getListModel().addElement(project.getPanel());
            }
        }
        getEventList().setPreferredSize(new Dimension(230, getListModel().size() * 100));

    }

    public void fillEvents() {
        projects = getAllFiles().get(getCalendarSelect().getSelectedIndex()).getCalendar().getProjects();
        performLiveSearch();
    }
    @Override
    public void findSelectedEvent() {
        JPanel selectedPanel = getListModel().getElementAt(getEventList().getSelectedIndex());

        String uid = (String) selectedPanel.getClientProperty("uid");

        for (Project project : projects) {
            if (project.getUuid().equals(uid)) {
                handleSelection(project);
                break;
            }
        }
    }

    private void handleSelection(Project project) {
        // Toggle the status of the project
        if (project.getStatus() == Status.VTODO_IN_PROCESS) {
            project.setStatus(Status.VTODO_COMPLETED);
        } else {
            project.setStatus(Status.VTODO_IN_PROCESS);
        }
        project.setPanel();
        MainPageGUI.setIsSaved(false);
        performLiveSearch();
    }

}
