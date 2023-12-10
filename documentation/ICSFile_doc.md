ICSFilePath(String fileName) :
    - sets the name of the file 

loadEvents ():
    - we are using the logger class to hide logs that would be printed in the terminal from the ical4j library
    - we initialize an arraylist where the events that come from the file will be saved in
    - we create an input stream of the file, then we build with it a calendar object from the ical4j
    this object will give us the property of extracting the events as different components.
    - we are looping through the events from the calendar we just created.
    - we check what type of events are in this calendar if they are vEvents (appointments) we create
    an appointment object using the data from the component, and we add it to the list same for vTodo (projects)
    - if there is something wrong with the information of a single event, for example a vTodo does not contain
    a status the application will move on to the next component
    - if there is a significant syntax error in the file the program will crash with a message
    - lastly we get some information about the calendar 
    - if the file does not exist it will also prompt a massage

createAppointment (), createProject:
    - gets the "must have" and the optional information of a component and creates the object, if a
    "must have" property is missing it will throw an exception that will be handled by the caller. 
    - returns the corresponding object



StoreEvents ():
    - creates a calendar object from the ical4j library
    - gets the properties of the ourCalendar and adds them to the new one
    - loops through the events of ourCalendar and creates vTodo and vEvent objects
    and adds them to the component of the calendar
    - if the file does not exist it will create a new one and if it does it will overwrite it

createVEvent (), createVTodo ():
    - they get the properties of the events from our calendar and create the corresponding objects 

