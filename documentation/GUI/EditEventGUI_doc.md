# *EditEventGUI class extends EventManagement*

## Fields:
- Event eventToEdit
- ArrayList<Event> events
- JTextArea titleField
- JTextArea descriptionField
- JDateChooser endDateChooser = null
- JDateChooser startDateChooser
- JSpinner startTimeSpinner
- JSpinner endTimeSpinner
- JScrollPane titleScrollPane
- JScrollPane descriptionScrollPane
- JPanel editPanel

## Methods:
- EditEventGUI(ArrayList<ICSFile> allFiles): **constructor that creates the fields with the help of the EventManagement's constructor.**
- search (String searchText): **searches for the events that match the search text and when its found, it shows the event to the List.**
- fillEvents(): **fills the ArrayList events with the events of the files.**
- findSelectedEvent(): **finds the selected event and shows it to the editPanel.**
- createEditModal(): **creates the editPanel.**
- initializeFields(): **initializes the fields of the editPanel and sets sizes text areas so the user can change the fields of the event.**
- addToPanel(): **adds the fields to the editPanel.**
- handleOkResponse(): **when the user presses the ok button, it changes the fields of the event.**

## Usage:
- This class is used to edit an event that's being searched by the user.