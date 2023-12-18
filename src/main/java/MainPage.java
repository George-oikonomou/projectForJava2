import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {
    JButton newEventButton;

    MainPage(){
        this.setTitle("Calendar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000,500);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(51,153,255));
        this.setLayout(null);
        CreateNewEventButton();
        this.add(getNewEventButton());
    }

    private void CreateNewEventButton(){
        newEventButton = new JButton();
        newEventButton.setBounds(30,150,170,60);
        newEventButton.addActionListener(this);
        newEventButton.setText("New Event");
        setNewEventButton(newEventButton);
    }

    public void setNewEventButton(JButton newEventButton) {
        this.newEventButton = newEventButton;
    }

    public JButton getNewEventButton() {
        return newEventButton;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==newEventButton) {
            Validate.println("new Event Button");
            newEventButton.setEnabled(true);
        }
    }
}
