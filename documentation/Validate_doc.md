
* [Validate Class General Information](#validate-class-general-information)
    * [Methods](#methods)
        * [strInput()](#strinput)
        * [checkAndReturnIntBetween](#checkandreturnintbetweenint-lowerbound-int-upperbound)
        * [DateTime(OurDateTime startDate)](#datetimeourdatetime-startdate)
        * [printMessageAndGetIntBetween(String prompt, int lowerBound, int upperBound)](#printmessageandgetintbetweenstring-prompt-int-lowerbound-int-upperbound)
        * [getDaysInMonth(int month, int year)](#getdaysinmonthint-month-int-year)
        * [checkIfTitleExists(OurCalendar ourCalendar, String title, int type)](#checkiftitleexistsourcalendar-ourcalendar-string-title-int-type)
        * [Title(OurCalendar calendar, int type)](#titleourcalendar-calendar-int-type)
        * [Other Helper Methods](#other-helper-methods)

# Validate Class General Information

The `Validate` class provides methods for validating user input and performing various checks within the program.

## Methods

<hr>

### `strInput()`

- This method validates user-provided input.
- It ensures the input is not empty and is a string.
- If the input is valid, it returns the input. otherwise, it prompts the user to enter valid input again and again.

<hr>

### `checkAndReturnIntBetween(int lowerBound, int upperBound)`

- This method takes two integers as parameters, representing the lower and upper bounds.
- It ensures the input is an integer within the specified range.
    - It uses the `strInput()` method for additional input validation,making input like "1 1 1 1" invalid unlike `scanner.nextInt()`.
- If the input is valid, it returns the input. otherwise, it prompts the user to enter a valid integer within the given range again and again.

<hr>

### `DateTime(OurDateTime startDate)`

- This method ensures the creation of a new `OurDateTime` object with a date and time after the provided start date.
- It prompts the user for the year, month, day, hour, and minute to construct the new `OurDateTime` object.
- it first checks that the year is the same or after the start date's year.
- if they are the same then it checks that the month is the same or after the start date's month and this logic continues for the day, hour, and minute.
    - it uses the `printMessageAndGetIntBetween` method for the messages and the bound validation.
<hr>


### `printMessageAndGetIntBetween(String prompt, int lowerBound, int upperBound)`

- This method displays a message to the user and returns an integer within the lowerBound and upperBound.
- It uses the `checkAndReturnIntBetween` to ensure that the input is within the specified range.

<hr>


### `getDaysInMonth(int month, int year)`

- This method returns the number of days in a given month, considering leap years.

<hr>


### `checkIfTitleExists(OurCalendar ourCalendar, String title, int type)`

- This method checks if a title already exists for a given event type in the provided calendar.
  - it uses the `eventSearch(title, type)` method from the [OurCalendar](OurCalendar_doc.md) class to search for the title.
- It returns `true` if the title is found for the `specified type`.Different Types are allowed to have the same Title.
- if it is not found, it returns `false`.

<hr>


### `Title(OurCalendar calendar, int type)`

- This method is used to get a unique title for a new event for a given event type in the provided calendar.
  - it uses the `checkIfTitleExists` method to check if the title already exists.
- if it does, it prompts the user to enter a new title until a unique title is provided.

<hr>


### Other Helper Methods
-  the purpose of these methods are for the stage 2 of the project currently they are typical print methods.
- `println(Object obj)`: Prints the specified object with a newline. (replaces `System.out.println()`)
- `print(Object obj)`: Prints the specified object. (replaces `System.out.print()`)

<hr>

<a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ"><img src="https://img.shields.io/badge/Documentation-click_Here-blue" alt="Documentation"></a>

- [Event Class General Information](Events_doc.md)
- [ICSFile Class General Information](ICSFile_doc.md)
- [OurCalendar Class General Information](OurCalendar_doc.md)
- [OurDateTime Class General Information](OurDateTime_doc.md)
- [TimeTeller Class General Information](TimeTeller_doc.md)


[Back to top](#validate-class-general-information)