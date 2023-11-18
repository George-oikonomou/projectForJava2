ICSFilePath(String fileName) :
    takes a file name we want to open and checks if the file exists using its path and 
    if it does, it returns true

LoadEvents ():
    This method loads events from an ics file and saves it to the "events" ArrayList on "OurCalendar" class
    rootLogger: is a variable that hides the logs of the ical4j library so they do not output on the terminal
    inputStream: standard variable to get input stream from a file in java
    builder : an object from the ical4j library that helps "build" the inputStream in a way that we can use the 
              ical4j library on it to extract the contents easily
    calendar: an object that that creates a calendar where from there we can get our event components with the
              useful library methods
    the for loop loops throw these VEvents with getComponents (each component is an event)
    then we are checking the type of event that the component variable is
    THERE NEEDS TO BE A CATEGORIES PROPERTY IN THE ICS FILE DEFINING THE TYPE OF THE EVENT ELSE THE PROGRAM WILL
    NOT WORK!
    after we determine the type of the event we extract the values we need
    CASE EVENT :
    the dateTime string saved in the ics file is not in the format we need to the "ICSFormatToOurDateTime" method
    transforms the string to make it and OurDateTime object 
    now we have all the correct information in correct format to create an Event OBJECT
    CASE PROJECT:
    same thing here we check for the validity and change the format of the information
    CASE APPOINTMENT:
    >>
    after an event is created it gets added to the list and we set the ArrayList of the calendar

StoreEvents ():
    file: tries to open a file based on the path we gave . If the file exists it overides it else it creates it
    then we write the basic properties of an ics file (124-127).
    then we loop throw the events and save their properties in the file 
    each ics file should start with BEGIN VCALENDAR AND END WITH END CALENDAR same goes for the events
    thats basically it

FUNCTIONALITY
    this is an inner class of ICSFile and it has some static methods that help its usage 
    basically it has 2 transformers that change the format from the ones used in the Java Program
    to ones used in the ics file