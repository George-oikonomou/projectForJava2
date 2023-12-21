import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {
    JButton newEventButton;
    private Image backgroundImage;
    JButton editEventButton;
    JButton printEventButton;

    MainPage(){
        this.setTitle("Calendar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000,500);
        this.setVisible(true);
        this.backgroundImage = new ImageIcon("mainscreen.png").getImage();
        this.setLayout(null);
        CreateNewEventButton();
        this.add(getNewEventButton());
        CreateEditEventButton();
        this.add(getEditEventButton());

    }
//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
//    }


    private void CreateNewEventButton(){
        newEventButton = new JButton();
        newEventButton.setBounds(30,80,170,60);
        newEventButton.addActionListener(this);
        newEventButton.setText("New Event");
        newEventButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        setNewEventButton(newEventButton);
    }

    private void CreateEditEventButton(){
        editEventButton = new JButton();
        editEventButton.setBounds(30,160,170,60);
        editEventButton.addActionListener(this);
        editEventButton.setText("Edit Event");
        editEventButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        setEditEventButton(editEventButton);
    }

    public void setNewEventButton(JButton newEventButton) {
        this.newEventButton = newEventButton;
    }

    public JButton getNewEventButton() {
        return newEventButton;
    }

    public void setEditEventButton(JButton editEventButton) {this.editEventButton = editEventButton;}

    public JButton getEditEventButton() {
        return editEventButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newEventButton) {
            Validate.println("new Event Button");
            newEventButton.setEnabled(true);
        } else if (e.getSource() == editEventButton)  {
            Validate.println("edit Event Button");
            editEventButton.setEnabled(true);
        }
    }
}
