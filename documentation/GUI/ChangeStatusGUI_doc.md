# *ChangeStatusGUI class extends JPanel*

## Fields:
- ArrayList<ICSFile> allFiles
- JList<JPanel> projectList
- DefaultListModel<JPanel> listModel
- JTextField enterTitle
- JButton searchTitle
- SingleCalendarSelect calendarSelect
- ArrayList<Project> projects

## Methods:
- ChangeStatusGUI(ArrayList<ICSFile> allFiles): **constructor that creates the fields to change the status of a project and a button that changes the status of the project with the help of an actionListener and adds them to the PrintPanel.**
- setupUI():
- addComponents():
- performLiveSearch():
- fillProjects():
- FindSelectedProject():
- handleSelection(Project project):

### Inner Class: ButtonListener implements ActionListener
- actionPerformed(ActionEvent e): 

### Inner Class: LiveSearchListener implements DocumentListener
