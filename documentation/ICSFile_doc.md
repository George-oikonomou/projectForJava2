ICSFilePath(String fileName) :
    takes a file name we want to open and checks if the file exists using its path and 
    if it does it returns true

LoadEvents (string fileName):
    for the time being it uses the ical4j library to try and open a ics file with events
    and save them to the array list . right now only the start of it has been made
    check here http://www.ical4j.org/examples/parsing/ if it cant it gives throws an excetoion
    that will be handled.