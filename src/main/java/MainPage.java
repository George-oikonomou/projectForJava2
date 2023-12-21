import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {
    JButton newEventButton;
    private Image backgroundImage;

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

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }


    private void CreateNewEventButton(){
        newEventButton = new JButton();
        newEventButton.setBounds(30,140,170,60);
        newEventButton.addActionListener(this);
        newEventButton.setText("New Event");
        newEventButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        setNewEventButton(newEventButton);
    }

    public void setNewEventButton(JButton newEventButton) {
        this.newEventButton = newEventButton;
    }

    public JButton getNewEventButton() {
        return newEventButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newEventButton) {
            Validate.println("new Event Button");
            newEventButton.setEnabled(true);
        }
    }
}
