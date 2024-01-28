package Utilities;

import javax.swing.*;
import java.awt.*;


/**
 * A ListCellRenderer that renders a JPanel.
 */
public class PanelListCellRenderer implements ListCellRenderer<JPanel> {

    /**
     * Returns a JPanel that contains a JLabel with the text of the JPanel.
     * @param list The JList that contains the JPanel.
     * @param value The JPanel to be rendered.
     * @param index The index of the JPanel in the JList.
     * @param isSelected Whether the JPanel is selected.
     * @param cellHasFocus Whether the JPanel has focus.
     * @return A JPanel that contains a JLabel with the text of the JPanel.
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
        value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());//if selected, set background to selection background, else set background to list background
        value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());//if selected, set foreground to selection foreground, else set foreground to list foreground

        try {
            value.setToolTipText(((JLabel) value.getComponent(0)).getText());// SET AS TOOLTIP THE TEXT OF THE LABEL (title)
        }catch (Exception ignored){}
        return value;//return the panel
    }
}
