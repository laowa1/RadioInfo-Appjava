package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Renderer for table.
 * @UserID - tfy17jfo
 * @date - 2018-01-25
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class TableRenderer implements TableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus, int row,
                                                   int column) {


        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter isoTime = DateTimeFormatter.ISO_TIME;
        JTextField editor = new JTextField();
        LocalDateTime time = LocalDateTime.now();
        Font myFont = new Font(Font.MONOSPACED, Font.BOLD, 14).deriveFont(
                AffineTransform.getScaleInstance(0.9, 1d));
        editor.setFont(myFont);
        editor.setOpaque(true);
        if (column > 0 && value != null && value.toString().length() > 13) {
            LocalDateTime timeValue = LocalDateTime.parse(value.toString(),
                    isoDateTime);
            String timeText = timeValue.format(isoTime);
            editor.setBounds(new Rectangle(table.getWidth()/6,
                    table.getRowHeight()));
            final String substring = timeText.substring(
                    0, timeText.length() - 3);
            if (time.isAfter(timeValue)) {
                editor.setBackground(new Color(210,120,105,200));
                editor.setText('(' + substring + ')');
            } else {
                editor.setBackground(new Color(180,210,100,200));
                editor.setText(substring);
            }
        } else if (value != null) {
            if ((row % 2) != 0) {
                editor.setBackground(new Color(150,220,220,90));
            }
            editor.setText(value.toString());
        }
        return editor;
    }
}
