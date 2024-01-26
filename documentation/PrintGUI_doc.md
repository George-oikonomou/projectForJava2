# *PrintGUI class extends JPanel*

## Fields:
- ArrayList<ICSFile> allFiles
- JList<JPanel> printList
- DefaultListModel<JPanel> listModel
- ArrayList<ICSFile> selectedFiles
- ArrayList <Event> selectedEvents
- JScrollPane scrollPane


## Methods:
- PrintGUI(ArrayList <ICSFile> allFiles): **sets the layout, the background the buttons, adds the allFiles ArrayList in a variable and then in the printPanel. Also, each button has an actionListener and calls a method for multiple files.**
- printEvents(): **prints the events in the printList to the printPanel, based on the clicked button.**
- JButton createStyledButton(): **creates a button with a specific style for the Calendars.**
- selectMultipleFiles(): **adds the selected files in the selectedFiles ArrayList.**
- performAction(App.AppChoices choice): **if the user didn't select a file, it shows a message. Else, based on the button the user clicked, it prints the events from the selected files and prints them with a specific method from the Calendar.**

## Usage:
- This class is used to print specific events based on the clicked button from the user and the selected files.
- It does that with the help of the arrayLists and the methods from the Calendar class.