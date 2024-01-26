## <ins> How to use 

### :RUN:
    java -jar  ProjectForJava2-1.0-SNAPSHOT.jar

This window should be created

![Local Image](src/main/resources/screenshotsForReadme/start.png)

Navigate to the "FILES" button on the menubar 

![Local Image](src/main/resources/screenshotsForReadme/menu.png)

<li>Add calendar</li> will open a file navigator that will let you choose a file you want to load
in the application, if you want to add another calendar repeat the same process, if you want to
create a new ics file just write the name of it in the File Name Section

<li>Save Calendar</li> will update the ics files you have loaded with the changes 
you did on the app 


![Local Image](src/main/resources/screenshotsForReadme/FileNavigator.png)

Great! you have loaded or created the calendar files you need to use the application. Now we will go over
on how to use each of the functionalities of the app. Lastly you should be able to see a section
being created on the frame, this is where the reminders are going to pop up! see more below.

![Local Image](src/main/resources/screenshotsForReadme/MainwithRemindresSection.png)


## FUNCTIONS

### ADD 

When you press the ADD button you are going to be prompted with 2 options
<li> Create appointment</li>
<li> Create project </li>

![Local Image](src/main/resources/screenshotsForReadme/ADD.png)

Press the Appointment or Project button and fill in the required Information, then select one of the calendars
that you have already loaded to save the new event. Press Create

![Local Image](src/main/resources/screenshotsForReadme/Appointment.png)

Great! you have added an event to an ics file. Don't forget to save The calendar!
Same goes for Project!

### EDIT

When you press the EDIT button you are going to be prompted with an interface asking you for 2 things
<li> A calendar where the event you want to edit is in</li>
<li> The title of the event you want to edit</li>

As you are typing you are going to see the events you are looking for

![Local Image](src/main/resources/screenshotsForReadme/Edit.png)

By pressing on an event you will be prompted an interface where you can make changes to the chosen event.
Remember you have to save you changes in the menu in order for the file to be changed

![Local Image](src/main/resources/screenshotsForReadme/EditIn.png)

### STATUS (change project from in progress to finished)

Works the same way as the event , except from the fact there will be no interface, just press the project to 
change its status

### PRINT

By pressing the PRINT button you will be prompted with an interface where 
<li> You are asked to select a number of calendars from the calendar you have already loaded </li>
<li> The time period you want to see events from these calendars</li>

Lets say you have loaded 5 calendars and want to see the events until the end of the month from 3 of them, well you can
(press ctrl if you want to select multiple files)

![Local Image](src/main/resources/screenshotsForReadme/Print.png)
![Local Image](src/main/resources/screenshotsForReadme/Print2.png)

### REMINDERS 

If an event that has been loaded either from a file or is created in the app, and is within 30 minutes away from the current time.
Will appear in the Reminders box, until it starts. It will also make a sound when it first goes in, which 30 minutes before it starts

![Local Image](src/main/resources/screenshotsForReadme/Reminders.png)

Lastly the current time is shown on the top left of the app

## Addition Information


The ics file should follow the correct format of an ics file https://datatracker.ietf.org/doc/html/rfc5545
<li> You can use an external ics file from example google calendar </li>
<li> For the program to function properly the ics file should have some properties for each
component (see documentation for ISCFile)</li>
<li>All the information the program asks is required, except for the "description" which is optional. Any incorrect or not logical input
the program will ask you to reenter the information</li>
<li>After you added all your event the program (and saved) the app will save those events in the file with the events it already (if
it had any)</li>
<li>The program will close when you press close button</li>

# <ins>#collaborators</ins>

<li><em>George Oikonomou (it2022078) </em></li>

<li><em>Spyros Georgiou (it2022010) </em></li>

<li><em>Georgia Vrettakou (it2022009) </em></li>
