package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Non-implemented cell-editor for table.
 * @UserID - tfy17jfo
 * @date - 2018-01-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class CustomRenderer extends DefaultTableCellRenderer {
    private DefaultTableModel tModel;

    public CustomRenderer(DefaultTableModel tModel) {
        this.tModel = tModel;
    }
}
