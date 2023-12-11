Superclass:

    Event: This class has a constructor, Setters and Getters, and a method that generates UUID for the event.
                
                In this class we have the fields:
                -dateTime for the new event
                -title and description for the event
                -uuid which is an id that is given to each event, in the file


Subclasses:
    
    Appointment:
    In this class we have a field for duration of each appointment, and an endDateTime.
    This class, has 2 constructors one for duration and another one for start and end DateTime, Setters and Getters and toString().

        Methods:
        -setDurationWithDtend: gets the start and the end of DateTime, and it recieves the duration in minutes 
        between the two DateTimes, then it becomes a string that represents the days, hours and minutes,
        and duration recieves a new value.

        -setDurationWithIcsDuration: 

        -parseIntOrZero: gets a String value, if it's not null it returns the value
        as a string, else returns 0.

        -calculateDurationInDays: gets the duration in minutes,
        it transforms it to a string of numbers for days, hours and minutes, that gets returned.

    Project:
    This class, has a constructor, Setters and Getters and toString().

        In this class we have the fields:
        -status
        -due for the deadline of the project
        -isFinished to check if the project is finished or not