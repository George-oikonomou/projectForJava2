* [OurDateTime class General Information](#ourdatetime-class-general-information)
  * [Methods](#methods)
    * [time](#timeint-minute-int-hour-boolean-includeseparators)
    * [date](#dateint-year-int-month-int-day-boolean-includeseparators)
    * [calculateCalculationFormat](#calculatecalculationformatint-year-int-month-int-day-int-hour-int-minute)
    * [dateAndTime](#dateandtime)
<hr>

# OurDateTime

## OurDateTime Class General Information
- This class has :
    - `3 constructor`
      - one that sets a `specific DateTime` from the user for the events
      - one that sets the `current real or custom DateTime`
      - and one for the live time
    - Setters and Getters
    - toString()
- In this class we have the field:
    - year
    - month
    - day
    - hour
    - minute
    - date
    - time
    - dayOfWeek
    - icsFormat
    - CalculationFormat for the date & time
    - CalculationFormat for the Reminder
    - dateFormat for the localDateTime
<hr>

## Methods

<hr>

- ### `time(int minute, int hour, boolean includeSeparators)`
  - Gets the minutes, the hour and a boolean if the time will be in this form `HH:MM` or in this form `HHMM`.
  - The hour and the minutes become a string based on the chosen form.
  - Then it returns the string.

- ### `date(int year, int month, int day, boolean includeSeparators)`
  - Gets the year, the month, the day and a boolean if the time will be in this form `YYYY/MM/DD` or in this form `YYYYMMDD`.
  - The year, the month and the days become a string based on the chosen form.
  - Then it returns the string.
- ### `calculateCalculationFormat(int year, int month, int day, int hour, int minute)`
  - Sets a date and time with no separators and returns a long number.

- ### `dateAndTime()`
  - The user types the desirable year, month, day, hour and minute.
  - Returns the new OurDateTime object.

<hr>

- [Event Class General Information](Events_doc.md)
- [ICSFile Class General Information](ICSFile_doc.md)
- [OurCalendar Class General Information](OurCalendar_doc.md)
- [TimeTeller Class General Information](TimeTeller_doc.md)
- [App Class General Information](App_doc.md)
- [Validate Class General Information](Validate_doc.md)

[Back to top](#ourdatetime-class-general-information)