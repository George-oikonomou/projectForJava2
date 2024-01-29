# *ChangeStatusGUI class extends EventManagement*

## Fields:
- ArrayList<Project> projects

## Methods:
- ChangeStatusGUI(ArrayList<ICSFile> allFiles): **constructor that creates the fields with the help of the EventManagement's constructor.**
- search(String searchText): **searches for the projects that match the search text and when its found, it shows the project to the List.**
- fillEvents(): **fills the ArrayList projects with the projects of the files.**
- findSelectedEvent(): **finds the selected project and changes status with the method handleSelection.**
- handleSelection(Project project): **when a project is selected, it changes the status of the project.**

## Usage:
- This class is used to change the status of a project.