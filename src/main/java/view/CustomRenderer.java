package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Non-implemented cell-editor for table.
 * @UserID - tfy17jfo
 * @date - 2018-01-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class CustomRenderer implements TableCellRenderer {
    private DefaultTableModel tModel;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        JTextField editor = new JTextField();
        if (value != null)
            editor.setText(value.toString());
        editor.setBackground((row % 2 == 0) ? Color.white : Color.cyan);
        return editor;
    }
}
