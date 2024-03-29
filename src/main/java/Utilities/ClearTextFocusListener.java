package Utilities;

import javax.swing.text.JTextComponent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * A FocusListener that clears the text of a JTextComponent when it gains focus
 * and restores the default text when it loses focus.
 */
public class ClearTextFocusListener implements FocusListener {
    private final String defaultText;
    private final JTextComponent textComponent;

    public ClearTextFocusListener(String defaultText, JTextComponent textComponent) {
        this.defaultText = defaultText;
        this.textComponent = textComponent;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (textComponent.getText().equals(defaultText)) {
            textComponent.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textComponent.getText().isEmpty()) {
            textComponent.setText(defaultText);
        }
    }
}
