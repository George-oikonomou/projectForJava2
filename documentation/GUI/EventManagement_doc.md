# *EventManagement class extends JPanel*

## Fields:
- String message
- ArrayList<ICSFile> allFiles
- private JList<JPanel> eventList
- DefaultListModel<JPanel> listModel
- JTextField enterTitle
- JButton searchTitle
- SingleCalendarSelect calendarSelect

## Methods:
- EventManagement(ArrayList<ICSFile> allFiles, String message): **constructor that creates the fields.**
- setupUI(): **sets up the UI of the JPanel.**
- ddComponents(): **adds the components to the JPanel.**
- fillEvents(): **fills the ArrayList projects with the projects of the files.**
- findSelectedEvent(): **finds the selected project and changes status with the method handleSelection.**
- performLiveSearch(): **searches for the projects that match the search text and when its found, it shows the project to the List.**
- search(String searchText): **searches for the projects that match the search text and when its found, it shows the project to the List.**

### Inner class: LiveSearchListener implements DocumentListener
- insertUpdate(DocumentEvent e): **calls the method performLiveSearch.**
- removeUpdate(DocumentEvent e): **calls the method performLiveSearch.**
- changedUpdate(DocumentEvent e)
- **This class is used for the Live searching of a title.**

## Usage:
- This class is used to manage the events of the calendar and is a superclass that gets extended by ChangeStatusGUI and EditEventsGUI.