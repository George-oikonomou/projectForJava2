import javax.swing.*;
import java.awt.*;

public class PanelListCellRenderer implements ListCellRenderer<JPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
        return value;
    }
}
