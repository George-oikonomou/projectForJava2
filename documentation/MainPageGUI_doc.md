# *MainPageGUI class extends from JFrame*

## Fields:
- OurMenuGUI menu
- JButton newEventButton
- JButton editEventButton
- JButton changeProjectStatusButton
- JButton printEventButton
- JPanel menuPanel
- JPanel reminderPanel
- boolean isReminder
- Pages currentPage
- JPanel printPanel
- enum Pages

## Methods:
- MainPageGUI(): **creates and sets panels, color, sizes and layouts with the help of some methods below.**
- getBackToPage(): **its used whenever a user is adding a new file, and it refreshes the printPanel.** 
- getPrintPanel(): **returns the printPanel.**
- initializeFrame(): **names the frame, sets the size, sets the location, sets the layout, sets the default close operation, adds the three panels and the menu.**
- refreshReminderPanel(): **refreshes the reminder panel by revalidating and repainting the panel.**
- createOptionsPanel(): **sets the layout, adds the buttons and sets the background color.**
- createPrintPanel(): **creates the printPanel, sets background color and the size.**
- createReminderPanel(): **creates the reminderPanel, sets the layout, adds the label, the live time and the reminder list.**
- createButtons(): **creates the buttons with the method createButton.**
- createButton(String text, String image, String toolTip): **creates a button with the given text, image and toolTip.**

### Inner Class: Functionality implements ActionListener
- actionPerformed(ActionEvent e): **handles the events of the buttons and for each action the printPanel gets revalidated and repainted.**
- handlePrint(): **adds in the printPanel an object PrintGUI to print the desirable events.**
- handleChangeStatus(): **adds in the printPanel an object ChangeProjectStatusGUI to change the status of a project.**
- handleEditEvent(): **adds in the printPanel an object EditEventGUI to edit an event.**
- handleNewEvent(): **creates two buttons, to choose to create an appointment or a project and adds them into the printPanel. Depending on the choice, we are creating the suitable event.**
- executeCreateAppointmentListener(): **creates an appointment and adds it to the printPanel and then revalidates and repaints the panel.**  
- executeCreateProjectListener(): **creates a project and adds it to the printPanel and then revalidates and repaints the panel.**

## Usage:
This class is used to create the main page of the program.
- It has a menu object, which is used to open, create and save a file.
- It contains three panels: The menuPanel includes the buttons. The printPanel is used to print messages and show textFields. 
The reminderPanel includes the reminder label, the live time and the reminder list.
- There are methods that are used to refresh the panel, create the panels and buttons.
- *Functionality*: This class implements ActionListener. It has a method to handle the events of the buttons. 
It's connecting the buttons with the methods of the menu object. 