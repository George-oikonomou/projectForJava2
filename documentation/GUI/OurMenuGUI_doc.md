# *OurMenuGUI class extends from JMenuBar*

## Fields:
- JMenuItem addCalendar
- JMenuItem saveCalendar
- ArrayList<ICSFile> allFiles
- MainPageGUI mainPageGUI

## Methods:
- OurMenuGUI(MainPageGUI mainPageGUI): **creates an object mainPageGUI, a JMenu and sets its Border. It also sets JMenuItems to add and save a calendar and adds them to the JMenu.**
- getAllFiles(): **returns the ArrayList allFiles.**
- addCalendar(): **creates a JFileChooser and if the user chooses a file, it checks if the file is already added. If it is not, it creates a new ICSFile and adds it to the ArrayList allFiles.**
- createNewIcsFile(File selectedFile): **creates a new calendar and adds it to the ArrayList allFiles.**
- loadIcsFile(File selectedFile): **loads the file that got selected.**
- calendarAlreadyAdded(File selectedFile): **checks if the file is already added with an enhanced for-loop.**

### Inner Class: Functionality implements ActionListener
- actionPerformed(ActionEvent e): **depending on the action, it calls the suitable method to add or save a file.**

## Usage: 
- This class is used to create the menu of the program to pick a file, or add a new one, and you can save it.
- The arrayList allFiles is used to store the files that are added and to check if a file is already added.
- mainPageGUI is used to call the methods of the MainPageGUI class when the user adds a new file to refresh the printPanel.
- *Functionality*: This class implements ActionListener. Its method controls adds or saves a calendar.


