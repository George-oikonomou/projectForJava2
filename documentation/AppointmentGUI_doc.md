# *AppointmentGUI class extends JPanel*

## Fields:
- ArrayList<ICSFile> allFiles
- JDateChooser startDateChooser
- JSpinner startTimeSpinner
- JDateChooser endDateChooser
- JSpinner endTimeSpinner
- JTextField title
- JTextArea description
- JScrollPane descriptionScrollPane
- JButton create
- SingleCalendarSelect calendarSelect

## Methods:
- AppointmentGUI(ArrayList<ICSFile> allFiles): **constructor that creates a layout, the fields to create an appointment and a button that creates the appointment with the help of an actionListener.**
- addComponents(): **adds the fields, that are needed to create an appointment, to the panel.**
- createAppointment(): **checks if all the fields are filled correctly. If not, it returns a message, else, it creates an appointment and adds it to the arrayList with the events and shows a message to the user that it got created successfully. Then the fields get refreshed.** 

### Inner Class: ButtonListener implements ActionListener
- actionPerformed(ActionEvent e): **when the user presses the create button, it calls the method createAppointment() that creates the appointment.**

## Usage:
- This class is used to create ,in the printPanel, the fields that need to be filled to create an appointment.