import gr.hua.dit.oop2.calendar.TimeListener;
import gr.hua.dit.oop2.calendar.TimeTeller;

import java.time.LocalDateTime;

public class CustomCurrentTime implements TimeTeller {
    private LocalDateTime currentTime;


    public CustomCurrentTime(LocalDateTime newTime) {
        this.currentTime = newTime;
    }


    public LocalDateTime now() {
        return currentTime;
    }

    @Override
    public void addTimeListener(TimeListener timeListener) {}

    @Override
    public void removeTimeListener(TimeListener timeListener) {}
}
