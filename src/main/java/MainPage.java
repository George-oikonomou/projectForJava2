import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame implements ActionListener {
    JButton newEventButton;
    private Image backgroundImage;
    JButton editEventButton;
    JButton changeProjectStatusButton;
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
        CreateChangeProjectStatusButton();
        this.add(getChangeProjectStatusButton());
        CreatePrintEventButton();
        this.add(getPrintEventButton());
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

    private void CreateChangeProjectStatusButton(){
        changeProjectStatusButton = new JButton();
        changeProjectStatusButton.setBounds(30,240,170,90);
        changeProjectStatusButton.addActionListener(this);
        changeProjectStatusButton.setText("<html>Change Projects<br /> Status</html>");
        changeProjectStatusButton.setFont(new Font("Comic Sans",Font.BOLD,20));
        setChangeProjectStatusButton(changeProjectStatusButton);
    }

    private void CreatePrintEventButton(){
        printEventButton = new JButton();
        printEventButton.setBounds(30,350,170,60);
        printEventButton.addActionListener(this);
        printEventButton.setText("Print Event");
        printEventButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        setPrintEventButton(printEventButton);
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

    public void setChangeProjectStatusButton(JButton changeProjectStatusButton) {
        this.changeProjectStatusButton = changeProjectStatusButton;
    }
    public JButton getChangeProjectStatusButton() {
        return changeProjectStatusButton;
    }

    public void setPrintEventButton(JButton printEventButton) {
        this.printEventButton = printEventButton;
    }
    public JButton getPrintEventButton() {
        return printEventButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newEventButton) {
            Validate.println("new Event Button");
            newEventButton.setEnabled(true);
        } else if (e.getSource() == editEventButton)  {
            Validate.println("edit Event Button");
            editEventButton.setEnabled(true);
        } else if (e.getSource() == changeProjectStatusButton) {
            Validate.println("change Project Status Button");
            changeProjectStatusButton.setEnabled(true);
        } else if (e.getSource() == printEventButton) {
            Validate.println("print Event Button");
            printEventButton.setEnabled(true);
        }
    }
}
