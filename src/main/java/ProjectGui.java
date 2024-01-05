import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class ProjectGui extends JPanel {

    private final JTextField title;
    private final JTextArea description;
    private final JDateChooser due;
    private final JSpinner dueTimeSpinner;
    private final JButton create;

    public ProjectGui() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(410, 350));


        this.due = new JDateChooser();
        JTextField dateTextField = ((JTextField) due.getDateEditor().getUiComponent());
        dateTextField.setEditable(false);
        due.setPreferredSize(new Dimension(100, 20));

        // Set the minimum date for the startDateChooser to January 1, 2024
        Calendar minDateCalendar = Calendar.getInstance();
        minDateCalendar.set(2024, Calendar.JANUARY, 1);
        due.setMinSelectableDate(minDateCalendar.getTime());

        this.title = new JTextField("Enter Title here", 10);
        this.title.setMaximumSize(new Dimension(300, title.getPreferredSize().height));
        this.title.addFocusListener(new ClearTextFocusListener("Enter Title here", title));

        this.description = new JTextArea("Project Description", 5, 20);
        this.description.addFocusListener(new ProjectGui.ClearTextFocusListener("Project Description", description));
        this.create = new JButton("Create");
        this.create.addActionListener(new ProjectGui.ButtonListener());

        // Use a JScrollPane for the JTextArea to enable scrolling if needed
        JScrollPane descriptionScrollPane = new JScrollPane(description);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(350, 100));

        this.dueTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dueTimeEditor = new JSpinner.DateEditor(dueTimeSpinner, "HH:mm");
        dueTimeSpinner.setEditor(dueTimeEditor);
        JFormattedTextField spinnerEditor = ((JSpinner.DefaultEditor) dueTimeSpinner.getEditor()).getTextField();
        JFormattedTextField spinnerTextField = spinnerEditor;
        spinnerTextField.setEditable(false);
        spinnerTextField.setPreferredSize(new Dimension(40, 20));

        add(new JLabel("Enter the due date of the project"));
        add(due);
        add(dueTimeSpinner);
        add(title);
        add(descriptionScrollPane);
        add(create);
    }


    private class ButtonListener implements ActionListener {
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