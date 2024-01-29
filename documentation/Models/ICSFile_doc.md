* [ICSFile class General Information](#ICSfile-class-general-information)
  * [Fields](#fields)
  * [Methods](#methods)
    * [ICSFile](#icsfile-string-filepath)
    * [getFileName](#getfilename)
    * [loadEvents](#loadevents)
    * [createAppointment](#createappointment)
    * [createProject](#createproject)
    * [storeEvents](#storeevents)
    * [createVevent/createVtodo](#createveventcreatevtodo)
    * [icsFormatToOurDateTime](#icsformattoourdatetime)
<hr>

# ICSFile Class General Information

- The ICSFile class is responsible for making sure the
program can create its own calendar when provided with 
an ics file, and also export that calendar to a valid ics file
- in order for the files system to work an ics file will have to meet
certain requirements, if these requirements are not met it will provide a \
message depending on the error.

<hr>

## fields
- private String fileName
- private Calendar ourCalendar

<hr>

## methods

<hr>

### `ICSFile (String filepath)`
- sets the name of the file and creates a new Calendar. 

<hr>

### `getFileName()`
- returns the name of the file from the path.


<hr>

### `loadEvents()`
- we are using the logger class to hide logs that would be printed in the terminal from the ical4j library
- we initialize an arraylist where the events that come from the file will be saved in
- we create an input stream of the file, then we build with it a calendar object from the ical4j
this object will give us the property of extracting the events as different components.
- we are looping through the events from the calendar we just created.
- we check what type of events are in this calendar if they are vEvents (appointments) we create
an appointment object using the data from the component with [createAppointment()](#createappointment), 
and we add it to the list same for vTodo (projects) [createProject()](#createproject)
- if there is something wrong with the information of a single event, for example a vTodo does not contain
a status the application will move on to the next component
- if there is a significant syntax error in the file the program will crash with a message
- lastly we get some information about the calendar 
- if the file does not exist it will also prompt a massage

<hr>

### `createAppointment()`
- gets the "must have" and the optional information of a vEvent and creates the appointment.
- if a "must have" property is missing it will throw an exception that will be handled by the caller.
- must have properties are : summary, startDate/time, endDate/time or duration,
- we use [IcsFormatToOurDateTime](#icsformattoourdatetime) to create a good object to handle the times
- optional are : description 
- returns the appointment

<hr>

### `createProject`
- gets the "must have" and the optional information of a vToto and creates the project 
- if a "must have" property is missing it will throw an exception that will be handled by the caller.
- must have properties are : due, summary, status
- - we use [IcsFormatToOurDateTime](#icsformattoourdatetime) to create a good object to handle the due 
- optional are : description
- returns the project

<hr>

### `StoreEvents()`
- creates a calendar object from the ical4j library
- gets the properties of the ourCalendar and adds them to the new one
- loops through the events of ourCalendar and creates vTodo and vEvent objects
and adds them to the component of the calendar
- if the file does not exist it will create a new one and if it does it will overwrite it

<hr>

### `createVevent()/createVtodo()`
- they get the properties of the events from our calendar and create the corresponding objects 

<hr>

### `IcsFormatToOurDateTime()`
- takes the format of the ics version of a date/time (due, dtstart, dtend...)
- creates objects with this information that has formats that are useful
to the application

<hr>

- [Event Class General Information](Events_doc.md)
- [App Class General Information](../Main/App_doc.md)
- [OurCalendar Class General Information](OurCalendar_doc.md)
- [OurDateTime Class General Information](OurDateTime_doc.md)
- [TimeTeller Class General Information](TimeTeller_doc.md)
- [Validate Class General Information](../Validate_doc.md)

[Back to top](#icsfile-class-general-information)