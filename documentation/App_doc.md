* [App Class General Information](#app-class-general-information)
    * [Methods](#methods)
        * [main](#mainstring-args)
        * [handleSingleArgument](#handlesingleargumentstring-arg)
        * [handleDoubleArguments](#handledoubleargumentsstring-args)
<hr>

# App Class General Information

- The `App` class is the main class of the program.
- It contains the main methods
- it initializes the `OurCalendar` object and the enum `AppChoices`
- AppChoices is an enum that contains the different choices available to the user when executing the program.
- It is responsible for redirecting and for the flow of the program.

<hr>

## Methods

<hr>

### `main(String[] args)`

- This method is the main method of the program.
- It is responsible for redirecting the flow of the program.

- if the user inputs one argument (file name )
    - it calls [handleSingleArgument](#handlesingleargumentargv0)

- if the user inputs two arguments it will print the corresponding list of events ( time frame / mode , name of file)
    - it calls [handleDoubleArguments](#handledoubleargumentsstring-args)

<hr>

### `handleSingleArgument(String arg)`


- This method is responsible for handling the single argument case.
- it creates a new file using the ics library and the file name provided by the user.
- It creates a new `OurCalendar` object and loads the events from the file.
    - It calls the method [AddEvents](OurCalendar_doc.md#addevents-) from the OurCalendar class and prompts the user to create events for the calendar.
    - it calls the method [storeEvents](ICSFile_doc.md#storeevents-) from the ICSFile class to store the events in the ICS file.

<hr>



### `handleDoubleArguments(String args[])`

- This method is responsible for handling the double argument case.

- It uses a stream to match the provided argument with predefined choices.

- It filters the enum constants based on the comparison with the argument.

- It finds the first matching element and assigns it to the 'choice' variable ,if no element is found the program exits.

- it Creates an instance of the ICSFile class and calls [loadEvents](ICSFile_doc.md#loadevents-) to load the events from the file.

- depending on the given argument / choice it will print the corresponding list of events.

- it uses the :
    - [printUpcomingEvents](OurCalendar_doc.md#printupcomingevents) from the OurCalendar class to print the upcoming events.
    - [printOldEvents](OurCalendar_doc.md#printoldevents) from the OurCalendar class to print the old events.
    - [printUnfinishedProjects](OurCalendar_doc.md#printunfinishedprojects) from the OurCalendar class to print the unfinished projects.
  

<hr>

- [Event Class General Information](Events_doc.md)
- [ICSFile Class General Information](ICSFile_doc.md)
- [OurCalendar Class General Information](OurCalendar_doc.md)
- [OurDateTime Class General Information](OurDateTime_doc.md)
- [TimeTeller Class General Information](TimeTeller_doc.md)
- [Validate Class General Information](Validate_doc.md)

[Back to top](#app-class-general-information)