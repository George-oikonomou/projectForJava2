# *ClearTextFocusListener class implements FocusListener*

## Fields:
- String defaultText
- JTextComponent textComponent

## Methods:
- ClearTextFocusListener(String defaultText, JTextComponent textComponent): **Constructor**
- focusGained(FocusEvent e): **when the text component gains focus, the default text is cleared**
- focusLost(FocusEvent e): **when the text component loses focus, the default text is restored**

## Usage:
- This is used for the text fields in the GUI that have default text in them. When the user clicks on the text field, the default text is cleared. When the user clicks out of the text field, the default text is restored if the user did not enter any text.