package GUI;
import gr.hua.dit.oop2.calendar.TimeEvent;
import gr.hua.dit.oop2.calendar.TimeListener;
import gr.hua.dit.oop2.calendar.TimeService;
import gr.hua.dit.oop2.calendar.TimeTeller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeGUI extends JPanel implements TimeListener {

    private final JLabel timeLabel;
    private LocalDateTime currentTime;

    public CurrentTimeGUI() {
        setPreferredSize(new Dimension(200, 50));
        this.timeLabel = new JLabel();
        add(timeLabel);
        TimeTeller teller = TimeService.getTeller();
        teller.addTimeListener(this);
    }

    private void updateAndDisplayTime(LocalDateTime currentTime) {
        // Update the current time
        this.currentTime = currentTime;
        // Format current time as HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = currentTime.format(formatter);
        timeLabel.setText(time);

        // Set font size
        Font font = timeLabel.getFont().deriveFont(Font.PLAIN, 30);
        timeLabel.setFont(font);

        // Repaint the panel
        revalidate();
        repaint();
    }

    @Override
    public void timeChanged(TimeEvent timeEvent) {
        updateAndDisplayTime(timeEvent.getDateTime());
    }
}

