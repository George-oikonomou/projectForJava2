- properties : year, month, day, hour, minute: the core values of an ourDateTime object
- date, time, icsFormat, calculationFormat : different formats we create with the core properties that
we use throughout the program
- the first constructor creates and OurDateTime object using values we give it
- second constructor creates an OurDateTime object that has the current time, live or custom time
use this to change the current time
- then we have different methods to set these formats get them and a toString
=


    CustomCurrentTime:
    It's a class that implements TimeTeller, so it can be able to change the LocalDateTime, to whatever DateTime we want.
    It has a constructor for the field currentTime and the LocalDateTIme now() returns the currentTime.