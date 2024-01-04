import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AppointmentGUI extends JPanel {
    private final DateInputGUI startDateGUI;
    private final DateInputGUI endDateGUI;
    private final JTextField title;
    private final JTextArea description;
    private final JButton create;
    private final JLabel start;
    private final JLabel end;

    public AppointmentGUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(410, 350));
        this.startDateGUI = new DateInputGUI();
        this.endDateGUI = new DateInputGUI();
        this.title = new JTextField("Appointment Name", 10);
        this.description = new JTextArea("Appointment Description", 5, 20);
        this.create = new JButton("Create");
        this.start = new JLabel("Enter the start date of the appointment");
        this.end = new JLabel("Enter the end date of the appointment");

        // Use a JScrollPane for the JTextArea to enable scrolling if needed
        JScrollPane descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        this.title.addFocusListener(new ClearTextFocusListener("Appointment Name", title));
        this.description.addFocusListener(new ClearTextFocusListener("Appointment Description", description));
        this.create.addActionListener(new ButtonListener());
        add(start);
        add(startDateGUI);
        add(end);
        add(endDateGUI);
        add(title);
        add(descriptionScrollPane);
        add(create);
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {


        }
    }

    private class ClearTextFocusListener implements FocusListener {
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
}