import javax.swing.text.JTextComponent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
