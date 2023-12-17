## <ins> How to use 

### :MODE 1:
    java -jar  ProjectForJava2-1.0-SNAPSHOT.jar [functionality] [file]
    functionality: day, week, month, pastday, pastweek, pastmonth, todo, due
    file : example.ics

The ics file should follow the correct format of an ics file https://datatracker.ietf.org/doc/html/rfc5545
<li> You can use an external ics file from example google calendar </li>
<li> For the program to function properly the ics file should have some properties for each
component (see documentation for ISCFile)</li>
<li>The program will print the events with their information depending on the functionality you chose</li>
<li>The program will close</li>

### :MODE 2:
    java -jar  ProjectForJava2-1.0-SNAPSHOT.jar [file]
    file : example.ics

The ics file should follow the correct format of an ics file https://datatracker.ietf.org/doc/html/rfc5545
<li> You can use an external ics file from example google calendar </li>
<li> For the program to function properly the ics file should have some properties for each
component (see documentation for ISCFile)</li>
<li>The program will ask you to add a new event to the calendar</li>
<li>All the information the program asks is required. Any incorrect or not logical input
the program will ask you to reenter the information</li>
<li>After you added all your event the program will save those events in the file with the events it already (if
it had any)</li>
<li>The program will close</li>

# <ins>#collaborators</ins>

<li><em>George Oikonomou (it2022078) </em></li>

<li><em>Spyros Georgiou (it2022010) </em></li>

<li><em>Georgia Vrettakou (it2022009) </em></li>
