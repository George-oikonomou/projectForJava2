package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeGUI extends JPanel {

    private final JLabel timeLabel;
    private LocalDateTime currentTime;

    public CurrentTimeGUI() {
        setPreferredSize(new Dimension(200, 50));
        this.timeLabel = new JLabel();
        add(timeLabel);
        updateTimeLabelWithTimer();
    }

    private void updateTimeLabelWithTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAndDisplayTime();
            }
        });

        timer.start();
    }

    private void updateAndDisplayTime() {
        // Update the current time
        currentTime = LocalDateTime.now();

        // Format current time as HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = currentTime.format(formatter);
        timeLabel.setText(time);

        // Set font size
        Font font = timeLabel.getFont().deriveFont(Font.PLAIN, 30);
        timeLabel.setFont(font);

        // Repaint the panel
        revalidate();
        repaint();
    }
}

