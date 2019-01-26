package controller;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * A custom listener for the table.
 * @UserID - tfy17jfo
 * @date - 2019-01-25
 * @version 2.0
 * @author Jakob Fridesjö
 */
public class TableSelectionListener implements ListSelectionListener {
    private final Controller controller;
    private final JTable table;

    /**
     *
     * @param table table for listening too.
     * @param controller controller for function
     */
    TableSelectionListener(JTable table, Controller controller) {
        this.table = table;
        this.controller = controller;
    }

    /**
     * Action if table changes.
     * @param e type of event.
     */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !controller.switching){
            controller.setProgramInfo(table.getSelectedRow());
            //System.out.println(table.getSelectedRow());
        }
    }
}

