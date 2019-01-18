package view;

import model.ProgramInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Table for schedule.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
public class TablePanel extends JPanel {
    DefaultTableModel tModel;
    JTable table;
    String columnNames[] = {"Program", "Schedule"};
    Object[][] rows = {};

    /**
     * Constructs the table
     */
    public TablePanel() {
        tModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tModel);
        //table.setDefaultEditor(Object.class, new TableEditor());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        table.setFillsViewportHeight(true);
        this.add(scrollPane);
    }

    /**
     * Updates the table.
     * @param pList
     */
    public void updateTable(List<ProgramInfo> pList) {
        for (ProgramInfo pI : pList) {
            String date = pI.getStartTimeUTC() + pI.getEndTimeUTC();
            tModel.addRow(new Object[]{pI.getName(), date});
        }
    }

    /**
     * Non-implemented function for showing which programs
     * have already expired.
     */
    public void renderOldPrograms() {
        //TODO implements rendering.
    }
}
