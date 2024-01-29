# *ProjectGUI class extends JPanel*

## Fields:
- ArrayList<ICSFile> allFiles
- JTextField title
- JTextArea description
- JDateChooser due
- JSpinner dueTimeSpinner
- JButton create
- SingleCalendarSelect calendarSelect

## Methods:
- ProjectGUI(ArrayList<ICSFile> allFiles): **constructor that creates a layout, the fields to create a project and a button that creates the project with the help of an actionListener and adds them to the PrintPanel.**
- createProject(): **checks if all the fields are filled correctly. If not, it returns a message, else, it creates a project and adds it to the arrayList with the events and shows a message to the user that it got created successfully. Then the fields get refreshed.**

### Inner Class: ButtonListener implements ActionListener
- actionPerformed(ActionEvent e): **when the user presses the create button, it calls the method createProject() that creates the project.**

## Usage:
- This class is used to create ,in the printPanel, the fields that need to be filled to create a project.