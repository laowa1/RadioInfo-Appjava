package controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TableSelectionListener implements ListSelectionListener {
    private final Controller controller;
    private final JTable table;

    public TableSelectionListener(JTable table, Controller controller) {
        this.table = table;
        this.controller = controller;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !controller.switching){
            controller.setProgramInfo(table.getSelectedRow());
            //System.out.println(table.getSelectedRow());
        }
    }
}

